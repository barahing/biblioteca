import java.sql.Timestamp;

public class BookLoan {
    private int id;
    private Book book;
    private Client client;
    private Timestamp loanDate;
    private Timestamp returnDate;
    private boolean loanIsActive;

    private static int nextId = 1;
    public BookLoan(Book book, Client client, Timestamp loanDate) {
        this.id = nextId++;
        this.book = book;
        this.client = client;
        this.loanIsActive = true;
    }

    public int getId() {
        return id;
    }
    
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Timestamp getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Timestamp loanDate) {
        this.loanDate = loanDate;
    }

    public Timestamp getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Timestamp returnDate) {
        this.returnDate = returnDate;
    }

    public boolean loanIsActive() {
        return loanIsActive;
    }

    public void setLoanIsActive(boolean loanIsActive) {
        this.loanIsActive = loanIsActive;
    }

    
}
