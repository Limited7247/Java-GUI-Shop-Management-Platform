/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibData.Providers;

import static LibData.Business.Configs.InventoryConfigs.*;
import LimitedSolution.Utilities.LibDataUtilities.ProviderUtilities.IProvider;
import LibData.JPAControllers.BookJpaController;
import LibData.Models.Book;
import static LibData.Models.Factories.ProductFactory.createProductByBook;
import LibData.Models.Inventory;
import LibData.Models.Product;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import LibData.Providers.ProviderHelper;
import static LibData.Providers.ProviderHelper.*;
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

    private BookJpaController getJPABooks() {
        RefreshDatabase(jpaBook.getEntityManager());
        return jpaBook;
    }

    private JPAJinqStream<Book> getJinqBooks() {
        return getJinqStream().streamAll(ProviderHelper.getEntityManager(), Book.class);
    }

    private ProductProvider _productProvider = new ProductProvider();
    private InventoryProvider _inventoryProvider = new InventoryProvider();

    private void showLog(Exception ex) {
        Logger.getLogger(BookProvider.class.getName()).log(Level.SEVERE, null, ex);
    }

    public List<Book> getAll() {
        try {
            return getJinqBooks().sortedDescendingBy(m -> m.getCreateTime()).toList();
        } catch (Exception ex) {
            showLog(ex);
            return null;
        }
    }

    public Book getById(String id) {
        try {
            return getJinqBooks().where(m -> m.getId().equals(id)).findOne().get();
        } catch (Exception ex) {
            showLog(ex);
            return null;
        }
    }

    public int count() {
        try {
            return getAll().size();
        } catch (Exception ex) {
            showLog(ex);
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
        } catch (Exception ex) {
            showLog(ex);
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

            /// Product
            new ProductProvider().Insert(createProductByBook(book));
            book.setProduct(_productProvider.getByIdCode(book.getIdCode()));
            book.setProductId(book.getProduct().getId());

            /// Inventory
            Inventory inventory = new Inventory();
            inventory.setProductId(book.getProduct());
            inventory.setUnit(INVENTORY_UNIT_TYPE_BOOK);
            inventory.setQuantity(0);
            inventory.setCreateBy(book.getCreatedBy());
            inventory.setStatus(INVENTORY_STATUS_UPTODATE);
            inventory.setType(INVENTORY_TYPE_INIT);
            new InventoryProvider().Insert(inventory);
            
            getJPABooks().create(book);
            return true;
        } catch (Exception ex) {
            showLog(ex);
            return false;
        }
    }

    public boolean CanBeDeleted(String id)
    {
        try {
            List<Inventory> list = (List<Inventory>) getById(id).getProduct().getInventoryCollection();
            if (list == null || list.isEmpty()) return true;
            
            if (list.size() == 1 && list.get(0).getType() == INVENTORY_TYPE_INIT) return true;
            
            return false;
            
        } catch (Exception e) {
            showLog(e);
            return false;
        }
    }
    
    @Override
    public boolean Delete(String id) {
        try {
            if (!CanBeDeleted(id)) return false;
            
            Product product = getById(id).getProduct();
            
            /// Delete Book
            getJPABooks().destroy(product.getId());
            /// Delete Inventory
            new InventoryProvider().DeleteByProductId(product.getId());
            /// Delete Product
            new ProductProvider().Delete(product.getId());

            return true;
        } catch (Exception ex) {
            showLog(ex);
            return false;
        }
    }

    @Override
    public boolean Update(Object object) {
        try {
            Book book = (Book) object;

            Book oldBook = getById(book.getId());
            Book updateBook = oldBook;

            Product product = oldBook.getProduct();
            product.setName(book.getName());
            product.setType(book.getType());
            product.setPrice(book.getPrice());
            _productProvider.Update(product);
            updateBook.setProduct(_productProvider.getByIdCode(product.getIdCode()));

            if (book.getIsbn() != null) {
                updateBook.setIsbn(book.getIsbn());
            }
            if (book.getName() != null) {
                updateBook.setName(book.getName());
            }
            if (book.getType() != null) {
                updateBook.setType(book.getType());
            }
            if (book.getAuthor() != null) {
                updateBook.setAuthor(book.getAuthor());
            }
            if (book.getPublisher() != null) {
                updateBook.setPublisher(book.getPublisher());
            }
            if (book.getPublishYear() > 0) {
                updateBook.setPublishYear(book.getPublishYear());
            }
            if (book.getPublishMonth() != null) {
                updateBook.setPublishMonth(book.getPublishMonth());
            }
            if (book.getDetails() != null) {
                updateBook.setDetails(book.getDetails());
            }
            if (book.getPrice() != null) {
                updateBook.setPrice(book.getPrice());
            }
            if (book.getPicture() != null) {
                updateBook.setPicture(book.getPicture());
            }

            getJPABooks().edit(updateBook);
            return true;
        } catch (Exception ex) {
            showLog(ex);
            return false;
        }
    }

    public List<Book> Find(String findString) {
        try {
            if (findString == null || findString.isEmpty()) {
                return getAll();
            }

            return getJinqBooks()
                    .where(
                            m -> m.getIdCode().contains(findString)
                            || m.getIsbn().contains(findString)
                            || m.getName().contains(findString)
                            || m.getAuthor().contains(findString)
                            || m.getPublisher().contains(findString)
                            || m.getType().contains(findString)
                    )
                    .toList();
        } catch (Exception e) {
            return getAll();
        }
    }
}
