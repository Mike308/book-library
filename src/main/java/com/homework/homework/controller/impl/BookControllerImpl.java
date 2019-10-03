package com.homework.homework.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.homework.controller.BookController;
import com.homework.homework.model.Book;
import com.homework.homework.model.DownloadLink;
import com.homework.homework.model.Rating;
import com.homework.homework.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class BookControllerImpl implements BookController {

    @Autowired
    private BookServiceImpl bookService;

    @Override
    @GetMapping("/book/{isbn}")
    public String getBookByISBN(@PathVariable String isbn)  {
        ObjectMapper objectMapper = new ObjectMapper();
        Book book = bookService.getSpecifiedBookByISBN(isbn);
        if (book.getIsbn() != null) {
            try {
                return objectMapper.writeValueAsString(book);
            }catch (JsonProcessingException e) {
                return "500";
            }
        }else {
            return "404, No result found";
        }
    }

    @Override
    @GetMapping("/category/{category}/books")
    public List<Book> getBooksByCategory(@PathVariable String category) {
        return bookService.getBooksByCategory(category);
    }

    @Override
    @GetMapping("/search")
    public List<Book> getBooksByQuery(@RequestParam String q) {
        return bookService.getBooks(q);
    }

    @Override
    @GetMapping("/rating")
    public List<Rating> getRatings() {
        return bookService.getRatings();
    }

    @Override
    @GetMapping("/downloadlinks")
    public List<DownloadLink> getDownloadLinks(@RequestParam  String isbn) {
        return bookService.getDownloadLinks(isbn);
    }


}
