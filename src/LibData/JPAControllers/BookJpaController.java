/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibData.JPAControllers;

import LibData.JPAControllers.exceptions.NonexistentEntityException;
import LibData.JPAControllers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import LibData.Models.Account;
import LibData.Models.Book;
import LibData.Models.Product;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Limited
 */
public class BookJpaController implements Serializable {

    public BookJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Book book) throws PreexistingEntityException, Exception {
        if (book.getProductCollection() == null) {
            book.setProductCollection(new ArrayList<Product>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Account createdBy = book.getCreatedBy();
            if (createdBy != null) {
                createdBy = em.getReference(createdBy.getClass(), createdBy.getId());
                book.setCreatedBy(createdBy);
            }
            Collection<Product> attachedProductCollection = new ArrayList<Product>();
            for (Product productCollectionProductToAttach : book.getProductCollection()) {
                productCollectionProductToAttach = em.getReference(productCollectionProductToAttach.getClass(), productCollectionProductToAttach.getId());
                attachedProductCollection.add(productCollectionProductToAttach);
            }
            book.setProductCollection(attachedProductCollection);
            em.persist(book);
            if (createdBy != null) {
                createdBy.getBookCollection().add(book);
                createdBy = em.merge(createdBy);
            }
            for (Product productCollectionProduct : book.getProductCollection()) {
                productCollectionProduct.getBookCollection().add(book);
                productCollectionProduct = em.merge(productCollectionProduct);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBook(book.getId()) != null) {
                throw new PreexistingEntityException("Book " + book + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Book book) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Book persistentBook = em.find(Book.class, book.getId());
            Account createdByOld = persistentBook.getCreatedBy();
            Account createdByNew = book.getCreatedBy();
            Collection<Product> productCollectionOld = persistentBook.getProductCollection();
            Collection<Product> productCollectionNew = book.getProductCollection();
            if (createdByNew != null) {
                createdByNew = em.getReference(createdByNew.getClass(), createdByNew.getId());
                book.setCreatedBy(createdByNew);
            }
            Collection<Product> attachedProductCollectionNew = new ArrayList<Product>();
            for (Product productCollectionNewProductToAttach : productCollectionNew) {
                productCollectionNewProductToAttach = em.getReference(productCollectionNewProductToAttach.getClass(), productCollectionNewProductToAttach.getId());
                attachedProductCollectionNew.add(productCollectionNewProductToAttach);
            }
            productCollectionNew = attachedProductCollectionNew;
            book.setProductCollection(productCollectionNew);
            book = em.merge(book);
            if (createdByOld != null && !createdByOld.equals(createdByNew)) {
                createdByOld.getBookCollection().remove(book);
                createdByOld = em.merge(createdByOld);
            }
            if (createdByNew != null && !createdByNew.equals(createdByOld)) {
                createdByNew.getBookCollection().add(book);
                createdByNew = em.merge(createdByNew);
            }
            for (Product productCollectionOldProduct : productCollectionOld) {
                if (!productCollectionNew.contains(productCollectionOldProduct)) {
                    productCollectionOldProduct.getBookCollection().remove(book);
                    productCollectionOldProduct = em.merge(productCollectionOldProduct);
                }
            }
            for (Product productCollectionNewProduct : productCollectionNew) {
                if (!productCollectionOld.contains(productCollectionNewProduct)) {
                    productCollectionNewProduct.getBookCollection().add(book);
                    productCollectionNewProduct = em.merge(productCollectionNewProduct);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = book.getId();
                if (findBook(id) == null) {
                    throw new NonexistentEntityException("The book with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Book book;
            try {
                book = em.getReference(Book.class, id);
                book.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The book with id " + id + " no longer exists.", enfe);
            }
            Account createdBy = book.getCreatedBy();
            if (createdBy != null) {
                createdBy.getBookCollection().remove(book);
                createdBy = em.merge(createdBy);
            }
            Collection<Product> productCollection = book.getProductCollection();
            for (Product productCollectionProduct : productCollection) {
                productCollectionProduct.getBookCollection().remove(book);
                productCollectionProduct = em.merge(productCollectionProduct);
            }
            em.remove(book);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Book> findBookEntities() {
        return findBookEntities(true, -1, -1);
    }

    public List<Book> findBookEntities(int maxResults, int firstResult) {
        return findBookEntities(false, maxResults, firstResult);
    }

    private List<Book> findBookEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Book.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Book findBook(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Book.class, id);
        } finally {
            em.close();
        }
    }

    public int getBookCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Book> rt = cq.from(Book.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
