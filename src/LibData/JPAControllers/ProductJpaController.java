/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibData.JPAControllers;

import LibData.JPAControllers.exceptions.IllegalOrphanException;
import LibData.JPAControllers.exceptions.NonexistentEntityException;
import LibData.JPAControllers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import LibData.Models.Book;
import LibData.Models.Account;
import LibData.Models.OrderLine;
import java.util.ArrayList;
import java.util.Collection;
import LibData.Models.Inventory;
import LibData.Models.Product;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Limited
 */
public class ProductJpaController implements Serializable {

    public ProductJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Product product) throws PreexistingEntityException, Exception {
        if (product.getOrderLineCollection() == null) {
            product.setOrderLineCollection(new ArrayList<OrderLine>());
        }
        if (product.getInventoryCollection() == null) {
            product.setInventoryCollection(new ArrayList<Inventory>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Book book = product.getBook();
            if (book != null) {
                book = em.getReference(book.getClass(), book.getProductId());
                product.setBook(book);
            }
            Account createdBy = product.getCreatedBy();
            if (createdBy != null) {
                createdBy = em.getReference(createdBy.getClass(), createdBy.getId());
                product.setCreatedBy(createdBy);
            }
            Collection<OrderLine> attachedOrderLineCollection = new ArrayList<OrderLine>();
            for (OrderLine orderLineCollectionOrderLineToAttach : product.getOrderLineCollection()) {
                orderLineCollectionOrderLineToAttach = em.getReference(orderLineCollectionOrderLineToAttach.getClass(), orderLineCollectionOrderLineToAttach.getId());
                attachedOrderLineCollection.add(orderLineCollectionOrderLineToAttach);
            }
            product.setOrderLineCollection(attachedOrderLineCollection);
            Collection<Inventory> attachedInventoryCollection = new ArrayList<Inventory>();
            for (Inventory inventoryCollectionInventoryToAttach : product.getInventoryCollection()) {
                inventoryCollectionInventoryToAttach = em.getReference(inventoryCollectionInventoryToAttach.getClass(), inventoryCollectionInventoryToAttach.getId());
                attachedInventoryCollection.add(inventoryCollectionInventoryToAttach);
            }
            product.setInventoryCollection(attachedInventoryCollection);
            em.persist(product);
            if (book != null) {
                Product oldProductOfBook = book.getProduct();
                if (oldProductOfBook != null) {
                    oldProductOfBook.setBook(null);
                    oldProductOfBook = em.merge(oldProductOfBook);
                }
                book.setProduct(product);
                book = em.merge(book);
            }
            if (createdBy != null) {
                createdBy.getProductCollection().add(product);
                createdBy = em.merge(createdBy);
            }
            for (OrderLine orderLineCollectionOrderLine : product.getOrderLineCollection()) {
                Product oldProductIdOfOrderLineCollectionOrderLine = orderLineCollectionOrderLine.getProductId();
                orderLineCollectionOrderLine.setProductId(product);
                orderLineCollectionOrderLine = em.merge(orderLineCollectionOrderLine);
                if (oldProductIdOfOrderLineCollectionOrderLine != null) {
                    oldProductIdOfOrderLineCollectionOrderLine.getOrderLineCollection().remove(orderLineCollectionOrderLine);
                    oldProductIdOfOrderLineCollectionOrderLine = em.merge(oldProductIdOfOrderLineCollectionOrderLine);
                }
            }
            for (Inventory inventoryCollectionInventory : product.getInventoryCollection()) {
                Product oldProductIdOfInventoryCollectionInventory = inventoryCollectionInventory.getProductId();
                inventoryCollectionInventory.setProductId(product);
                inventoryCollectionInventory = em.merge(inventoryCollectionInventory);
                if (oldProductIdOfInventoryCollectionInventory != null) {
                    oldProductIdOfInventoryCollectionInventory.getInventoryCollection().remove(inventoryCollectionInventory);
                    oldProductIdOfInventoryCollectionInventory = em.merge(oldProductIdOfInventoryCollectionInventory);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProduct(product.getId()) != null) {
                throw new PreexistingEntityException("Product " + product + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Product product) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Product persistentProduct = em.find(Product.class, product.getId());
            Book bookOld = persistentProduct.getBook();
            Book bookNew = product.getBook();
            Account createdByOld = persistentProduct.getCreatedBy();
            Account createdByNew = product.getCreatedBy();
            Collection<OrderLine> orderLineCollectionOld = persistentProduct.getOrderLineCollection();
            Collection<OrderLine> orderLineCollectionNew = product.getOrderLineCollection();
            Collection<Inventory> inventoryCollectionOld = persistentProduct.getInventoryCollection();
            Collection<Inventory> inventoryCollectionNew = product.getInventoryCollection();
            List<String> illegalOrphanMessages = null;
            if (bookOld != null && !bookOld.equals(bookNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Book " + bookOld + " since its product field is not nullable.");
            }
            for (OrderLine orderLineCollectionOldOrderLine : orderLineCollectionOld) {
                if (!orderLineCollectionNew.contains(orderLineCollectionOldOrderLine)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain OrderLine " + orderLineCollectionOldOrderLine + " since its productId field is not nullable.");
                }
            }
            for (Inventory inventoryCollectionOldInventory : inventoryCollectionOld) {
                if (!inventoryCollectionNew.contains(inventoryCollectionOldInventory)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Inventory " + inventoryCollectionOldInventory + " since its productId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (bookNew != null) {
                bookNew = em.getReference(bookNew.getClass(), bookNew.getProductId());
                product.setBook(bookNew);
            }
            if (createdByNew != null) {
                createdByNew = em.getReference(createdByNew.getClass(), createdByNew.getId());
                product.setCreatedBy(createdByNew);
            }
            Collection<OrderLine> attachedOrderLineCollectionNew = new ArrayList<OrderLine>();
            for (OrderLine orderLineCollectionNewOrderLineToAttach : orderLineCollectionNew) {
                orderLineCollectionNewOrderLineToAttach = em.getReference(orderLineCollectionNewOrderLineToAttach.getClass(), orderLineCollectionNewOrderLineToAttach.getId());
                attachedOrderLineCollectionNew.add(orderLineCollectionNewOrderLineToAttach);
            }
            orderLineCollectionNew = attachedOrderLineCollectionNew;
            product.setOrderLineCollection(orderLineCollectionNew);
            Collection<Inventory> attachedInventoryCollectionNew = new ArrayList<Inventory>();
            for (Inventory inventoryCollectionNewInventoryToAttach : inventoryCollectionNew) {
                inventoryCollectionNewInventoryToAttach = em.getReference(inventoryCollectionNewInventoryToAttach.getClass(), inventoryCollectionNewInventoryToAttach.getId());
                attachedInventoryCollectionNew.add(inventoryCollectionNewInventoryToAttach);
            }
            inventoryCollectionNew = attachedInventoryCollectionNew;
            product.setInventoryCollection(inventoryCollectionNew);
            product = em.merge(product);
            if (bookNew != null && !bookNew.equals(bookOld)) {
                Product oldProductOfBook = bookNew.getProduct();
                if (oldProductOfBook != null) {
                    oldProductOfBook.setBook(null);
                    oldProductOfBook = em.merge(oldProductOfBook);
                }
                bookNew.setProduct(product);
                bookNew = em.merge(bookNew);
            }
            if (createdByOld != null && !createdByOld.equals(createdByNew)) {
                createdByOld.getProductCollection().remove(product);
                createdByOld = em.merge(createdByOld);
            }
            if (createdByNew != null && !createdByNew.equals(createdByOld)) {
                createdByNew.getProductCollection().add(product);
                createdByNew = em.merge(createdByNew);
            }
            for (OrderLine orderLineCollectionNewOrderLine : orderLineCollectionNew) {
                if (!orderLineCollectionOld.contains(orderLineCollectionNewOrderLine)) {
                    Product oldProductIdOfOrderLineCollectionNewOrderLine = orderLineCollectionNewOrderLine.getProductId();
                    orderLineCollectionNewOrderLine.setProductId(product);
                    orderLineCollectionNewOrderLine = em.merge(orderLineCollectionNewOrderLine);
                    if (oldProductIdOfOrderLineCollectionNewOrderLine != null && !oldProductIdOfOrderLineCollectionNewOrderLine.equals(product)) {
                        oldProductIdOfOrderLineCollectionNewOrderLine.getOrderLineCollection().remove(orderLineCollectionNewOrderLine);
                        oldProductIdOfOrderLineCollectionNewOrderLine = em.merge(oldProductIdOfOrderLineCollectionNewOrderLine);
                    }
                }
            }
            for (Inventory inventoryCollectionNewInventory : inventoryCollectionNew) {
                if (!inventoryCollectionOld.contains(inventoryCollectionNewInventory)) {
                    Product oldProductIdOfInventoryCollectionNewInventory = inventoryCollectionNewInventory.getProductId();
                    inventoryCollectionNewInventory.setProductId(product);
                    inventoryCollectionNewInventory = em.merge(inventoryCollectionNewInventory);
                    if (oldProductIdOfInventoryCollectionNewInventory != null && !oldProductIdOfInventoryCollectionNewInventory.equals(product)) {
                        oldProductIdOfInventoryCollectionNewInventory.getInventoryCollection().remove(inventoryCollectionNewInventory);
                        oldProductIdOfInventoryCollectionNewInventory = em.merge(oldProductIdOfInventoryCollectionNewInventory);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = product.getId();
                if (findProduct(id) == null) {
                    throw new NonexistentEntityException("The product with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Product product;
            try {
                product = em.getReference(Product.class, id);
                product.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The product with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Book bookOrphanCheck = product.getBook();
            if (bookOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the Book " + bookOrphanCheck + " in its book field has a non-nullable product field.");
            }
            Collection<OrderLine> orderLineCollectionOrphanCheck = product.getOrderLineCollection();
            for (OrderLine orderLineCollectionOrphanCheckOrderLine : orderLineCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the OrderLine " + orderLineCollectionOrphanCheckOrderLine + " in its orderLineCollection field has a non-nullable productId field.");
            }
            Collection<Inventory> inventoryCollectionOrphanCheck = product.getInventoryCollection();
            for (Inventory inventoryCollectionOrphanCheckInventory : inventoryCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the Inventory " + inventoryCollectionOrphanCheckInventory + " in its inventoryCollection field has a non-nullable productId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Account createdBy = product.getCreatedBy();
            if (createdBy != null) {
                createdBy.getProductCollection().remove(product);
                createdBy = em.merge(createdBy);
            }
            em.remove(product);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Product> findProductEntities() {
        return findProductEntities(true, -1, -1);
    }

    public List<Product> findProductEntities(int maxResults, int firstResult) {
        return findProductEntities(false, maxResults, firstResult);
    }

    private List<Product> findProductEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Product.class));
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

    public Product findProduct(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Product.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Product> rt = cq.from(Product.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
