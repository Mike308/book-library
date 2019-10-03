package com.homework.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//model of book.json or same structured json file containing data about book

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonFile {
    private String requestedUrl;
    private List<Item> items;
}
