package com.homework.homework.service.impl;

import com.homework.homework.model.Book;
import com.homework.homework.model.IndustryIdentifier;
import com.homework.homework.model.JsonFile;
import com.homework.homework.model.Rating;
import com.homework.homework.service.ApiService;
import com.homework.homework.utils.JsonParser;
import com.homework.homework.utils.Utils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApiServiceImpl implements ApiService {

    private List<Book> mapper(JsonFile jsonFile) {
        List<Book> books = new ArrayList<>();
        jsonFile.getItems().parallelStream().forEach(item -> {
            Book book = new Book();
            book.setIsbn(item.getVolumeInfo().getIndustryIdentifiers().stream().filter(isbn -> "ISBN_13".equals(isbn.getType()))
                    .findAny().orElse(new IndustryIdentifier("ID", item.getId())).getIdentifier());
            book.setTitle(item.getVolumeInfo().getTitle());
            book.setSubtitle(item.getVolumeInfo().getSubtitle());
            book.setPublisher(item.getVolumeInfo().getPublisher());
            if (item.getVolumeInfo().getPublishedDate() != null) {
                book.setPublishedDate(Utils.convertDateStringToUnixTimeStamp(item.getVolumeInfo().getPublishedDate()));
            }
            book.setDescription(item.getVolumeInfo().getDescription());
            book.setPageCount(item.getVolumeInfo().getPageCount());
            book.setThumbnailUrl(item.getVolumeInfo().getImageLinks().getThumbnail());
            book.setLanguage(item.getVolumeInfo().getLanguage());
            book.setPreviewLink(item.getVolumeInfo().getPreviewLink());
            book.setAverageRating(item.getVolumeInfo().getAverageRating());
            book.setAuthors(item.getVolumeInfo().getAuthors());
            book.setCategories(item.getVolumeInfo().getCategories());
            books.add(book);
        });
        return books;
    }

    @Override
    @PostConstruct
    public List<Book> getAllBooks() {
        return mapper(JsonParser.parseJson("misc/books.json"));
    }

    @Override
    public Book getSpecifiedBookByISBN(String id) {
        return getAllBooks().parallelStream().filter(book -> book != null).filter(specifiedBook -> id.equals(specifiedBook.getIsbn())).findAny().orElse(new Book());
    }

    @Override
    public List<Book> getBooksByCategory(String category) {
        return getAllBooks().parallelStream().filter(book -> book != null).filter(book -> book.getCategories() != null)
                .filter(book -> book.getCategories().contains(category)).collect(Collectors.toList());
    }

    @Override
    public List<Book> getBooks(String query) {
        return getAllBooks().parallelStream().filter(book -> book != null).filter(book -> book.getTitle().contains(query) || book.getSubtitle().contains(query)
                || book.getDescription().contains(query)).collect(Collectors.toList());
    }

    @Override
    public List<Rating> getRatings() {
        List<String> authors = new ArrayList<>();
        List<Rating> ratings = new ArrayList<>();
        getAllBooks().parallelStream().filter(book -> book != null).filter(book -> book.getAuthors() != null).forEach(book -> authors.addAll(book.getAuthors()));
        authors.parallelStream().filter(v -> v != null).distinct().forEach(author -> {
            System.out.println(author);
            OptionalDouble averageOptional = getAllBooks().parallelStream().filter(book -> book != null).filter(book -> book.getAuthors() != null)
                    .filter(book -> book.getAuthors().contains(author)).mapToDouble(t -> t.getAverageRating()).average();
            ratings.add(new Rating(author, averageOptional.getAsDouble()));
        });
        return ratings;
    }


}
