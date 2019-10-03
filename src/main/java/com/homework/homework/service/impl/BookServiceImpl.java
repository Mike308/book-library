package com.homework.homework.service.impl;

import com.homework.homework.model.*;
import com.homework.homework.model.mapper.CustomMapper;
import com.homework.homework.service.BookService;
import com.homework.homework.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private JsonFile jsonFile;
    private CustomMapper customMapper = new CustomMapper();


    public BookServiceImpl() {
        jsonFile = Utils.parseJson(Utils.readConfiguration());
    }


    @Override
    public List<Book> getAllBooks() {
         return customMapper.mapper(jsonFile);
    }

    @Override
    public Book getSpecifiedBookByISBN(String id) {
        return getAllBooks().stream().filter(book -> book != null) //eliminate null book object (preventing before NullPointerException)
                .filter(specifiedBook -> id.equals(specifiedBook.getIsbn())).findAny().orElse(new Book());
    }

    @Override
    public List<Book> getBooksByCategory(String category) {
        return getAllBooks().stream().filter(book -> book != null).filter(book -> book.getCategories() != null)
                .filter(book -> book.getCategories().contains(category)).collect(Collectors.toList());
    }

    @Override
    public List<Book> getBooks(String query) {
        return  getAllBooks().stream().filter(book -> book != null)
                .filter(book -> Utils.customContains(book.toString(), query)).collect(Collectors.toList());
    }

    @Override
    public List<Rating> getRatings() {
        List<String> authors = new ArrayList<>();
        List<Rating> ratings = new ArrayList<>();
        getAllBooks().stream().filter(book -> book != null).filter(book -> book.getAuthors() != null)
                .filter(book -> book.getAverageRating() > 0.0) //eliminating non rated books
                .forEach(book -> authors.addAll(book.getAuthors())); //fetch and add authors to authors list
        authors.stream().filter(v -> v != null).distinct().forEach(author -> { //iterate through authors list and filter book belongs to author
            OptionalDouble averageOptional = getAllBooks().stream().filter(book -> book != null)
                    .filter(book -> book.getAuthors() != null)
                    .filter(book -> book.getAuthors().contains(author))
                    .mapToDouble(t -> t.getAverageRating()).average(); //mapping book to average rating and calculating average for each author
            ratings.add(new Rating(author, averageOptional.getAsDouble()));
        });
        return ratings.stream().sorted(Comparator.comparingDouble(Rating::getAverageRating)
                               .reversed()).collect(Collectors.toList()); //sorting average rate from highest to lowest
    }

    @Override
    public List<DownloadLink> getDownloadLinks(String isbn) {
        List<DownloadLink> downloadLinks = new ArrayList<>(); //create list of download links
        getAllBooks().stream().filter(item -> item != null).filter(item -> isbn.equals(item.getIsbn()))
                     .filter(item -> item.getAccessInfo() != null).forEach(item -> { //filter all books containing access info and insert links into download links list
            if (item.getAccessInfo().getEpub().isAvailable()){
                downloadLinks.add(new DownloadLink("epub", item.getAccessInfo().getEpub().getAcsTokenLink()));
            }
            if (item.getAccessInfo().getPdf().isAvailable()) {
                downloadLinks.add(new DownloadLink("pdf", item.getAccessInfo().getPdf().getAcsTokenLink()));
            }
        });
        return downloadLinks;
    }


}
