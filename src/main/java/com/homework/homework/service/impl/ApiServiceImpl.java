package com.homework.homework.service.impl;

import com.homework.homework.model.Book;
import com.homework.homework.model.IndustryIdentifier;
import com.homework.homework.model.JsonFile;
import com.homework.homework.service.ApiService;
import com.homework.homework.utils.JsonParser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
            book.setPublishedData(item.getVolumeInfo().getPublishedData());
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
    public List<Book> getAllBooks() {

        return mapper(JsonParser.parseJson("misc/books.json"));
    }

    @Override
    public Book getSpecifiedBookByISBN(String id) {
        return getAllBooks().parallelStream().filter(specifiedBook -> id.equals(specifiedBook.getIsbn())).findAny().orElse(null);
    }
}
