package com.homework.homework.service;

import com.homework.homework.model.Book;
import com.homework.homework.model.Rating;

import java.util.List;

public interface ApiService {
    List<Book> getAllBooks();
    Book getSpecifiedBookByISBN(String id);
    List<Book> getBooksByCategory(String category);
    List<Book> getBooks(String query);
    List<Rating> getRatings();
}
