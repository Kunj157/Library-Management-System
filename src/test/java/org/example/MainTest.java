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
}


