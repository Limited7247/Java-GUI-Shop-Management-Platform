/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibData.JPAControllers;

import LibData.JPAControllers.exceptions.IllegalOrphanException;
import LibData.JPAControllers.exceptions.NonexistentEntityException;
import LibData.JPAControllers.exceptions.PreexistingEntityException;
import LibData.Models.Account;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import LibData.Models.Orders;
import java.util.ArrayList;
import java.util.Collection;
import LibData.Models.OrderLine;
import LibData.Models.Book;
import LibData.Models.Product;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Limited
 */
public class AccountJpaController implements Serializable {

    public AccountJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Account account) throws PreexistingEntityException, Exception {
        if (account.getOrdersCollection() == null) {
            account.setOrdersCollection(new ArrayList<Orders>());
        }
        if (account.getOrderLineCollection() == null) {
            account.setOrderLineCollection(new ArrayList<OrderLine>());
        }
        if (account.getBookCollection() == null) {
            account.setBookCollection(new ArrayList<Book>());
        }
        if (account.getProductCollection() == null) {
            account.setProductCollection(new ArrayList<Product>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Orders> attachedOrdersCollection = new ArrayList<Orders>();
            for (Orders ordersCollectionOrdersToAttach : account.getOrdersCollection()) {
                ordersCollectionOrdersToAttach = em.getReference(ordersCollectionOrdersToAttach.getClass(), ordersCollectionOrdersToAttach.getId());
                attachedOrdersCollection.add(ordersCollectionOrdersToAttach);
            }
            account.setOrdersCollection(attachedOrdersCollection);
            Collection<OrderLine> attachedOrderLineCollection = new ArrayList<OrderLine>();
            for (OrderLine orderLineCollectionOrderLineToAttach : account.getOrderLineCollection()) {
                orderLineCollectionOrderLineToAttach = em.getReference(orderLineCollectionOrderLineToAttach.getClass(), orderLineCollectionOrderLineToAttach.getId());
                attachedOrderLineCollection.add(orderLineCollectionOrderLineToAttach);
            }
            account.setOrderLineCollection(attachedOrderLineCollection);
            Collection<Book> attachedBookCollection = new ArrayList<Book>();
            for (Book bookCollectionBookToAttach : account.getBookCollection()) {
                bookCollectionBookToAttach = em.getReference(bookCollectionBookToAttach.getClass(), bookCollectionBookToAttach.getId());
                attachedBookCollection.add(bookCollectionBookToAttach);
            }
            account.setBookCollection(attachedBookCollection);
            Collection<Product> attachedProductCollection = new ArrayList<Product>();
            for (Product productCollectionProductToAttach : account.getProductCollection()) {
                productCollectionProductToAttach = em.getReference(productCollectionProductToAttach.getClass(), productCollectionProductToAttach.getId());
                attachedProductCollection.add(productCollectionProductToAttach);
            }
            account.setProductCollection(attachedProductCollection);
            em.persist(account);
            for (Orders ordersCollectionOrders : account.getOrdersCollection()) {
                Account oldCreateByOfOrdersCollectionOrders = ordersCollectionOrders.getCreateBy();
                ordersCollectionOrders.setCreateBy(account);
                ordersCollectionOrders = em.merge(ordersCollectionOrders);
                if (oldCreateByOfOrdersCollectionOrders != null) {
                    oldCreateByOfOrdersCollectionOrders.getOrdersCollection().remove(ordersCollectionOrders);
                    oldCreateByOfOrdersCollectionOrders = em.merge(oldCreateByOfOrdersCollectionOrders);
                }
            }
            for (OrderLine orderLineCollectionOrderLine : account.getOrderLineCollection()) {
                Account oldCreateByOfOrderLineCollectionOrderLine = orderLineCollectionOrderLine.getCreateBy();
                orderLineCollectionOrderLine.setCreateBy(account);
                orderLineCollectionOrderLine = em.merge(orderLineCollectionOrderLine);
                if (oldCreateByOfOrderLineCollectionOrderLine != null) {
                    oldCreateByOfOrderLineCollectionOrderLine.getOrderLineCollection().remove(orderLineCollectionOrderLine);
                    oldCreateByOfOrderLineCollectionOrderLine = em.merge(oldCreateByOfOrderLineCollectionOrderLine);
                }
            }
            for (Book bookCollectionBook : account.getBookCollection()) {
                Account oldCreatedByOfBookCollectionBook = bookCollectionBook.getCreatedBy();
                bookCollectionBook.setCreatedBy(account);
                bookCollectionBook = em.merge(bookCollectionBook);
                if (oldCreatedByOfBookCollectionBook != null) {
                    oldCreatedByOfBookCollectionBook.getBookCollection().remove(bookCollectionBook);
                    oldCreatedByOfBookCollectionBook = em.merge(oldCreatedByOfBookCollectionBook);
                }
            }
            for (Product productCollectionProduct : account.getProductCollection()) {
                Account oldCreatedByOfProductCollectionProduct = productCollectionProduct.getCreatedBy();
                productCollectionProduct.setCreatedBy(account);
                productCollectionProduct = em.merge(productCollectionProduct);
                if (oldCreatedByOfProductCollectionProduct != null) {
                    oldCreatedByOfProductCollectionProduct.getProductCollection().remove(productCollectionProduct);
                    oldCreatedByOfProductCollectionProduct = em.merge(oldCreatedByOfProductCollectionProduct);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAccount(account.getId()) != null) {
                throw new PreexistingEntityException("Account " + account + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Account account) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Account persistentAccount = em.find(Account.class, account.getId());
            Collection<Orders> ordersCollectionOld = persistentAccount.getOrdersCollection();
            Collection<Orders> ordersCollectionNew = account.getOrdersCollection();
            Collection<OrderLine> orderLineCollectionOld = persistentAccount.getOrderLineCollection();
            Collection<OrderLine> orderLineCollectionNew = account.getOrderLineCollection();
            Collection<Book> bookCollectionOld = persistentAccount.getBookCollection();
            Collection<Book> bookCollectionNew = account.getBookCollection();
            Collection<Product> productCollectionOld = persistentAccount.getProductCollection();
            Collection<Product> productCollectionNew = account.getProductCollection();
            List<String> illegalOrphanMessages = null;
            for (Orders ordersCollectionOldOrders : ordersCollectionOld) {
                if (!ordersCollectionNew.contains(ordersCollectionOldOrders)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Orders " + ordersCollectionOldOrders + " since its createBy field is not nullable.");
                }
            }
            for (OrderLine orderLineCollectionOldOrderLine : orderLineCollectionOld) {
                if (!orderLineCollectionNew.contains(orderLineCollectionOldOrderLine)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain OrderLine " + orderLineCollectionOldOrderLine + " since its createBy field is not nullable.");
                }
            }
            for (Book bookCollectionOldBook : bookCollectionOld) {
                if (!bookCollectionNew.contains(bookCollectionOldBook)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Book " + bookCollectionOldBook + " since its createdBy field is not nullable.");
                }
            }
            for (Product productCollectionOldProduct : productCollectionOld) {
                if (!productCollectionNew.contains(productCollectionOldProduct)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Product " + productCollectionOldProduct + " since its createdBy field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Orders> attachedOrdersCollectionNew = new ArrayList<Orders>();
            for (Orders ordersCollectionNewOrdersToAttach : ordersCollectionNew) {
                ordersCollectionNewOrdersToAttach = em.getReference(ordersCollectionNewOrdersToAttach.getClass(), ordersCollectionNewOrdersToAttach.getId());
                attachedOrdersCollectionNew.add(ordersCollectionNewOrdersToAttach);
            }
            ordersCollectionNew = attachedOrdersCollectionNew;
            account.setOrdersCollection(ordersCollectionNew);
            Collection<OrderLine> attachedOrderLineCollectionNew = new ArrayList<OrderLine>();
            for (OrderLine orderLineCollectionNewOrderLineToAttach : orderLineCollectionNew) {
                orderLineCollectionNewOrderLineToAttach = em.getReference(orderLineCollectionNewOrderLineToAttach.getClass(), orderLineCollectionNewOrderLineToAttach.getId());
                attachedOrderLineCollectionNew.add(orderLineCollectionNewOrderLineToAttach);
            }
            orderLineCollectionNew = attachedOrderLineCollectionNew;
            account.setOrderLineCollection(orderLineCollectionNew);
            Collection<Book> attachedBookCollectionNew = new ArrayList<Book>();
            for (Book bookCollectionNewBookToAttach : bookCollectionNew) {
                bookCollectionNewBookToAttach = em.getReference(bookCollectionNewBookToAttach.getClass(), bookCollectionNewBookToAttach.getId());
                attachedBookCollectionNew.add(bookCollectionNewBookToAttach);
            }
            bookCollectionNew = attachedBookCollectionNew;
            account.setBookCollection(bookCollectionNew);
            Collection<Product> attachedProductCollectionNew = new ArrayList<Product>();
            for (Product productCollectionNewProductToAttach : productCollectionNew) {
                productCollectionNewProductToAttach = em.getReference(productCollectionNewProductToAttach.getClass(), productCollectionNewProductToAttach.getId());
                attachedProductCollectionNew.add(productCollectionNewProductToAttach);
            }
            productCollectionNew = attachedProductCollectionNew;
            account.setProductCollection(productCollectionNew);
            account = em.merge(account);
            for (Orders ordersCollectionNewOrders : ordersCollectionNew) {
                if (!ordersCollectionOld.contains(ordersCollectionNewOrders)) {
                    Account oldCreateByOfOrdersCollectionNewOrders = ordersCollectionNewOrders.getCreateBy();
                    ordersCollectionNewOrders.setCreateBy(account);
                    ordersCollectionNewOrders = em.merge(ordersCollectionNewOrders);
                    if (oldCreateByOfOrdersCollectionNewOrders != null && !oldCreateByOfOrdersCollectionNewOrders.equals(account)) {
                        oldCreateByOfOrdersCollectionNewOrders.getOrdersCollection().remove(ordersCollectionNewOrders);
                        oldCreateByOfOrdersCollectionNewOrders = em.merge(oldCreateByOfOrdersCollectionNewOrders);
                    }
                }
            }
            for (OrderLine orderLineCollectionNewOrderLine : orderLineCollectionNew) {
                if (!orderLineCollectionOld.contains(orderLineCollectionNewOrderLine)) {
                    Account oldCreateByOfOrderLineCollectionNewOrderLine = orderLineCollectionNewOrderLine.getCreateBy();
                    orderLineCollectionNewOrderLine.setCreateBy(account);
                    orderLineCollectionNewOrderLine = em.merge(orderLineCollectionNewOrderLine);
                    if (oldCreateByOfOrderLineCollectionNewOrderLine != null && !oldCreateByOfOrderLineCollectionNewOrderLine.equals(account)) {
                        oldCreateByOfOrderLineCollectionNewOrderLine.getOrderLineCollection().remove(orderLineCollectionNewOrderLine);
                        oldCreateByOfOrderLineCollectionNewOrderLine = em.merge(oldCreateByOfOrderLineCollectionNewOrderLine);
                    }
                }
            }
            for (Book bookCollectionNewBook : bookCollectionNew) {
                if (!bookCollectionOld.contains(bookCollectionNewBook)) {
                    Account oldCreatedByOfBookCollectionNewBook = bookCollectionNewBook.getCreatedBy();
                    bookCollectionNewBook.setCreatedBy(account);
                    bookCollectionNewBook = em.merge(bookCollectionNewBook);
                    if (oldCreatedByOfBookCollectionNewBook != null && !oldCreatedByOfBookCollectionNewBook.equals(account)) {
                        oldCreatedByOfBookCollectionNewBook.getBookCollection().remove(bookCollectionNewBook);
                        oldCreatedByOfBookCollectionNewBook = em.merge(oldCreatedByOfBookCollectionNewBook);
                    }
                }
            }
            for (Product productCollectionNewProduct : productCollectionNew) {
                if (!productCollectionOld.contains(productCollectionNewProduct)) {
                    Account oldCreatedByOfProductCollectionNewProduct = productCollectionNewProduct.getCreatedBy();
                    productCollectionNewProduct.setCreatedBy(account);
                    productCollectionNewProduct = em.merge(productCollectionNewProduct);
                    if (oldCreatedByOfProductCollectionNewProduct != null && !oldCreatedByOfProductCollectionNewProduct.equals(account)) {
                        oldCreatedByOfProductCollectionNewProduct.getProductCollection().remove(productCollectionNewProduct);
                        oldCreatedByOfProductCollectionNewProduct = em.merge(oldCreatedByOfProductCollectionNewProduct);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = account.getId();
                if (findAccount(id) == null) {
                    throw new NonexistentEntityException("The account with id " + id + " no longer exists.");
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
            Account account;
            try {
                account = em.getReference(Account.class, id);
                account.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The account with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Orders> ordersCollectionOrphanCheck = account.getOrdersCollection();
            for (Orders ordersCollectionOrphanCheckOrders : ordersCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Account (" + account + ") cannot be destroyed since the Orders " + ordersCollectionOrphanCheckOrders + " in its ordersCollection field has a non-nullable createBy field.");
            }
            Collection<OrderLine> orderLineCollectionOrphanCheck = account.getOrderLineCollection();
            for (OrderLine orderLineCollectionOrphanCheckOrderLine : orderLineCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Account (" + account + ") cannot be destroyed since the OrderLine " + orderLineCollectionOrphanCheckOrderLine + " in its orderLineCollection field has a non-nullable createBy field.");
            }
            Collection<Book> bookCollectionOrphanCheck = account.getBookCollection();
            for (Book bookCollectionOrphanCheckBook : bookCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Account (" + account + ") cannot be destroyed since the Book " + bookCollectionOrphanCheckBook + " in its bookCollection field has a non-nullable createdBy field.");
            }
            Collection<Product> productCollectionOrphanCheck = account.getProductCollection();
            for (Product productCollectionOrphanCheckProduct : productCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Account (" + account + ") cannot be destroyed since the Product " + productCollectionOrphanCheckProduct + " in its productCollection field has a non-nullable createdBy field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(account);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Account> findAccountEntities() {
        return findAccountEntities(true, -1, -1);
    }

    public List<Account> findAccountEntities(int maxResults, int firstResult) {
        return findAccountEntities(false, maxResults, firstResult);
    }

    private List<Account> findAccountEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Account.class));
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

    public Account findAccount(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Account.class, id);
        } finally {
            em.close();
        }
    }

    public int getAccountCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Account> rt = cq.from(Account.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
