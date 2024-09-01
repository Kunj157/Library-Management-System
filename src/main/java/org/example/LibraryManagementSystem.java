package org.example;

import org.example.model.Book;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class LibraryManagementSystem {
    public static final List<Book> availableBooks = new ArrayList<>();
    public static final List<Book> borrowedBooks = new ArrayList<>();

    public void addBook(Book book) throws IllegalArgumentException {
        for (Book availableBook : availableBooks) {
            if (availableBook.getISBN().equals(book.getISBN())) {
                throw new IllegalArgumentException("Book cannot be added as there is already a book added with ISBN: " + book.getISBN());
            }
        }
        availableBooks.add(book);
        System.out.println("Book with ISBN " + book.getISBN() + " added successfully!");
    }


}
