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

    private JinqJPAStreamProvider streams = new JinqJPAStreamProvider(getEntityManagerFactory());

    private InventoryJpaController getJPAInventories() {
        return new InventoryJpaController(getEntityManagerFactory());
    }

    private JPAJinqStream<Inventory> getJinqInventories() {
        return streams.streamAll(getEntityManager(), Inventory.class);
    }

    private void showLog(Exception ex) {
        Logger.getLogger(InventoryProvider.class.getName()).log(Level.SEVERE, null, ex);
    }

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
                    .where(m -> m.getStatus() == INVENTORY_STATUS_UPTODATE)
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
                    Saved(inventory.getId());
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

}
