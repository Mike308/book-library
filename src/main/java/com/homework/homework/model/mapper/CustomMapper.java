package com.homework.homework.model.mapper;

import com.homework.homework.model.Book;
import com.homework.homework.model.IndustryIdentifier;
import com.homework.homework.model.JsonFile;
import com.homework.homework.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class CustomMapper {
    public List<Book> mapper(JsonFile jsonFile) {
        List<Book> books = new ArrayList<>();
        jsonFile.getItems().stream().forEach(item -> {
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
            book.setAccessInfo(item.getAccessInfo());
            books.add(book);
        });
        return books;
    }
}
