package com.homework.homework.controller;

import com.homework.homework.model.Book;
import com.homework.homework.model.DownloadLink;
import com.homework.homework.model.Rating;

import java.util.List;


public interface BookController {
    String getBookByISBN(String isbn);
    List<Book> getBooksByCategory(String category);
    List<Book> getBooksByQuery(String q);
    List<Rating> getRatings();
    List<DownloadLink> getDownloadLinks(String isbn);
}
