/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibData.Models.Factories;

import LibData.Business.GlobalConfigs;
import LibData.Models.Book;
import java.math.BigInteger;
import java.util.Date;

/**
 *
 * @author Limited
 */
public class BookFactory {

    public static Book createBook() {
        Book book = new Book();

        book.setId("");
        book.setIdCode("");
        book.setName("");
        book.setType("");
        book.setAuthor("");
        book.setPublisher("");
        book.setPublishYear(0);
        book.setPublishMonth(0);
        book.setDetails("");
        book.setPrice(BigInteger.ZERO);
        book.setPicture("");
        book.setCreateTime(new Date());
        book.setCreatedBy(null);
        book.setStatus(GlobalConfigs.CONST_UNDEFINED_KEY);

        return book;
    }

    public static Book createBook(
            String ISBN, String Price,
            String Name, String Author,
            String Publisher, String Type,
            String Year, String Details) {

        Book book = createBook();

        book.setIsbn(ISBN);
        try {
            book.setPrice(BigInteger.valueOf(Long.parseLong(Price)));
        } catch (Exception e) {
        }
        book.setName(Name);
        book.setAuthor(Author);
        book.setPublisher(Publisher);
        book.setType(Type);
        book.setPublishYear(Integer.parseInt(Year));
        book.setDetails(Details);

        return book;
    }

//    public static Book createBook(String ISBN, String Price, String Name, String Author, String Publisher, String Type, String Year, String Details, String CreatedBy) {
//        Book book = createBook(ISBN, Price, Name, Author, Publisher, Type, Year, Details);
//        
//        book.setCreatedBy(createdBy);
//        
//        return book;
//    }
    
    public static Book createBook(String ISBN, String Price, String Name, String Author, String Publisher, String Type, String Year, String Month, String Details) {
        Book book = createBook(ISBN, Price, Name, Author, Publisher, Type, Year, Details);
        
        if (!Month.isEmpty()) {
            book.setPublishMonth(Integer.parseInt(Month));
        }
        
        return book;
    }

    public static Book createBook(String Id, String ISBN, String Price, String Name, String Author, String Publisher, String Type, String PublishYear, String PublishMonth, String Details) {
        Book book = createBook(ISBN, Price, Name, Author, Publisher, Type, PublishYear, PublishMonth, Details);
        
        book.setId(Id);
        
        return book;
    }
}
