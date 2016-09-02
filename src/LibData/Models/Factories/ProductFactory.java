/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibData.Models.Factories;

import LibData.Models.Book;
import LibData.Models.Product;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author Limited
 */
public class ProductFactory {
    public static Product createProductByBook(Book book)
    {
        Product product = new Product();
        
        product.setId(UUID.randomUUID().toString());
        product.setIdCode(book.getIdCode());
        product.setCreateTime(new Date());
        product.setCreatedBy(book.getCreatedBy());
        product.setStatus(book.getStatus());
        product.setName(book.getName());
        product.setType(book.getType());
        product.setPrice(book.getPrice());
        
        return product;
    }
    
}
