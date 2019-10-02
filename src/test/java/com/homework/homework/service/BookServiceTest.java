package com.homework.homework.service;

import com.homework.homework.model.Book;
import com.homework.homework.service.impl.BookServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookServiceImpl apiService;

    @Test
    public void countsOfBooksShouldBeGreaterThanZero() {
        assertTrue(apiService.getAllBooks().size() > 0);
    }

    @Test
    public void booksWithISBNEqualsTo9781592432172ShouldHavePublisherEqualsInfoStrategistcom() {
        assertEquals("InfoStrategist.com", apiService.getSpecifiedBookByISBN("9781592432172").getPublisher());
    }

    @Test
    public void bookWithISBNEqualsTo9780080568782ShouldGiveBookOfTitleEqualsToTCPIPSocketsinJava() {
        Book book = new Book();
        book.setIsbn("9780080568782");
        book.setTitle("TCP/IP Sockets in Java");
        book.setSubtitle("Practical Guide for Programmers");
        book.setPublisher("Morgan Kaufmann");
        book.setPublishedDate(1314568800000L);
        book.setDescription("The networking capabilities of the Java platform have been extended considerably since the first edition of the book. This new edition covers version 1.5-1.7, the most current iterations, as well as making the following improvements: The API (application programming interface) reference sections in each chapter, which describe the relevant parts of each class, have been replaced with (i) a summary section that lists the classes and methods used in the code, and (ii) a \"gotchas\" section that mentions nonobvious or poorly-documented aspects of the objects. In addition, the book covers several new classes and capabilities introduced in the last few revisions of the Java platform. New abstractions to be covered include NetworkInterface, InterfaceAddress, Inet4/6Address, SocketAddress/InetSocketAddress, Executor, and others; extended access to low-level network information; support for IPv6; more complete access to socket options; and scalable I/O. The example code is also modified to take advantage of new language features such as annotations, enumerations, as well as generics and implicit iterators where appropriate. Most Internet applications use sockets to implement network communication protocols. This book's focused, tutorial-based approach helps the reader master the tasks and techniques essential to virtually all client-server projects using sockets in Java. Chapter 1 provides a general overview of networking concepts to allow readers to synchronize the concepts with terminology. Chapter 2 introduces the mechanics of simple clients and servers. Chapter 3 covers basic message construction and parsing. Chapter 4 then deals with techniques used to build more robust clients and servers. Chapter 5 (NEW) introduces the scalable interface facilities which were introduced in Java 1.5, including the buffer and channel abstractions. Chapter 6 discusses the relationship between the programming constructs and the underlying protocol implementations in more detail. Programming concepts are introduced through simple program examples accompanied by line-by-line code commentary that describes the purpose of every part of the program. No other resource presents so concisely or so effectively the material necessary to get up and running with Java sockets programming. Focused, tutorial-based instruction in key sockets programming techniques allows reader to quickly come up to speed on Java applications. Concise and up-to-date coverage of the most recent platform (1.7) for Java applications in networking technology.");
        book.setPageCount(192);
        book.setThumbnailUrl("http://books.google.com/books/content?id=lfHo7uMk7r4C&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api");
        book.setLanguage("en");
        book.setPreviewLink("http://books.google.pl/books?id=lfHo7uMk7r4C&printsec=frontcover&dq=java&hl=&cd=38&source=gbs_api");
        book.setAverageRating(4.0);
        book.setAuthors(Arrays.asList("Kenneth L. Calvert", "Michael J. Donahoo"));
        book.setCategories(Arrays.asList("Computers"));
        assertEquals(book, apiService.getSpecifiedBookByISBN("9780080568782"));
    }

    @Test
    public void bookWithISBNEqualsToN1IiAQAAIAAJShouldGiveJavaFirstContact() {
        Book book = new Book();
        book.setIsbn("N1IiAQAAIAAJ");
        book.setTitle("Java");
        book.setSubtitle("First Contact");
        book.setPublishedDate(883612800000L);
        book.setDescription("Structured into five parts, the book introduces students to using\n" +
                "objects from day one, using them to introduce the basic conepts of programming and\n" +
                "allowing the reader to become familiar with using objects in a variety of\n" +
                "applications.");
        book.setPageCount(660);
        book.setThumbnailUrl("http://books.google.com/books/content?id=N1IiAQAAIAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api");
        book.setLanguage("en");
        book.setPreviewLink("http://books.google.pl/books?id=N1IiAQAAIAAJ&q=java&dq=java&hl=&cd=12&source=gbs_api");
        book.setAuthors(Arrays.asList("Roger Garside", "John A. Mariani"));
        book.setCategories(Arrays.asList("Java (Computer program language)"));
    }

    @Test
    public void countOfBooksWithCategoryEqualsToComputerShouldByGreaterThanZero() {
        assertTrue(apiService.getBooksByCategory("Computers").size() > 0);
    }










}
