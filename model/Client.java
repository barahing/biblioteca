import java.util.ArrayList;
import java.util.List;


public class Client {
    private int id;
    private String name;
    private String direccion; 
    private String email;
    private List<Book> books = new ArrayList<Book>();

    private static int nextId = 1;

    public Client(String name, String direccion, String email) {
        this.id = nextId++;
        this.name = name;
        this.direccion = direccion;
        this.email = email;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
