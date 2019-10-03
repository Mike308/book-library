package com.homework.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//model of field VolumeInfo

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VolumeInfo {
    private String title;
    private String subtitle;
    private String publisher;
    private String publishedDate;
    private String description;
    private int pageCount;
    private ImageLink imageLinks;
    private String language;
    private String previewLink;
    private double averageRating;
    private List<String> authors;
    private List<String> categories;
    private List<IndustryIdentifier> industryIdentifiers;
}
