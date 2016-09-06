/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage.Models.Inventory;

/**
 *
 * @author Limited
 */
public class InventoryInformations {
    public String ProductId;
    public String InStock;
    public String InQuantity;
    public String OutQuantity;

    public InventoryInformations(String ProductId, String InStock, String InQuantity, String OutQuantity) {
        this.ProductId = ProductId;
        this.InStock = InStock;
        this.InQuantity = InQuantity;
        this.OutQuantity = OutQuantity;
    }
    
    
}
