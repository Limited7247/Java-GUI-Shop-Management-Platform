/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibData.Providers;

import LibData.Business.Configs.InventoryConfigs;
import static LibData.Business.Configs.InventoryConfigs.*;
import LibData.JPAControllers.BookJpaController;
import LibData.JPAControllers.InventoryJpaController;
import LibData.Models.Account;
import LibData.Models.Inventory;
import static LibData.Providers.ProviderHelper.*;
import LimitedSolution.Utilities.LibDataUtilities.ProviderUtilities.IProvider;
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
public class InventoryProvider implements IProvider {

    private InventoryJpaController jpaInventories = new InventoryJpaController(getEntityManagerFactory());

    private InventoryJpaController getJPAInventories() {
        RefreshDatabase(jpaInventories.getEntityManager());
        return jpaInventories;
    }

    private JPAJinqStream<Inventory> getJinqInventories() {
        return getJinqStream().streamAll(getEntityManager(), Inventory.class);
    }

    private void showLog(Exception ex) {
        Logger.getLogger(InventoryProvider.class.getName()).log(Level.SEVERE, null, ex);
    }

    private ProductProvider _productProvider = new ProductProvider();

    public List<Inventory> getAll() {
        try {
            return getJinqInventories().sortedDescendingBy(m -> m.getCreateTime()).toList();
        } catch (Exception e) {
            showLog(e);
            return null;
        }
    }

    public List<Inventory> getAllUpToDate() {
        try {
            return getJinqInventories()
                    .where(m -> m.getType() == INVENTORY_TYPE_INIT)
                    .sortedDescendingBy(m -> m.getCreateTime())
                    .toList();

        } catch (Exception e) {
            showLog(e);
            return null;
        }
    }

    private Inventory getById(String id) {
        try {
            return getJPAInventories().findInventory(id);
        } catch (Exception e) {
            return null;
        }
    }

    private List<Inventory> getByProductId(String id) {
        try {
            return getJinqInventories()
                    .where(m -> m.getProductId().getId().equals(id))
                    .sortedDescendingBy(m -> m.getCreateTime())
                    .toList();

        } catch (Exception e) {
            showLog(e);
            return null;
        }
    }

    private String getNewIdCode() {
        try {
            String key = "I";
            if (!new ConfigsProvider().IncreaseValueByKey(key)) {
                return "";
            }

            return key + "-" + String.format("%1$06d", Long.parseLong(new ConfigsProvider().getValueByKey(key)));
        } catch (Exception ex) {
            showLog(ex);
            return "";
        }
    }

    public Integer getInStockByProductId(String productId) {
        try {
            for (Inventory inventory : getByProductId(productId)) {
                if (inventory.getType() == INVENTORY_TYPE_INIT
                        && inventory.getStatus() == INVENTORY_STATUS_UPTODATE) {

                    return inventory.getQuantity();
                }
            }

            return null;
        } catch (Exception e) {
            showLog(e);
            return null;
        }
    }

    public Integer getInQuantitiesByProductId(String productId) {
        try {
            Integer counter = 0;

            for (Inventory inventory : getByProductId(productId)) {
                if (inventory.getType() == INVENTORY_TYPE_IN) {
                    counter += inventory.getQuantity();
                }
            }

            return counter;
        } catch (Exception e) {
            showLog(e);
            return null;
        }
    }

    public Integer getOutQuantitiesByProductId(String productId) {
        try {
            Integer counter = 0;

            for (Inventory inventory : getByProductId(productId)) {
                if (inventory.getType() == INVENTORY_TYPE_OUT) {
                    counter += inventory.getQuantity();
                }
            }

            return counter;
        } catch (Exception e) {
            showLog(e);
            return null;
        }
    }

    public boolean Saved(String id) {
        try {
            Inventory inventory = getById(id);
            inventory.setStatus(INVENTORY_STATUS_SAVED);
            getJPAInventories().edit(inventory);
            return true;
        } catch (Exception e) {
            showLog(e);
            return false;
        }
    }

    public boolean SavedTheRest(String id) {
        try {
            List<Inventory> list = getByProductId(getById(id).getProductId().getId());

            if (list != null) {
                for (Inventory inventory : list) {
                    if (!inventory.getId().toUpperCase().equals(id.toUpperCase())) {
                        Saved(inventory.getId());
                    }
                }
            }

            return true;
        } catch (Exception e) {
            showLog(e);
            return false;
        }
    }

    @Override
    public boolean Insert(Object object) {
        try {
            Inventory inventory = (Inventory) object;
            inventory.setId(UUID.randomUUID().toString());
            inventory.setIdCode(getNewIdCode());
            inventory.setCreateTime(new Date());
            inventory.setStatus(INVENTORY_STATUS_UPTODATE);

            getJPAInventories().create(inventory);
            return true;
        } catch (Exception e) {
            showLog(e);
            return false;
        }
    }

    public boolean InsertInit(Object object) {
        try {
            Inventory inventory = (Inventory) object;
            inventory.setId(UUID.randomUUID().toString());
            inventory.setIdCode(getNewIdCode());
            inventory.setCreateTime(new Date());
            inventory.setStatus(INVENTORY_STATUS_UPTODATE);

            getJPAInventories().create(inventory);
            SavedTheRest(inventory.getId());
            return true;
        } catch (Exception e) {
            showLog(e);
            return false;
        }
    }

    @Override
    public boolean Delete(String id) {
        try {
            getJPAInventories().destroy(id);
            return true;
        } catch (Exception e) {
            showLog(e);
            return false;
        }
    }

    public boolean DeleteByProductId(String productId) {
        try {
            Delete(getByProductId(productId).get(0).getId());
            return true;
        } catch (Exception e) {
            showLog(e);
            return false;
        }
    }

    @Override
    public boolean Update(Object object) {
        try {
            Inventory inventory = (Inventory) object;
            Inventory updateInventory = getById(inventory.getId());
            updateInventory.setQuantity(inventory.getQuantity());

            getJPAInventories().edit(updateInventory);
            return true;
        } catch (Exception e) {
            showLog(e);
            return false;
        }
    }

    public boolean InventoryIn(String inventoryId, int Quantity, Account _account) {
        try {
            Inventory initInventory = getById(inventoryId);

            Inventory inventory = new Inventory();
            inventory.setProductId(initInventory.getProductId());
            inventory.setQuantity(Quantity);
            inventory.setCreateBy(_account);
            inventory.setUnit(initInventory.getUnit());
            inventory.setType(INVENTORY_TYPE_IN);
            Insert(inventory);

            initInventory.setQuantity(initInventory.getQuantity() + Quantity);
            Update(initInventory);

            SavedTheRest(initInventory.getId());
            return true;
        } catch (Exception e) {
            showLog(e);
            return false;
        }
    }

    public boolean InventoryOut(String inventoryId, int Quantity, Account _account) {
        try {
            Inventory initInventory = getById(inventoryId);

            Inventory inventory = new Inventory();
            inventory.setProductId(initInventory.getProductId());
            inventory.setQuantity(Quantity);
            inventory.setCreateBy(_account);
            inventory.setUnit(initInventory.getUnit());
            inventory.setType(INVENTORY_TYPE_OUT);
            Insert(inventory);

            if (initInventory.getQuantity() - Quantity < 0) {
                return false;
            }

            initInventory.setQuantity(initInventory.getQuantity() - Quantity);
            Update(initInventory);

            SavedTheRest(initInventory.getId());
            return true;
        } catch (Exception e) {
            showLog(e);
            return false;
        }
    }

    public boolean InventoryCheckout(String inventoryId, int Quantity, Account _account) {
        try {
            Inventory initInventory = getById(inventoryId);

            Inventory inventory = new Inventory();
            inventory.setProductId(initInventory.getProductId());
            inventory.setQuantity(Quantity);
            inventory.setCreateBy(_account);
            inventory.setUnit(initInventory.getUnit());
            inventory.setType(INVENTORY_TYPE_UPDATE);
            Insert(inventory);

            if (Quantity < 0) {
                return false;
            }

            initInventory.setQuantity(Quantity);
            Update(initInventory);

            SavedTheRest(initInventory.getId());
            return true;
        } catch (Exception e) {
            showLog(e);
            return false;
        }
    }

    public List<Inventory> Find(String findString) {
        try {
            if (findString == null || findString.isEmpty()) {
                return getAllUpToDate();
            }

            return getJinqInventories()
                    .where(
                            m -> m.getIdCode().contains(findString)
                            || m.getProductId().getIdCode().contains(findString)
                            || m.getProductId().getName().contains(findString)
                            && m.getType() == INVENTORY_TYPE_INIT
                    )
                    .toList();
        } catch (Exception e) {
            return getAll();
        }
    }

}
