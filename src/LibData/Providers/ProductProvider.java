/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibData.Providers;

import LibData.JPAControllers.ProductJpaController;
import LibData.Models.Book;
import LibData.Models.Inventory;
import LibData.Models.Product;
import LimitedSolution.Utilities.LibDataUtilities.ProviderUtilities.IProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.jinq.jpa.JPAJinqStream;
import org.jinq.jpa.JinqJPAStreamProvider;

/**
 *
 * @author Limited
 */
public class ProductProvider implements IProvider {

    private EntityManagerFactory getEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("MVCDemoPU");
    }

    private ProductJpaController jpaProduct = new ProductJpaController(ProviderHelper.getEntityManagerFactory());

    private JinqJPAStreamProvider streams = new JinqJPAStreamProvider(ProviderHelper.getEntityManagerFactory());

    private ProductJpaController getJPAProducts() {
        return new ProductJpaController(getEntityManagerFactory());
    }

    private JPAJinqStream<Product> getJinqProducts() {
        return streams.streamAll(ProviderHelper.getEntityManager(), Product.class);
    }

    private void showLog(Exception ex) {
        Logger.getLogger(ProductProvider.class.getName()).log(Level.SEVERE, null, ex);
    }

    public List<Product> getAll() {
        try {
            return getJinqProducts().sortedDescendingBy(m -> m.getCreateTime()).toList();
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
            String key = "P";
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
            Product product = (Product) object;

            product.setId(UUID.randomUUID().toString());
            if (product.getIdCode() == null || product.getIdCode().isEmpty()) {
                product.setIdCode(getNewIdCode());
            }
            product.setCreateTime(new Date());

            getJPAProducts().create(product);
            return true;
        } catch (Exception ex) {
            showLog(ex);
            return false;
        }
    }

    @Override
    public boolean Delete(String id) {
        try {
            Product product = getById(id);
            product.setInventoryCollection(new ArrayList<Inventory>());
            getJPAProducts().edit(product);
            getJPAProducts().destroy(id);
            return true;
        } catch (Exception ex) {
            showLog(ex);
            return false;
        }
    }

    @Override
    public boolean Update(Object object) {
        try {
            Product product = (Product) object;
            Product updateProduct = getById(product.getId());

//            if (product.getStatus() != null) updateProduct.setStatus(product.getStatus());
            if (product.getName() != null) {
                updateProduct.setName(product.getName());
            }
            if (product.getType() != null) {
                updateProduct.setType(product.getType());
            }
            if (product.getPrice() != null) {
                updateProduct.setPrice(product.getPrice());
            }
//            product.setBook(getById(product.getId()).getBook());
//            product.setInventoryCollection(product.getInventoryCollection());
            
            getJPAProducts().edit(updateProduct);
            return true;
        } catch (Exception ex) {
            showLog(ex);
            return false;
        }
    }

    Product getByIdCode(String idCode) {
        try {
            return getJinqProducts().where(m -> m.getIdCode().equals(idCode)).findOne().get();
        } catch (Exception ex) {
            showLog(ex);
            return null;
        }
    }

    public Product getById(String id) {
        try {
            return getJPAProducts().findProduct(id);
        } catch (Exception ex) {
            showLog(ex);
            return null;
        }
    }

}
