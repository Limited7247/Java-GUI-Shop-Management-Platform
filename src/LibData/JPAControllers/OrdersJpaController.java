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
import LibData.Models.Account;
import LibData.Models.OrderLine;
import LibData.Models.Orders;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Limited
 */
public class OrdersJpaController implements Serializable {

    public OrdersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Orders orders) throws PreexistingEntityException, Exception {
        if (orders.getOrderLineCollection() == null) {
            orders.setOrderLineCollection(new ArrayList<OrderLine>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Account createBy = orders.getCreateBy();
            if (createBy != null) {
                createBy = em.getReference(createBy.getClass(), createBy.getId());
                orders.setCreateBy(createBy);
            }
            Collection<OrderLine> attachedOrderLineCollection = new ArrayList<OrderLine>();
            for (OrderLine orderLineCollectionOrderLineToAttach : orders.getOrderLineCollection()) {
                orderLineCollectionOrderLineToAttach = em.getReference(orderLineCollectionOrderLineToAttach.getClass(), orderLineCollectionOrderLineToAttach.getId());
                attachedOrderLineCollection.add(orderLineCollectionOrderLineToAttach);
            }
            orders.setOrderLineCollection(attachedOrderLineCollection);
            em.persist(orders);
            if (createBy != null) {
                createBy.getOrdersCollection().add(orders);
                createBy = em.merge(createBy);
            }
            for (OrderLine orderLineCollectionOrderLine : orders.getOrderLineCollection()) {
                Orders oldOrderIdOfOrderLineCollectionOrderLine = orderLineCollectionOrderLine.getOrderId();
                orderLineCollectionOrderLine.setOrderId(orders);
                orderLineCollectionOrderLine = em.merge(orderLineCollectionOrderLine);
                if (oldOrderIdOfOrderLineCollectionOrderLine != null) {
                    oldOrderIdOfOrderLineCollectionOrderLine.getOrderLineCollection().remove(orderLineCollectionOrderLine);
                    oldOrderIdOfOrderLineCollectionOrderLine = em.merge(oldOrderIdOfOrderLineCollectionOrderLine);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOrders(orders.getId()) != null) {
                throw new PreexistingEntityException("Orders " + orders + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Orders orders) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orders persistentOrders = em.find(Orders.class, orders.getId());
            Account createByOld = persistentOrders.getCreateBy();
            Account createByNew = orders.getCreateBy();
            Collection<OrderLine> orderLineCollectionOld = persistentOrders.getOrderLineCollection();
            Collection<OrderLine> orderLineCollectionNew = orders.getOrderLineCollection();
            List<String> illegalOrphanMessages = null;
            for (OrderLine orderLineCollectionOldOrderLine : orderLineCollectionOld) {
                if (!orderLineCollectionNew.contains(orderLineCollectionOldOrderLine)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain OrderLine " + orderLineCollectionOldOrderLine + " since its orderId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (createByNew != null) {
                createByNew = em.getReference(createByNew.getClass(), createByNew.getId());
                orders.setCreateBy(createByNew);
            }
            Collection<OrderLine> attachedOrderLineCollectionNew = new ArrayList<OrderLine>();
            for (OrderLine orderLineCollectionNewOrderLineToAttach : orderLineCollectionNew) {
                orderLineCollectionNewOrderLineToAttach = em.getReference(orderLineCollectionNewOrderLineToAttach.getClass(), orderLineCollectionNewOrderLineToAttach.getId());
                attachedOrderLineCollectionNew.add(orderLineCollectionNewOrderLineToAttach);
            }
            orderLineCollectionNew = attachedOrderLineCollectionNew;
            orders.setOrderLineCollection(orderLineCollectionNew);
            orders = em.merge(orders);
            if (createByOld != null && !createByOld.equals(createByNew)) {
                createByOld.getOrdersCollection().remove(orders);
                createByOld = em.merge(createByOld);
            }
            if (createByNew != null && !createByNew.equals(createByOld)) {
                createByNew.getOrdersCollection().add(orders);
                createByNew = em.merge(createByNew);
            }
            for (OrderLine orderLineCollectionNewOrderLine : orderLineCollectionNew) {
                if (!orderLineCollectionOld.contains(orderLineCollectionNewOrderLine)) {
                    Orders oldOrderIdOfOrderLineCollectionNewOrderLine = orderLineCollectionNewOrderLine.getOrderId();
                    orderLineCollectionNewOrderLine.setOrderId(orders);
                    orderLineCollectionNewOrderLine = em.merge(orderLineCollectionNewOrderLine);
                    if (oldOrderIdOfOrderLineCollectionNewOrderLine != null && !oldOrderIdOfOrderLineCollectionNewOrderLine.equals(orders)) {
                        oldOrderIdOfOrderLineCollectionNewOrderLine.getOrderLineCollection().remove(orderLineCollectionNewOrderLine);
                        oldOrderIdOfOrderLineCollectionNewOrderLine = em.merge(oldOrderIdOfOrderLineCollectionNewOrderLine);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = orders.getId();
                if (findOrders(id) == null) {
                    throw new NonexistentEntityException("The orders with id " + id + " no longer exists.");
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
            Orders orders;
            try {
                orders = em.getReference(Orders.class, id);
                orders.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orders with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<OrderLine> orderLineCollectionOrphanCheck = orders.getOrderLineCollection();
            for (OrderLine orderLineCollectionOrphanCheckOrderLine : orderLineCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Orders (" + orders + ") cannot be destroyed since the OrderLine " + orderLineCollectionOrphanCheckOrderLine + " in its orderLineCollection field has a non-nullable orderId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Account createBy = orders.getCreateBy();
            if (createBy != null) {
                createBy.getOrdersCollection().remove(orders);
                createBy = em.merge(createBy);
            }
            em.remove(orders);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Orders> findOrdersEntities() {
        return findOrdersEntities(true, -1, -1);
    }

    public List<Orders> findOrdersEntities(int maxResults, int firstResult) {
        return findOrdersEntities(false, maxResults, firstResult);
    }

    private List<Orders> findOrdersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Orders.class));
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

    public Orders findOrders(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Orders.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrdersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Orders> rt = cq.from(Orders.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
