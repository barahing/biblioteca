import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class bibliotecaApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Client>clients = new ArrayList<>();
    private static final List<Book> books = new ArrayList<>();
    private static final List<Author> authors = new ArrayList<>();
    private static final List<BookLoan> bookLoans = new ArrayList<>();
    public static void main(String[] args) {
        // Initialize the application, load data, etc.
        

        System.out.println("Welcome to the Biblioteca App!");
        while(true){
            System.out.println("Choose an option:");
            System.out.println("1. Loan a book");
            System.out.println("2. Return a book");
            System.out.println("3. Create a new client");
            System.out.println("4. Create a new book");
            System.out.println("5. Create a new Ahuthor");
            System.out.println("6. List all books");
            System.out.println("7. List all authors");
            System.out.println("8. List all clients");
            System.out.println("9. List all book loans");
            System.out.println("0. Exit");
            
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> loanBook();
                case 2 -> returnBook();
                case 3 -> createClient();   
                case 4 -> createBook();
                case 5 -> createAuthor();
                case 6 -> listAllBooks();
                case 7 -> listAllAuthors();
                case 8 -> listAllClients();
                case 9 -> listAllLoans();
                case 0 -> System.exit(0);
                default -> System.out.println("Invalid choice, please try again.");
            }
        }

    }
    public static void createClient() {
        System.out.println("Enter client name: ");
        String name = scanner.nextLine();
        System.out.println("Enter client address: ");
        String address = scanner.nextLine();
        System.out.println("Enter client email: ");
        String email = scanner.nextLine();
        
        if (name.isEmpty() || address.isEmpty() || email.isEmpty()) {
            System.out.println("All fields are required.");
            return;
        }
        Client newClient = new Client(name, address, email);
        clients.add(newClient);
        System.out.println("Client created successfully!");
    }

    public static void createAuthor() {
        System.out.println("Enter author name: ");
        String name = scanner.nextLine();
        System.out.println("Enter author nationality: ");
        String nationality = scanner.nextLine();
        
        if (name.isEmpty() || nationality.isEmpty()) {
            System.out.println("All fields are required.");
            return;
        }
        Author newAuthor = new Author(name, nationality);
        authors.add(newAuthor);
        System.out.println("Author created successfully!");

    }

    public static void createBook() {
        System.out.println("Enter book title: ");
        String title = scanner.nextLine();
        System.out.println("Enter book author: ");
        String author = scanner.nextLine();
        System.out.println("Enter book year: ");
        int year = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter book category: " );
        for (Category category : Category.values()) {
            System.out.println(category);
        }
        String category = scanner.nextLine();
        
        if (title.isEmpty() || author.isEmpty() || category.isEmpty() ) {
            System.out.println("All fields are required.");
            return;
        }
        if (year < -2000) {
            System.out.println("Year cannot be less than 2000 B.C. ;)");
            return;
        }
        Book newBook = new Book(title, author, year, Category.valueOf(category.toUpperCase()));
        books.add(newBook);
        System.out.println("Book created successfully!");
    }

    public static void loanBook() {
        if (clients.isEmpty()) {
            System.out.println("No clients available for loan.");
            return;
        }
        if (books.isEmpty()) {
            System.out.println("No books available for loan.");
            return;
        }
        if (books.stream().noneMatch(Book::isAvailable)) {
            System.out.println("No books available for loan.");
            return;
        }
        System.out.println("Enter client ID to loan a book:");  
        for (Client client : clients) {
            System.out.println("ID: " + client.getId() + ", Name: " + client.getName());
        } 


        int clientId = Integer.parseInt(scanner.nextLine());
        if (clientId < 1 || clientId > clients.size()) {
            System.out.println("Invalid client ID.");
            return;
        }
        System.out.println("Enter the book ID to loan:");   
        for (Book book : books.stream().filter(book -> !bookLoans.stream().anyMatch(loan -> loan.getBook().equals(book))).toList()) {
            System.out.println("ID: " + book.getId() + ", Title: " + book.getTitle());
        }
        int bookId = Integer.parseInt(scanner.nextLine());
        if (bookId < 1 || bookId > books.size()) {
            System.out.println("Invalid book ID.");
            return;
        }

        BookLoan bookLoan = new BookLoan(books.get(bookId - 1), clients.get(clientId - 1), Timestamp.from(Instant.now()));
        LoanService loanService = new LoanService();
        loanService.loanBook(bookLoan);
        System.out.println("Book loaned successfully!");
        bookLoans.add(bookLoan);
    }

    public static void returnBook() {
        if (clients.isEmpty()) {
            System.out.println("No clients available for returning books.");
            return;
        }
        if (books.isEmpty()) {
            System.out.println("No books available for return.");
            return;
        }
        if (bookLoans.isEmpty()) {
            System.out.println("No book loans available for return.");
            return;
        }
        if (bookLoans.stream().noneMatch(BookLoan::loanIsActive)) {
            System.out.println("No active book loans available for return.");
            return;
        }
        System.out.println("Enter client ID to return a book:");  
        
        for (Client client : bookLoans.stream()
                .map(BookLoan::getClient)
                .distinct()
                .toList()) {
            System.out.println("ID: " + client.getId() + ", Name: " + client.getName());
        } 

        int clientId = Integer.parseInt(scanner.nextLine());
        if (clientId < 1 || clientId > clients.size()) {
            System.out.println("Invalid client ID.");
            return;
        }
        System.out.println("Enter the book ID to return:");   
        for (Book book : bookLoans.stream()
                .filter(loan -> loan.getClient().getId() == clientId && loan.loanIsActive())
                .map(BookLoan::getBook)
                .distinct()
                .toList()) {
            System.out.println("ID: " + book.getId() + ", Title: " + book.getTitle());
        }
        int bookId = Integer.parseInt(scanner.nextLine());
        if (bookId < 1 || bookId > books.size()) {
            System.out.println("Invalid book ID.");
            return;
        }

        BookLoan bookLoan = new BookLoan(books.get(bookId - 1), clients.get(clientId - 1), Timestamp.from(Instant.now()));
        LoanService loanService = new LoanService();
        loanService.returnBook(bookLoan);
        System.out.println("Book returned successfully!");
        bookLoan.setLoanIsActive(false);
        bookLoans.set(bookId - 1, bookLoan);
    }

    public static void listAllBooks(){
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }
        System.out.println("List of all books:");
        for (Book book : books) {
            System.out.println("ID: " + book.getId() + ", Title: " + book.getTitle() + ", Author: " + book.getAuthor() + ", Year: " + book.getYear() + ", Genre: " + book.getCategory());
        }
    }

    public static void listAllAuthors(){
        if (authors.isEmpty()) {
            System.out.println("No authors available.");
            return;
        }
        System.out.println("List of all authors:");
        for (Author author : authors) {
            System.out.println("ID: " + author.getId() + ", Name: " + author.getName() + ", Nationality: " + author.getNationality());
        }
    }

    public static void listAllClients(){
        if (clients.isEmpty()) {
            System.out.println("No clients available.");
            return;
        }
        System.out.println("List of all clients:");
        for (Client client : clients) {
            System.out.println("ID: " + client.getId() + ", Name: " + client.getName() + ", Address: " + client.getDireccion() + ", Email: " + client.getEmail());
        }
    }

    public static void listAllLoans() {
        if (bookLoans.isEmpty()) {
            System.out.println("No book loans available.");
            return;
        }
        System.out.println("List of all book loans:");
        for (BookLoan loan : bookLoans) {
            System.out.println("ID: " + loan.getId() + ", Book: " + loan.getBook().getTitle() + ", Client: " + loan.getClient().getName() + ", Loan Date: " + loan.getLoanDate() + ", Return Date: " + loan.getReturnDate() + ", Active: " + loan.loanIsActive());
        }
    }
}
