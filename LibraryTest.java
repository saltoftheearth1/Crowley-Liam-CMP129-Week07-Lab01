public class LibraryTest {
    private static void printTestResult(String label, boolean passed) {
        System.out.println((passed ? "PASS" : "FAIL") + " | " + label);
    }

    public static void main(String[] args) {
        Library library = new Library("Maple Grove Library");

        Book hobbit = new Book("The Hobbit", "J.R.R. Tolkien", "9780547928227");
        Book pride = new Book("Pride and Prejudice", "Jane Austen", "9780141439518");
        Book giver = new Book("The Giver", "Lois Lowry", "9780544336261");

        library.addBook(hobbit);
        library.addBook(pride);
        library.addBook(giver);

        Member alice = new Member("Alice Johnson", "M101");
        Member bob = new Member("Bob Smith", "M102");
        library.registerMember(alice);
        library.registerMember(bob);

        System.out.println("=== SETUP TESTS ===");
        printTestResult("01. add three different books",
                library.getBooks().size() == 3 && library.findBookByIsbn("9780544336261") == giver);
        printTestResult("02. register two different members",
                library.getMembers().size() == 2 && library.findMemberById("M102") == bob);

        System.out.println("\n=== INITIAL LIBRARY RECORDS ===");
        library.displayBooks();
        library.displayMembers();

        System.out.println("\n=== BORROW AND RETURN TESTS ===");
        // check the actual results after each borrowing attempt
        boolean firstBorrow = library.borrowBook("M101", "9780547928227");
        printTestResult("03. alice borrows an available book",
                firstBorrow && !hobbit.isAvailable() && alice.getBorrowedBooks().contains(hobbit));

        boolean secondBorrow = library.borrowBook("M102", "9780547928227");
        printTestResult("04. bob cannot borrow the same unavailable book",
                !secondBorrow && !bob.getBorrowedBooks().contains(hobbit));

        boolean firstReturn = library.returnBook("M101", "9780547928227");
        printTestResult("05. alice returns the borrowed book",
                firstReturn && hobbit.isAvailable() && !alice.getBorrowedBooks().contains(hobbit));

        boolean borrowAgain = library.borrowBook("M102", "9780547928227");
        printTestResult("06. bob borrows the returned book",
                borrowAgain && !hobbit.isAvailable() && bob.getBorrowedBooks().contains(hobbit));

        boolean invalidIsbn = library.borrowBook("M101", "9999999999999");
        printTestResult("07. reject an invalid isbn", !invalidIsbn);

        boolean invalidMember = library.borrowBook("M999", "9780141439518");
        printTestResult("08. reject an invalid membership id", !invalidMember && pride.isAvailable());

        boolean wrongReturn = library.returnBook("M101", "9780547928227");
        printTestResult("09. reject a return by someone who did not borrow the book",
                !wrongReturn && !hobbit.isAvailable() && bob.getBorrowedBooks().contains(hobbit));

        boolean invalidReturnMember = library.returnBook("M999", "9780547928227");
        printTestResult("10. reject a return with an invalid membership id", !invalidReturnMember);

        boolean invalidReturnIsbn = library.returnBook("M102", "9999999999999");
        printTestResult("11. reject a return with an invalid isbn", !invalidReturnIsbn);

        printTestResult("12. final records are consistent",
                !hobbit.isAvailable() && pride.isAvailable() && giver.isAvailable()
                        && alice.getBorrowedBooks().isEmpty()
                        && bob.getBorrowedBooks().size() == 1
                        && bob.getBorrowedBooks().contains(hobbit));

        System.out.println("\n=== FINAL LIBRARY RECORDS ===");
        library.displayBooks();
        library.displayMembers();
    }
}

