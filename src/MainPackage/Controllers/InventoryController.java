/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage.Controllers;

import LibData.Models.Account;
import LibData.Models.Book;
import LibData.Models.Inventory;
import LibData.Models.OrderLine;
import LibData.Models.Orders;
import LibData.Models.Product;
import LibData.Providers.InventoryProvider;
import LimitedSolution.Utilities.JTableHelper;
import static LimitedSolution.Utilities.JTableHelper.TableColumnAdjuster;
import MainPackage.Models.Inventory.InventoryTableModel;
import MainPackage.Models.Inventory.InventoryViewModel;
import MainPackage.Views.Inventory.InventoryFrame;
import java.util.List;
import javax.persistence.criteria.Order;
import javax.swing.JOptionPane;

/**
 *
 * @author Limited
 */
public class InventoryController {

    private InventoryProvider _inventoryProvider = new InventoryProvider();

    public InventoryController() {

    }

    public void ShowInventoryTable(InventoryFrame _inventoryFrame) {
        _inventoryFrame.inventoriesTable.setModel(new InventoryTableModel(_inventoryProvider.getAllUpToDate()));
        JTableHelper.TableColumnAdjuster(_inventoryFrame.inventoriesTable, 40);
    }

    public InventoryViewModel GetInventoryInformations(Inventory inventory) {
        InventoryViewModel model = new InventoryViewModel(inventory);

        Integer inStock = _inventoryProvider.getInStockByProductId(inventory.getProductId().getId());
        Integer in = _inventoryProvider.getInQuantitiesByProductId(inventory.getProductId().getId());
        Integer out = _inventoryProvider.getOutQuantitiesByProductId(inventory.getProductId().getId());

        model.InStock = inStock != null ? inStock + "" : "0";
        model.In = in != null ? in + "" : "0";
        model.Out = out != null ? out + "" : "0";

        return model;
    }

    private static boolean CheckQuantityEnter(String quantity) {
        try {
            int Quantity = Integer.parseInt(quantity);
            return (Quantity > 0);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean InventoryIn(InventoryFrame inventoryFrame, Inventory inventory, String quantity, Account _account) {
        if (!CheckQuantityEnter(quantity)) {
            JOptionPane.showMessageDialog(
                    inventoryFrame,
                    "Dữ liệu không hợp lệ.",
                    "Nhập kho",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        int Quantity = Integer.parseInt(quantity);

        if (JOptionPane.showConfirmDialog(
                inventoryFrame,
                "Bạn chắc chắn muốn nhập kho " + '\n'
                + "Sản phẩm: " + inventory.getProductId().getName() + '\n'
                + "Số lượng: " + Quantity + "?",
                "Nhập kho",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            if (_inventoryProvider.InventoryIn(inventory.getId(), Quantity, _account)) {
                JOptionPane.showMessageDialog(
                        inventoryFrame,
                        "Nhập kho " + '\n'
                        + "Sản phẩm: " + inventory.getProductId().getName() + '\n'
                        + "Số lượng: " + Quantity + '\n'
                        + "Thành công.",
                        "Nhập kho",
                        JOptionPane.INFORMATION_MESSAGE);
                return true;
            }

            JOptionPane.showMessageDialog(
                    inventoryFrame,
                    "Nhập kho " + '\n'
                    + "Sản phẩm: " + inventory.getProductId().getName() + '\n'
                    + "Số lượng: " + Quantity + '\n'
                    + "Thất bại.",
                    "Nhập kho",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public void Find(InventoryFrame inventoryFrame, String text) {
        List<Inventory> list = _inventoryProvider.Find(text);
        inventoryFrame.inventoriesTable.setModel(new InventoryTableModel(list));
        TableColumnAdjuster(inventoryFrame.inventoriesTable, 40);
        JOptionPane.showMessageDialog(
                inventoryFrame,
                "Tìm thấy " + list.size() + " kết quả",
                "Tìm kiếm Kiểm kê",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public boolean InventoryOut(InventoryFrame inventoryFrame, Inventory inventory, String quantity, Account _account) {
        if (!CheckQuantityEnter(quantity)) {
            JOptionPane.showMessageDialog(
                    inventoryFrame,
                    "Dữ liệu không hợp lệ.",
                    "Xuất kho",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        int Quantity = Integer.parseInt(quantity);

        if (JOptionPane.showConfirmDialog(
                inventoryFrame,
                "Bạn chắc chắn muốn xuất kho " + '\n'
                + "Sản phẩm: " + inventory.getProductId().getName() + '\n'
                + "Số lượng: " + Quantity + "?",
                "Xuất kho",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            if (_inventoryProvider.InventoryOut(inventory.getId(), Quantity, _account)) {
                JOptionPane.showMessageDialog(
                        inventoryFrame,
                        "Xuất kho " + '\n'
                        + "Sản phẩm: " + inventory.getProductId().getName() + '\n'
                        + "Số lượng: " + Quantity + '\n'
                        + "Thành công.",
                        "Xuất kho",
                        JOptionPane.INFORMATION_MESSAGE);
                return true;
            }

            JOptionPane.showMessageDialog(
                    inventoryFrame,
                    "Xuất kho " + '\n'
                    + "Sản phẩm: " + inventory.getProductId().getName() + '\n'
                    + "Số lượng: " + Quantity + '\n'
                    + "Thất bại.",
                    "Xuất kho",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public boolean InventoryCheckout(InventoryFrame inventoryFrame, Inventory inventory, String quantity, Account _account) {
        if (!CheckQuantityEnter(quantity)) {
            JOptionPane.showMessageDialog(
                    inventoryFrame,
                    "Dữ liệu không hợp lệ.",
                    "Kiểm kê",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        int Quantity = Integer.parseInt(quantity);

        if (JOptionPane.showConfirmDialog(
                inventoryFrame,
                "Bạn chắc chắn muốn cập nhật " + '\n'
                + "Sản phẩm: " + inventory.getProductId().getName() + '\n'
                + "Số lượng: " + Quantity + "?",
                "Kiểm kê",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            if (_inventoryProvider.InventoryCheckout(inventory.getId(), Quantity, _account)) {
                JOptionPane.showMessageDialog(
                        inventoryFrame,
                        "Cập nhật " + '\n'
                        + "Sản phẩm: " + inventory.getProductId().getName() + '\n'
                        + "Số lượng: " + Quantity + '\n'
                        + "Thành công.",
                        "Kiểm kê",
                        JOptionPane.INFORMATION_MESSAGE);
                return true;
            }

            JOptionPane.showMessageDialog(
                    inventoryFrame,
                    "Cập nhật " + '\n'
                    + "Sản phẩm: " + inventory.getProductId().getName() + '\n'
                    + "Số lượng: " + Quantity + '\n'
                    + "Thất bại.",
                    "Kiểm kê",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}
