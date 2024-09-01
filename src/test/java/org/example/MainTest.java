package org.example;

import org.example.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    LibraryManagementSystem lms;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    public static final List<Book> availableBooks = LibraryManagementSystem.getAvailableBooks();
    public static final List<Book> borrowedBooks = LibraryManagementSystem.getBorrowedBooks();

    @BeforeEach
    public void setUp() {
        lms = new LibraryManagementSystem();
    }

    @Test
    public void addBookTest() {
        Book book = new Book("title", "987-123-123-9876", "author", 2004);
        // number of books before adding
        int noOfBooks = availableBooks.size();
        lms.addBook(book);
        assertEquals(noOfBooks + 1, availableBooks.size());
        assertTrue(availableBooks.contains(book));
    }

    @Test
    public void testAddBookWithDuplicateISBN() {
        Book book1 = new Book("Effective Java", "12345", "Joshua Bloch", 2008);
        Book book2 = new Book("Java Concurrency in Practice", "12345", "Brian Goetz", 2006);

        lms.addBook(book1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> lms.addBook(book2));
        assertEquals("Book cannot be added as there is already a book added with ISBN: 12345", exception.getMessage());
    }

    @Test
    void testViewAvailableBooksWhenNoBooksAvailable() {
        System.setOut(new PrintStream(outputStreamCaptor));
        lms.viewAvailableBooks();
        String expectedOutput = "Sorry, currently no books are available with us.";
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @Test
    void testViewAvailableBooksWhenBooksAreAvailable() {
        Book book1 = new Book("Atomic Habits", "234-234-234-1255", "James Clear", 2018);

        lms.addBook(book1);
        System.setOut(new PrintStream(outputStreamCaptor));
        lms.viewAvailableBooks();

        String expectedOutput = "Following Books are available with us: \n";
        assertTrue(outputStreamCaptor.toString().contains(expectedOutput));
        assertTrue(outputStreamCaptor.toString().contains("Title: Atomic Habits"));
        assertTrue(outputStreamCaptor.toString().contains("Author: James Clear"));
        assertTrue(outputStreamCaptor.toString().contains("ISBN: 234-234-234-1255"));
        assertTrue(outputStreamCaptor.toString().contains("PublicationYear: 2018"));
    }

    @Test
    void addBookWithNullAuthorNameTest(){
        Book book4=new Book("Psychology Of Money","34567","",2019);
        assertThrows(IllegalArgumentException.class,()->{
            lms.addBook(book4);
        },"Adding a book with Null as AuthorName should throw an IllegalArgumentException");
    }

    @Test
    void addBookWithNullTitleTest(){
        Book book4=new Book("","43768","Morgan Housel",2019);
        assertThrows(IllegalArgumentException.class,()->{
            lms.addBook(book4);
        },"Adding a book with Null Title should throw an IllegalArgumentException");
    }

    @Test
    void addBookWithNullISBNTest(){
        Book book4=new Book("Alchemist","","Paulo Coelho",2019);
        assertThrows(IllegalArgumentException.class,()->{
            lms.addBook(book4);
        },"Adding a book with Null ISBN should throw an IllegalArgumentException");
    }

    @Test
    public void borrowAvailableBookTest() {
        Book book = new Book("title", "987-123-123-9876", "author", 2004);
        // Add a single books
        lms.addBook(book);
        // available books before borrowing
        int noOfAvailableBooks = availableBooks.size();
        // borrowed books before borrowing
        int noOfBorrowedBooks = borrowedBooks.size();
        lms.borrowBook("987-123-123-9876");
        assertEquals(noOfAvailableBooks - 1, availableBooks.size());
        assertEquals(noOfBorrowedBooks + 1, borrowedBooks.size());
        assertTrue(borrowedBooks.contains(book));
        assertFalse(availableBooks.contains(book));
    }

    @Test
    public void borrowUnavailableBook() {
        Book book = new Book("Rich Dad Poor Dad", "32145", "Robert Kiyoski", 2021);
        // Add a single books
        lms.addBook(book);
        assertThrows(IllegalArgumentException.class, () -> lms.borrowBook("01345"),
                "Trying to borrow an unavailable book should thrown an IllegalArgumentException");
    }

    @Test
    public void returnBorrowedBookTest() {
        Book book = new Book("title", "987-123-123-0000", "author", 2004);
        // Add a single books
        lms.addBook(book);
        // borrow that book
        lms.borrowBook("987-123-123-0000");
        // available books before returning
        int noOfAvailableBooks = availableBooks.size();
        // borrowed books before returning
        int noOfBorrowedBooks = borrowedBooks.size();
        lms.returnBook("987-123-123-0000");
        assertEquals(noOfAvailableBooks + 1, availableBooks.size());
        assertEquals(noOfBorrowedBooks - 1, borrowedBooks.size());
        assertTrue(availableBooks.contains(book));
        assertFalse(borrowedBooks.contains(book));
    }

    @Test
    public void returnWrongBookTest() {
        Book book = new Book("title", "987-123-123-1111", "author", 2004);
        // Add a single books
        lms.addBook(book);
        // borrow that book
        lms.borrowBook("987-123-123-1111");
        assertThrows(IllegalArgumentException.class, () -> lms.returnBook("789-789-789-7899"),
                "Trying to return a wrong book which is not yet borrowed should throw an IllegalArgumentException");
    }

}


