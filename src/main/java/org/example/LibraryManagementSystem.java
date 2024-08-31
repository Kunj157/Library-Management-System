package org.example;

import org.example.model.Book;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class LibraryManagementSystem {
    private static final List<Book> availableBooks = new ArrayList<>();
    private static final List<Book> borrowedBooks = new ArrayList<>();

    // Declaring this methods so that other classes can only access both the lists in a read-only manner
    public static List<Book> getAvailableBooks() {
        return Collections.unmodifiableList(availableBooks);
    }
    public static List<Book> getBorrowedBooks() {
        return Collections.unmodifiableList(borrowedBooks);
    }
    public void viewAvailableBooks() {
        if (availableBooks.isEmpty()) {
            System.out.println("Sorry, currently no books are available with us.");
            return;
        }
        System.out.println("Following Books are available with us: \n");
        for (Book book : availableBooks) {
            System.out.println(
                    "Title: " + book.getTitle() + "\n"
                            + "Author: " + book.getAuthor() + "\n"
                            + "PublicationYear: " + book.getPublicationYear() + "\n"
                            + "ISBN: " + book.getISBN() + "\n");
        }
    }
}
