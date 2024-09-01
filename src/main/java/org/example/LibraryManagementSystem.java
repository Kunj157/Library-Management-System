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

    public static List<Book> getAvailableBooks() {
        return Collections.unmodifiableList(availableBooks);
    }
    public static List<Book> getBorrowedBooks() {
        return Collections.unmodifiableList(borrowedBooks);
    }

    public void addBook(Book book) throws IllegalArgumentException {
        if(book.getAuthor()==null||book.getAuthor().trim().isEmpty()){
            throw new IllegalArgumentException("Book Author can't be null");
        }
        if(book.getTitle()==null||book.getTitle().trim().isEmpty()){
            throw new IllegalArgumentException("Book Title can't be null");
        }
        if(book.getISBN()==null||book.getISBN().trim().isEmpty()){
            throw new IllegalArgumentException("ISBN no can't be null");
        }
        for (Book availableBook : availableBooks) {
            if (availableBook.getISBN().equals(book.getISBN())) {
                throw new IllegalArgumentException("Book cannot be added as there is already a book added with ISBN: " + book.getISBN());
            }
        }
        availableBooks.add(book);
        System.out.println("Book with ISBN " + book.getISBN() + " added successfully!");
    }

    public void borrowBook(String ISBN) throws IllegalArgumentException{
        // Using Iterator to safely and efficiently remove the book from the list while iterating it at the same time
        Iterator<Book> iterator = availableBooks.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getISBN().equals(ISBN)) {
                iterator.remove();
                borrowedBooks.add(book);
                System.out.println("Book with ISBN " + ISBN + " borrowed Successfully!");
                return;
            }
        }
        throw new IllegalArgumentException("Sorry the book with ISBN " + ISBN + " is not available!");
    }

    public void returnBook(String ISBN) throws IllegalArgumentException{
        // Using Iterator to safely and efficiently remove the book from the list while iterating it at the same time
        Iterator<Book> iterator = borrowedBooks.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getISBN().equals(ISBN)) {
                iterator.remove();
                availableBooks.add(book);
                System.out.println("Book with ISBN " + ISBN + " returned Successfully!");
                return;
            }
        }
        throw new IllegalArgumentException("You are returning the wrong book with ISBN as: " + ISBN);
    }

    public void viewAvailableBooks() {
        if (availableBooks.isEmpty()) {
            System.out.println("Sorry, currently no books are available with us.");
            return;
        }
        System.out.println("Following Books are available with us: ");
        for (Book book : availableBooks) {
            System.out.println(
                    "Title: " + book.getTitle() + "\n"
                            + "Author: " + book.getAuthor() + "\n"
                            + "PublicationYear: " + book.getPublicationYear() + "\n"
                            + "ISBN: " + book.getISBN() + "\n");
        }
    }
}
