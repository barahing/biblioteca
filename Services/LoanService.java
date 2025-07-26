public class LoanService {
    public void loanBook(BookLoan bookLoan) {
        bookLoan.getBook().loanBook(bookLoan);
    }

    public void returnBook(BookLoan bookLoan) {
        bookLoan.getBook().returnBook(bookLoan);
    }
}
