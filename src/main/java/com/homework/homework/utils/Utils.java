package com.homework.homework.utils;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.homework.model.JsonFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {

    public static JsonFile parseJson(String path) {
        JsonFile jsonFile = new JsonFile();
        ObjectMapper jsonFileMapper = new ObjectMapper();
        jsonFileMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); //preventing before raise exception of unknown property which is not present in model class
        try{
            File file = new File(path); //loading json file
            jsonFile = jsonFileMapper.readValue(file, JsonFile.class); //de-serializing json file to object
        }catch (IOException  e) {
            e.printStackTrace();
        }
        return jsonFile;
    }

    public static long convertDateStringToUnixTimeStamp(String dateString) {
        try {
            String pattern = "yyyy";
            if (dateString.contains("-")) pattern = "yyyy-MM-dd";
            DateFormat dateFormat = new SimpleDateFormat(pattern);
            Date date = dateFormat.parse(dateString);
            return  date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static  boolean customContains(String inputString, String keyword) {
        List<String> inputStringList = Arrays.asList(inputString.toUpperCase() //converting string to upper (made case insensitive)
                                             .replaceAll("[^A-Za-z0-9]", " ") //replace all special char (leaving only letter and digits)
                                             .split(" ")).stream() //tokenize string
                                             .map(s -> s.replaceAll("\\s+", "")) //replace all spaces
                                             .collect(Collectors.toList()); //converting to list which contain all words in input string
        List<String> keyWords = Arrays.asList(keyword.toUpperCase().replaceAll("[^A-Za-z0-9]", " ").split(" ")).stream()
                .map(s -> s.replaceAll("\\s+", "")).collect(Collectors.toList());
        return keyWords.stream().allMatch(inputStringList::contains);
    }

    public static String readConfiguration() {
        try {
            Stream<String> lines = Files.lines(Paths.get("config.txt"));
            String data = lines.collect(Collectors.joining("\n"));
            lines.close();
            return data.trim();
        } catch (IOException e) {
            return "books.json";
        }
    }


}
