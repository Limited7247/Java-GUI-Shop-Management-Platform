/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibData.Providers;

import LimitedSolution.Utilities.LibDataUtilities.ProviderUtilities.IProvider;
import LibData.JPAControllers.BookJpaController;
import LibData.Models.Book;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import LibData.Providers.ProviderHelper;
import java.util.Date;
import java.util.List;
import java.util.UUID;
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

    public List<Book> getAll() {
        try {
            return getJinqBooks().sortedDescendingBy(m -> m.getCreateTime()).toList();
        } catch (Exception e) {
            return null;
        }
    }

    public int count() {
        try {
            return getAll().size();
        } catch (Exception e) {
            return -1;
        }
    }

    public String getNewIdCode()
    {
        try {
            String key = "B";
            
            String IdCode = key + "-" + new ConfigsProvider().getValueByKey(key);
            return "";
        } catch (Exception e) {
            return "";
        }
    }
    
    @Override
    public boolean Insert(Object object) {
        try {
            Book book = (Book) object;
            book.setId(UUID.randomUUID().toString());
            ///book.setIdCode();
            book.setCreateTime(new Date());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean Delete(String id) {
        try {
            getJPABooks().destroy(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean Update(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
