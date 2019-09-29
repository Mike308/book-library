package com.homework.homework.service;

import com.homework.homework.model.Book;

import java.util.List;

public interface ApiService {
    List<Book> getAllBooks();
    Book getSpecifiedBookByISBN(String id);
}
