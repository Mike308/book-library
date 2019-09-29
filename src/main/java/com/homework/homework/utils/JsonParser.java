package com.homework.homework.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.homework.model.Book;
import com.homework.homework.model.JsonFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    public static List<Book> parseJson(String path) {
        JsonFile jsonFile = new JsonFile();
        ObjectMapper jsonFileMapper = new ObjectMapper();
        jsonFileMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try{
            File file = new File(path);
            jsonFile = jsonFileMapper.readValue(file, JsonFile.class);
        }catch (IOException  e) {
            e.printStackTrace();
        }
        return jsonFile.getItems();
    }
}
