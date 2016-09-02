/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibData.Providers;

import LimitedSolution.Utilities.LibDataUtilities.ProviderUtilities.IProvider;
import LibData.JPAControllers.BookJpaController;
import LibData.Models.Book;
import static LibData.Models.Factories.ProductFactory.createProductByBook;
import LibData.Models.Product;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import LibData.Providers.ProviderHelper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jinq.jpa.JPAJinqStream;
import org.jinq.jpa.JinqJPAStreamProvider;

/**
 *
 * @author Limited
 */
public class BookProvider implements IProvider {

    private BookJpaController jpaBook = new BookJpaController(ProviderHelper.getEntityManagerFactory());

    private JinqJPAStreamProvider streams = new JinqJPAStreamProvider(ProviderHelper.getEntityManagerFactory());

    private BookJpaController getJPABooks() {
        return jpaBook;
    }

    private JPAJinqStream<Book> getJinqBooks() {
        return streams.streamAll(ProviderHelper.getEntityManager(), Book.class);
    }

    private ProductProvider _productProvider = new ProductProvider();

    public List<Book> getAll() {
        try {
            return getJinqBooks().sortedDescendingBy(m -> m.getCreateTime()).toList();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public int count() {
        try {
            return getAll().size();
        } catch (Exception e) {
            System.out.println(e);
            return -1;
        }
    }

    public String getNewIdCode() {
        try {
            String key = "B";
            if (!new ConfigsProvider().IncreaseValueByKey(key)) {
                return "";
            }

            return key + "-" + String.format("%1$06d", Long.parseLong(new ConfigsProvider().getValueByKey(key)));
        } catch (Exception e) {
            System.out.println(e);
            return "";
        }
    }

    @Override
    public boolean Insert(Object object) {
        try {
            Book book = (Book) object;

            book.setId(UUID.randomUUID().toString());

            String IdCode = getNewIdCode();
            if (IdCode.isEmpty()) {
                return false;
            }
            book.setIdCode(IdCode);

            book.setCreateTime(new Date());

            new ProductProvider().Insert(createProductByBook(book));
            ArrayList<Product> product = new ArrayList<Product>();
            product.add(_productProvider.getByIdCode(book.getIdCode()));
            book.setProductCollection(product);

            getJPABooks().create(book);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean Delete(String id) {
        try {
            getJPABooks().destroy(id);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean Update(Object object) {
        try {
            Book book = (Book) object;

            Product product
                    = _productProvider.getByIdCode(
                            getJPABooks().findBook(book.getId()).getIdCode()
                    );
            product.setName(book.getName());
            product.setType(book.getType());
            product.setPrice(book.getPrice());
            _productProvider.Update(product);

            book.setProductCollection(book.getProductCollection());
            
            getJPABooks().edit(book);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(BookProvider.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
