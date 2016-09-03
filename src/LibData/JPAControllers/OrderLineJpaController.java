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
import LibData.Models.Orders;
import LibData.Models.Product;
import LibData.Models.Inventory;
import LibData.Models.OrderLine;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Limited
 */
public class OrderLineJpaController implements Serializable {

    public OrderLineJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(OrderLine orderLine) throws PreexistingEntityException, Exception {
        if (orderLine.getInventoryCollection() == null) {
            orderLine.setInventoryCollection(new ArrayList<Inventory>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Account createBy = orderLine.getCreateBy();
            if (createBy != null) {
                createBy = em.getReference(createBy.getClass(), createBy.getId());
                orderLine.setCreateBy(createBy);
            }
            Orders orderId = orderLine.getOrderId();
            if (orderId != null) {
                orderId = em.getReference(orderId.getClass(), orderId.getId());
                orderLine.setOrderId(orderId);
            }
            Product productId = orderLine.getProductId();
            if (productId != null) {
                productId = em.getReference(productId.getClass(), productId.getId());
                orderLine.setProductId(productId);
            }
            Collection<Inventory> attachedInventoryCollection = new ArrayList<Inventory>();
            for (Inventory inventoryCollectionInventoryToAttach : orderLine.getInventoryCollection()) {
                inventoryCollectionInventoryToAttach = em.getReference(inventoryCollectionInventoryToAttach.getClass(), inventoryCollectionInventoryToAttach.getId());
                attachedInventoryCollection.add(inventoryCollectionInventoryToAttach);
            }
            orderLine.setInventoryCollection(attachedInventoryCollection);
            em.persist(orderLine);
            if (createBy != null) {
                createBy.getOrderLineCollection().add(orderLine);
                createBy = em.merge(createBy);
            }
            if (orderId != null) {
                orderId.getOrderLineCollection().add(orderLine);
                orderId = em.merge(orderId);
            }
            if (productId != null) {
                productId.getOrderLineCollection().add(orderLine);
                productId = em.merge(productId);
            }
            for (Inventory inventoryCollectionInventory : orderLine.getInventoryCollection()) {
                inventoryCollectionInventory.getOrderLineCollection().add(orderLine);
                inventoryCollectionInventory = em.merge(inventoryCollectionInventory);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOrderLine(orderLine.getId()) != null) {
                throw new PreexistingEntityException("OrderLine " + orderLine + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(OrderLine orderLine) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OrderLine persistentOrderLine = em.find(OrderLine.class, orderLine.getId());
            Account createByOld = persistentOrderLine.getCreateBy();
            Account createByNew = orderLine.getCreateBy();
            Orders orderIdOld = persistentOrderLine.getOrderId();
            Orders orderIdNew = orderLine.getOrderId();
            Product productIdOld = persistentOrderLine.getProductId();
            Product productIdNew = orderLine.getProductId();
            Collection<Inventory> inventoryCollectionOld = persistentOrderLine.getInventoryCollection();
            Collection<Inventory> inventoryCollectionNew = orderLine.getInventoryCollection();
            if (createByNew != null) {
                createByNew = em.getReference(createByNew.getClass(), createByNew.getId());
                orderLine.setCreateBy(createByNew);
            }
            if (orderIdNew != null) {
                orderIdNew = em.getReference(orderIdNew.getClass(), orderIdNew.getId());
                orderLine.setOrderId(orderIdNew);
            }
            if (productIdNew != null) {
                productIdNew = em.getReference(productIdNew.getClass(), productIdNew.getId());
                orderLine.setProductId(productIdNew);
            }
            Collection<Inventory> attachedInventoryCollectionNew = new ArrayList<Inventory>();
            for (Inventory inventoryCollectionNewInventoryToAttach : inventoryCollectionNew) {
                inventoryCollectionNewInventoryToAttach = em.getReference(inventoryCollectionNewInventoryToAttach.getClass(), inventoryCollectionNewInventoryToAttach.getId());
                attachedInventoryCollectionNew.add(inventoryCollectionNewInventoryToAttach);
            }
            inventoryCollectionNew = attachedInventoryCollectionNew;
            orderLine.setInventoryCollection(inventoryCollectionNew);
            orderLine = em.merge(orderLine);
            if (createByOld != null && !createByOld.equals(createByNew)) {
                createByOld.getOrderLineCollection().remove(orderLine);
                createByOld = em.merge(createByOld);
            }
            if (createByNew != null && !createByNew.equals(createByOld)) {
                createByNew.getOrderLineCollection().add(orderLine);
                createByNew = em.merge(createByNew);
            }
            if (orderIdOld != null && !orderIdOld.equals(orderIdNew)) {
                orderIdOld.getOrderLineCollection().remove(orderLine);
                orderIdOld = em.merge(orderIdOld);
            }
            if (orderIdNew != null && !orderIdNew.equals(orderIdOld)) {
                orderIdNew.getOrderLineCollection().add(orderLine);
                orderIdNew = em.merge(orderIdNew);
            }
            if (productIdOld != null && !productIdOld.equals(productIdNew)) {
                productIdOld.getOrderLineCollection().remove(orderLine);
                productIdOld = em.merge(productIdOld);
            }
            if (productIdNew != null && !productIdNew.equals(productIdOld)) {
                productIdNew.getOrderLineCollection().add(orderLine);
                productIdNew = em.merge(productIdNew);
            }
            for (Inventory inventoryCollectionOldInventory : inventoryCollectionOld) {
                if (!inventoryCollectionNew.contains(inventoryCollectionOldInventory)) {
                    inventoryCollectionOldInventory.getOrderLineCollection().remove(orderLine);
                    inventoryCollectionOldInventory = em.merge(inventoryCollectionOldInventory);
                }
            }
            for (Inventory inventoryCollectionNewInventory : inventoryCollectionNew) {
                if (!inventoryCollectionOld.contains(inventoryCollectionNewInventory)) {
                    inventoryCollectionNewInventory.getOrderLineCollection().add(orderLine);
                    inventoryCollectionNewInventory = em.merge(inventoryCollectionNewInventory);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = orderLine.getId();
                if (findOrderLine(id) == null) {
                    throw new NonexistentEntityException("The orderLine with id " + id + " no longer exists.");
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
            OrderLine orderLine;
            try {
                orderLine = em.getReference(OrderLine.class, id);
                orderLine.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orderLine with id " + id + " no longer exists.", enfe);
            }
            Account createBy = orderLine.getCreateBy();
            if (createBy != null) {
                createBy.getOrderLineCollection().remove(orderLine);
                createBy = em.merge(createBy);
            }
            Orders orderId = orderLine.getOrderId();
            if (orderId != null) {
                orderId.getOrderLineCollection().remove(orderLine);
                orderId = em.merge(orderId);
            }
            Product productId = orderLine.getProductId();
            if (productId != null) {
                productId.getOrderLineCollection().remove(orderLine);
                productId = em.merge(productId);
            }
            Collection<Inventory> inventoryCollection = orderLine.getInventoryCollection();
            for (Inventory inventoryCollectionInventory : inventoryCollection) {
                inventoryCollectionInventory.getOrderLineCollection().remove(orderLine);
                inventoryCollectionInventory = em.merge(inventoryCollectionInventory);
            }
            em.remove(orderLine);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<OrderLine> findOrderLineEntities() {
        return findOrderLineEntities(true, -1, -1);
    }

    public List<OrderLine> findOrderLineEntities(int maxResults, int firstResult) {
        return findOrderLineEntities(false, maxResults, firstResult);
    }

    private List<OrderLine> findOrderLineEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OrderLine.class));
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

    public OrderLine findOrderLine(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OrderLine.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrderLineCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<OrderLine> rt = cq.from(OrderLine.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
