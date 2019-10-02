package com.homework.homework.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

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
//        Trie trie = Trie.builder().ignoreCase().onlyWholeWords().ignoreOverlaps().addKeywords(keyword.split(" ")).build();
//        Collection<Emit> emits = trie.parseText(inputString);
//        boolean found = false;
//        for (Emit emit : emits) {
//            System.out.println(emit.get);
//            found = true;
//        }
//        return found;
        List<String> inputStringList = Arrays.asList(inputString.toUpperCase().replaceAll("[^A-Za-z0-9]", " ").split(" ")).stream()
                .map(s -> s.replaceAll("\\s+", "")).collect(Collectors.toList());
        List<String> keyWords = Arrays.asList(keyword.toUpperCase().replaceAll("[^A-Za-z0-9]", " ").split(" ")).stream()
                .map(s -> s.replaceAll("\\s+", "")).collect(Collectors.toList());
        inputStringList.forEach(System.out::println);
        return keyWords.stream().allMatch(inputStringList::contains);
    }
}
