/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibData.Providers;

import LibData.JPAControllers.ProductJpaController;
import LibData.Models.Book;
import LibData.Models.Product;
import LimitedSolution.Utilities.LibDataUtilities.ProviderUtilities.IProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.jinq.jpa.JPAJinqStream;
import org.jinq.jpa.JinqJPAStreamProvider;

/**
 *
 * @author Limited
 */
public class ProductProvider implements IProvider {

    private ProductJpaController jpaProduct = new ProductJpaController(ProviderHelper.getEntityManagerFactory());

    private JinqJPAStreamProvider streams = new JinqJPAStreamProvider(ProviderHelper.getEntityManagerFactory());

    private ProductJpaController getJPAProducts() {
        return jpaProduct;
    }

    private JPAJinqStream<Product> getJinqProducts() {
        return streams.streamAll(ProviderHelper.getEntityManager(), Product.class);
    }

    public List<Product> getAll() {
        try {
            return getJinqProducts().sortedDescendingBy(m -> m.getCreateTime()).toList();
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
            String key = "P";
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
            Product product = (Product) object;

            product.setId(UUID.randomUUID().toString());
            if (product.getIdCode() == null || product.getIdCode().isEmpty()) {
                product.setIdCode(getNewIdCode());
            }
            product.setCreateTime(new Date());

            getJPAProducts().create(product);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean Delete(String id) {
        try {
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean Update(Object object) {
        try {
            Product product = (Product) object;
            product.setBookCollection(getById(product.getId()).getBookCollection());
            
            getJPAProducts().edit(product);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    Product getByIdCode(String idCode) {
        try {
            return getJinqProducts().where(m -> m.getIdCode().equals(idCode)).findOne().get();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    private Product getById(String id) {
        try {
            return getJPAProducts().findProduct(id);
        } catch (Exception e) {
            return null;
        }
    }

}
