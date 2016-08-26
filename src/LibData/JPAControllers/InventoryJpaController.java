/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibData.JPAControllers;

import LibData.JPAControllers.exceptions.NonexistentEntityException;
import LibData.JPAControllers.exceptions.PreexistingEntityException;
import LibData.Models.Inventory;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import LibData.Models.Product;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Limited
 */
public class InventoryJpaController implements Serializable {

    public InventoryJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Inventory inventory) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Product productId = inventory.getProductId();
            if (productId != null) {
                productId = em.getReference(productId.getClass(), productId.getId());
                inventory.setProductId(productId);
            }
            em.persist(inventory);
            if (productId != null) {
                productId.getInventoryCollection().add(inventory);
                productId = em.merge(productId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findInventory(inventory.getId()) != null) {
                throw new PreexistingEntityException("Inventory " + inventory + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Inventory inventory) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inventory persistentInventory = em.find(Inventory.class, inventory.getId());
            Product productIdOld = persistentInventory.getProductId();
            Product productIdNew = inventory.getProductId();
            if (productIdNew != null) {
                productIdNew = em.getReference(productIdNew.getClass(), productIdNew.getId());
                inventory.setProductId(productIdNew);
            }
            inventory = em.merge(inventory);
            if (productIdOld != null && !productIdOld.equals(productIdNew)) {
                productIdOld.getInventoryCollection().remove(inventory);
                productIdOld = em.merge(productIdOld);
            }
            if (productIdNew != null && !productIdNew.equals(productIdOld)) {
                productIdNew.getInventoryCollection().add(inventory);
                productIdNew = em.merge(productIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = inventory.getId();
                if (findInventory(id) == null) {
                    throw new NonexistentEntityException("The inventory with id " + id + " no longer exists.");
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
            Inventory inventory;
            try {
                inventory = em.getReference(Inventory.class, id);
                inventory.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inventory with id " + id + " no longer exists.", enfe);
            }
            Product productId = inventory.getProductId();
            if (productId != null) {
                productId.getInventoryCollection().remove(inventory);
                productId = em.merge(productId);
            }
            em.remove(inventory);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Inventory> findInventoryEntities() {
        return findInventoryEntities(true, -1, -1);
    }

    public List<Inventory> findInventoryEntities(int maxResults, int firstResult) {
        return findInventoryEntities(false, maxResults, firstResult);
    }

    private List<Inventory> findInventoryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Inventory.class));
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

    public Inventory findInventory(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Inventory.class, id);
        } finally {
            em.close();
        }
    }

    public int getInventoryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Inventory> rt = cq.from(Inventory.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
