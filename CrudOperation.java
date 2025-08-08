import java.sql.*;
import java.util.Scanner;

public class CrudOperation {

    //Define ulr, username, password
    //create connection instance
    //Create connection instance, register with driver manager
    //ResultSet to hold the result
    //close Connection

    private final static String URL = "jdbc:postgresql://localhost:5432/library_db";

    private final static String USERNAME = "postgres";

    private final static String PASSWORD = "huong1509";

    private final static Scanner SCANNER = new Scanner(System.in);

    public void createBook() throws SQLException {
        System.out.println("Enter Book Title Name: ");
        String title = SCANNER.nextLine();
        System.out.println("Enter Book Author Name: ");
        String author = SCANNER.nextLine();

        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        String sql = """
                insert into Books (title, author)
                values (?, ?)
                """;

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, author);
        //use execte update

        int rowAffectd = ps.executeUpdate();
        if (rowAffectd > 0) {
            System.out.println("Book has been created");
        }else{
            System.out.println("Book could not be created");
        }

    }

    public void findByTitle() throws SQLException {
        System.out.println("Enter book title:" );
        String title = SCANNER.nextLine();

        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        String sql = """

                select * from Books where title = ilike ?;

                """;
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, "%" + title + "%");

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Book book = new Book(
                    rs.getInt("ID"),
                    rs.getString("Title"),
                    rs.getString("Author")
            );
            System.out.println(book);

        }
    }

    public <ConnectIon> void updateById() throws SQLException{
        System.out.println("Enter an id to update: ");
        int id = Integer.parseInt(SCANNER.nextLine());

        if(!existsById(id)){
            System.out.println("Book not found!");
            return;
        }

        System.out.println("Enter new title: ");
        String title = SCANNER.nextLine();
        System.out.println("Enter new author: ");
        String author = SCANNER.nextLine();
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        String sql = """
                update books set title = ?, author = ? where id = ?
                """;
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,title);
        ps.setString(2,author);
        ps.setInt(3,id);

        int rowsAffected = ps.executeUpdate();
        if (rowsAffected>0){
            System.out.println("Update ");
        }else{
            System.out.println("Failed to update");
        }
    }

    public void deleteById() throws SQLException {
        System.out.print("Enter book ID to delete: ");
        int id = Integer.parseInt(SCANNER.nextLine());

        if (!existsById(id)) {
            System.out.println("Book not found!");
            return;
        }

        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int affected = ps.executeUpdate();
            System.out.println(affected > 0 ? "Book deleted successfully!" : "Delete failed.");
        }
    }

    public void listAllBooks() throws SQLException {
        String sql = "SELECT * FROM books";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author")
                ));
            }

            if (!found) {
                System.out.println("No books available.");
            }
        }
    }

    public static boolean existsById(int id) throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        String sql = "select 1 from books where id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
}