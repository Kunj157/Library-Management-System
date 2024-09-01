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
    public static final List<Book> availableBooks = LibraryManagementSystem.availableBooks;
    public static final List<Book> borrowedBooks = LibraryManagementSystem.borrowedBooks;

    @BeforeEach
    public void setUp() {
        lms = new LibraryManagementSystem();
        System.setOut(new PrintStream(outputStreamCaptor));
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
        lms.viewAvailableBooks();
        String expectedOutput = "Sorry, currently no books are available with us.";
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @Test
    void testViewAvailableBooksWhenBooksAreAvailable() {
        Book book1 = new Book("12345", "Effective Java", "Joshua Bloch", 2008);
        Book book2 = new Book("67890", "Clean Code", "Robert C. Martin", 2008);

        lms.addBook(book1);
        lms.addBook(book2);

        lms.viewAvailableBooks();

        String expectedOutput = "Following Books are available with us: \n\n" +
                "Title: Effective Java\n" +
                "Author: Joshua Bloch\n" +
                "PublicationYear: 2008\n" +
                "ISBN: 12345\n" +
                "Title: Clean Code\n" +
                "Author: Robert C. Martin\n" +
                "PublicationYear: 2008\n" +
                "ISBN: 67890\n";

        assertEquals(expectedOutput.trim(), outputStreamCaptor.toString().trim());
    }
}


