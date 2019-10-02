package com.homework.homework.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.homework.model.*;
import com.homework.homework.service.BookService;
import com.homework.homework.utils.JsonParser;
import com.homework.homework.utils.Utils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private JsonFile jsonFile;
    List<Book> allBooks;

    public BookServiceImpl() {
        jsonFile = JsonParser.parseJson("misc/books.json");
    }

    private List<Book> mapper(JsonFile jsonFile) {
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

    public  JsonFile parseJson(String path) {
        JsonFile jsonFile = new JsonFile();
        ObjectMapper jsonFileMapper = new ObjectMapper();
        jsonFileMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try{
            File file = new File(path);
            jsonFile = jsonFileMapper.readValue(file, JsonFile.class);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return jsonFile;
    }

    private List<DownloadLink> getDownloadLinks() {
        List<DownloadLink> downloadLinks = new ArrayList<>();
        jsonFile.getItems().stream().forEach(item -> {
            if (item.getAccessInfo().getEpub().isAvailable()){
                downloadLinks.add(new DownloadLink("epub", item.getAccessInfo().getEpub().getAcsTokenLink()));
            }
            if (item.getAccessInfo().getPdf().isAvailable()) {
                downloadLinks.add(new DownloadLink("pdf", item.getAccessInfo().getPdf().getAcsTokenLink()));
            }
        });
        return downloadLinks;
    }

    @Override
    public List<Book> getAllBooks() {
         allBooks = mapper(jsonFile);
         return allBooks;
    }

    @Override
    public Book getSpecifiedBookByISBN(String id) {
        return getAllBooks().stream().filter(book -> book != null).filter(specifiedBook -> id.equals(specifiedBook.getIsbn())).findAny().orElse(new Book());
    }

    @Override
    public List<Book> getBooksByCategory(String category) {
        return getAllBooks().stream().filter(book -> book != null).filter(book -> book.getCategories() != null)
                .filter(book -> book.getCategories().contains(category)).collect(Collectors.toList());
    }

    @Override
    public List<Book> getBooks(String query) {
        return  getAllBooks().stream().filter(book -> book != null).filter(book -> Utils.customContains(book.toString(), query)).collect(Collectors.toList());
    }

    @Override
    public List<Rating> getRatings() {
        List<String> authors = new ArrayList<>();
        List<Rating> ratings = new ArrayList<>();
        getAllBooks().stream().filter(book -> book != null).filter(book -> book.getAuthors() != null).filter(book -> book.getAverageRating() > 0.0) //eliminating non rated books
                .forEach(book -> authors.addAll(book.getAuthors())); //fetch and add authors to authors list
        authors.stream().filter(v -> v != null).distinct().forEach(author -> {
            OptionalDouble averageOptional = getAllBooks().stream().filter(book -> book != null).filter(book -> book.getAuthors() != null)
                    .filter(book -> book.getAuthors().contains(author)).mapToDouble(t -> t.getAverageRating()).average();
            ratings.add(new Rating(author, averageOptional.getAsDouble()));
        });
        return ratings.stream().sorted(Comparator.comparingDouble(Rating::getAverageRating).reversed()).collect(Collectors.toList());
    }

    @Override
    public List<DownloadLink> getDownloadLinks(String isbn) {
        List<DownloadLink> downloadLinks = new ArrayList<>();
        getAllBooks().stream().filter(item -> item != null).filter(item -> isbn.equals(item.getIsbn())).filter(item -> item.getAccessInfo() != null).forEach(item -> {
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
