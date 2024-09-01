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
        if(book.getAuthor()==null||book.getAuthor().trim().isEmpty()){
            throw new IllegalArgumentException("Book Author can't be null");
        }
        for (Book availableBook : availableBooks) {
            if (availableBook.getISBN().equals(book.getISBN())) {
                throw new IllegalArgumentException("Book cannot be added as there is already a book added with ISBN: " + book.getISBN());
            }
        }
        availableBooks.add(book);
        System.out.println("Book with ISBN " + book.getISBN() + " added successfully!");
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
