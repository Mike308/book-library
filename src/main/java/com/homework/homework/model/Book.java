package com.homework.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private String isbn;
    private String title;
    private String subtitle;
    private String publisher;
    private long publishedData;
    private String description;
    private int pageCount;
    private String thumbnailUrl;
    private String language;
    private String previewLink;
    private double averageRating;
    private List<String> authors;
    private List<String> categories;
}
