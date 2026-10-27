import java.util.ArrayList;

public class Library {
    private String name;
    private ArrayList<Book> books;
    private ArrayList<Member> members;

    public Library(String name) {
        this.name = name;
        // set up the empty catalog and member list
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Book> getBooks() {
        return new ArrayList<>(books);
    }

    public ArrayList<Member> getMembers() {
        return new ArrayList<>(members);
    }

    public void addBook(Book book) {
        // skip null books and duplicate isbn values
        if (book != null && findBookByIsbn(book.getIsbn()) == null) {
            books.add(book);
        }
    }

    public void registerMember(Member member) {
        // each membership id should only belong to one member
        if (member != null && findMemberById(member.getMembershipId()) == null) {
            members.add(member);
        }
    }

    public Book findBookByIsbn(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    public Member findMemberById(String membershipId) {
        for (Member member : members) {
            if (member.getMembershipId().equals(membershipId)) {
                return member;
            }
        }
        return null;
    }

    public boolean borrowBook(String membershipId, String isbn) {
        // check both ids before trying to change anything
        Member member = findMemberById(membershipId);
        Book book = findBookByIsbn(isbn);

        if (member == null) {
            System.out.println("Borrow failed: membership ID " + membershipId + " was not found.");
            return false;
        }
        if (book == null) {
            System.out.println("Borrow failed: ISBN " + isbn + " was not found.");
            return false;
        }
        if (!member.borrowBook(book)) {
            System.out.println("Borrow failed: \"" + book.getTitle() + "\" is unavailable.");
            return false;
        }
        return true;
    }

    public boolean returnBook(String membershipId, String isbn) {
        // the member and the book both need to exist first
        Member member = findMemberById(membershipId);
        Book book = findBookByIsbn(isbn);

        if (member == null) {
            System.out.println("Return failed: membership ID " + membershipId + " was not found.");
            return false;
        }
        if (book == null) {
            System.out.println("Return failed: ISBN " + isbn + " was not found.");
            return false;
        }
        if (!member.returnBook(book)) {
            System.out.println("Return failed: \"" + book.getTitle()
                    + "\" was not borrowed by " + member.getName() + ".");
            return false;
        }
        return true;
    }

    public void displayBooks() {
        System.out.println("--- Books in " + name + " ---");
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
        }
        for (Book book : books) {
            book.displayInfo();
        }
    }

    public void displayMembers() {
        System.out.println("--- Registered Members ---");
        if (members.isEmpty()) {
            System.out.println("No registered members.");
        }
        for (Member member : members) {
            member.displayInfo();
        }
    }
}

