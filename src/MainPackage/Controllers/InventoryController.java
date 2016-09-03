/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage.Controllers;

import LibData.Models.Book;
import LibData.Models.Inventory;
import LibData.Models.OrderLine;
import LibData.Models.Orders;
import LibData.Models.Product;
import LibData.Providers.InventoryProvider;
import LimitedSolution.Utilities.JTableHelper;
import MainPackage.Models.Inventory.InventoryTableModel;
import MainPackage.Views.Inventory.InventoryFrame;
import javax.persistence.criteria.Order;

/**
 *
 * @author Limited
 */
public class InventoryController {
    
    private InventoryProvider _inventoryProvider = new InventoryProvider();
    
    public InventoryController()
    {
        
    }

    public void ShowInventoryTable(InventoryFrame _inventoryFrame) {
        _inventoryFrame.inventoriesTable.setModel(new InventoryTableModel(_inventoryProvider.getAllUpToDate()));
        JTableHelper.TableColumnAdjuster(_inventoryFrame.inventoriesTable, 5);
    }
}
