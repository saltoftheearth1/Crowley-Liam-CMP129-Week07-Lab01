import java.util.ArrayList;

public class Member {
    private String name;
    private String membershipId;
    private ArrayList<Book> borrowedBooks;

    public Member(String name, String membershipId) {
        this.name = name;
        this.membershipId = membershipId;
        // nobody has borrowed anything when they first join
        this.borrowedBooks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
    }

    public ArrayList<Book> getBorrowedBooks() {
        // return a copy so outside code can't mess with the list
        return new ArrayList<>(borrowedBooks);
    }

    public boolean borrowBook(Book book) {
        // an unavailable book can't be borrowed again
        if (book == null || !book.isAvailable()) {
            return false;
        }

        borrowedBooks.add(book);
        book.setAvailable(false);
        return true;
    }

    public boolean returnBook(Book book) {
        // only the person who borrowed it can return it
        if (book == null || !borrowedBooks.contains(book)) {
            return false;
        }

        borrowedBooks.remove(book);
        book.setAvailable(true);
        return true;
    }

    public void displayInfo() {
        System.out.println("Name: " + name + " | Membership ID: " + membershipId);

        if (borrowedBooks.isEmpty()) {
            System.out.println("  Borrowed books: None");
        } else {
            System.out.println("  Borrowed books:");
            for (Book book : borrowedBooks) {
                System.out.println("    - " + book.getTitle() + " (ISBN: " + book.getIsbn() + ")");
            }
        }
    }
}

