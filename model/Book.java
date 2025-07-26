import java.sql.Timestamp;
import java.time.Instant;

public class Book implements ILoanable {
    private int id;
    private String title;
    private String author;
    private int year;
    private Category category;
    private boolean isAvailable;

    static int nextId=1;
    public Book(String title, String author, int year, Category category) {
        this.id = nextId++;
        this.title = title;
        this.author = author;
        this.year = year;
        this.category = category;
        this.isAvailable = true;
    }
    public int getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public boolean isAvailable() {
        return isAvailable;
    }
    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    
    
    @Override
    public void returnBook(BookLoan bookLoan) {
        if (this.isAvailable) {
            System.out.println("Book is already available for return.");
            return;
        }
        if (!this.isAvailable && bookLoan.loanIsActive()) {
            bookLoan.setLoanIsActive(false);
            this.isAvailable = true;
            bookLoan.getClient().getBooks().remove(this);
            bookLoan.setReturnDate(Timestamp.from(Instant.now()));
        } else {
            throw new IllegalStateException("Cannot return book: Loan is not active or does not match.");
        }
        
    }
    @Override
    public void loanBook(BookLoan bookLoan) {
        if (this.isAvailable) {
            bookLoan.setClient(bookLoan.getClient());
            bookLoan.setBook(this);
            bookLoan.setLoanDate(Timestamp.from(Instant.now()));
            bookLoan.setLoanIsActive(true);
            this.isAvailable = false;
            bookLoan.getClient().getBooks().add(bookLoan.getBook());
        } else {
            throw new IllegalStateException("Book is not available for loan.");
        }
    }

    
}
