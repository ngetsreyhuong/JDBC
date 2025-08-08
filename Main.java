import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CrudOperation operation = new CrudOperation();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("""
                    1. Add book
                    2. Find book by title
                    3. Update book by id
                    4. Delete book by id
                    5. List all books
                    0. Exit
                    """);

            System.out.print("Enter option: -> ");
            int option = Integer.parseInt(sc.nextLine());

            if (option == 0) {
                System.out.println("Exiting program...");
                break;
            }

            try {
                switch (option) {
                    case 1 -> operation.createBook();
                    case 2 -> operation.findByTitle();
                    case 3 -> operation.updateById();
                    case 4 -> operation.deleteById();
                    case 5 -> operation.listAllBooks();
                    default -> System.out.println("Invalid option.");
                }
            } catch (SQLException e) {
                System.out.println("SQL Exception occurred: " + e.getMessage());
            }
        }
    }
}
