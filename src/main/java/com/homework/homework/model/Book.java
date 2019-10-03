package com.homework.homework.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"accessInfo"}) //hiding this field in response
@JsonInclude(JsonInclude.Include.NON_DEFAULT) //hiding fields which have default values eg. null, 0.0 etc.
public class Book {
    private String isbn;
    private String title;
    private String subtitle;
    private String publisher;
    private long  publishedDate;
    private String description;
    private int pageCount;
    private String thumbnailUrl;
    private String language;
    private String previewLink;
    private double averageRating;
    private List<String> authors;
    private List<String> categories;
    private AccessInfo accessInfo;
}
