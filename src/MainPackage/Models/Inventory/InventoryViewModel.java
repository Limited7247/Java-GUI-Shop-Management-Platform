/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage.Models.Inventory;

import LibData.Models.Inventory;
import static LimitedSolution.Utilities.CurrencyHelper.IntToVND;

/**
 *
 * @author Limited
 */
public class InventoryViewModel {
    public String IdCode;
    public String Price;
    public String Name;
    public String Type;
    
    public String InStock;
    public String In;
    public String Out;       
    
    public InventoryViewModel(Inventory inventory)
    {
        this.IdCode = inventory.getIdCode();
        this.Price 
                = inventory.getProductId().getPrice() != null 
                ? IntToVND(inventory.getProductId().getPrice())
                : "";
        this.Name = inventory.getProductId().getName();
        this.Type = inventory.getProductId().getType();
    }
}
