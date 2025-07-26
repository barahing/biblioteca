import java.util.ArrayList;
import java.util.List;

public class Author {
    private int id;
    private String name;
    private String nationality;
    private List<Book> books = new ArrayList<>();
    
    private static int nextId = 1;

    public Author(String name, String nationality) {
        this.id = nextId++;
        this.name = name;
        this.nationality = nationality;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getNationality() {
        return nationality;
    }
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
    public List<Book> getBooks() {
        return books;
    }
    public void setBooks(List<Book> books) {
        this.books = books;
    }
    public static int getNextId() {
        return nextId;
    }
    public static void setNextId(int nextId) {
        Author.nextId = nextId;
    }
    public static void addBookToAuthor(Book book, Author author) {
        if (book != null && author != null) {
            author.getBooks().add(book);
            book.setAuthor(author.getName());
        } else {
            throw new IllegalArgumentException("Book or Author cannot be null");
        }
    }
    
}
