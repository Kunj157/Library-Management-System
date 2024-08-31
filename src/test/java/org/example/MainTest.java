package org.example;

import org.example.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    LibraryManagementSystem lms;
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
    }

}