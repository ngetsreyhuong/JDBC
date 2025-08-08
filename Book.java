import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Book {
    private Integer id;
    private String title;
    private String author;
}
