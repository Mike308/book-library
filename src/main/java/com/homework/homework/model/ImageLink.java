package com.homework.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//model of field ImageLink

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageLink {
    private String smallThumbnail;
    private String thumbnail;
}
