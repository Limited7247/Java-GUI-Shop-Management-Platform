/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage.Controllers;

import LibData.Models.Book;
import LibData.Models.OrderLine;
import LibData.Models.Product;
import LibData.Providers.BookProvider;
import LibData.Providers.InventoryProvider;
import LibData.Providers.ProductProvider;
import static LimitedSolution.Utilities.CurrencyHelper.VNDToInt;
import static LimitedSolution.Utilities.JTableHelper.TableColumnAdjuster;
import MainPackage.Models.Book.BookTableModel;
import MainPackage.Views.Book.BooksFrame;
import MainPackage.Views.Orders.NewOrderFrame;
import java.util.List;
import javax.swing.JOptionPane;
import static LimitedSolution.Utilities.JTableHelper.TableColumnAdjuster;
import MainPackage.Views.Orders.NewOrderTableModel;

/**
 *
 * @author Limited
 */
public class OrderController {

    private BookProvider _bookProvider = new BookProvider();
    private InventoryProvider _inventoryProvider = new InventoryProvider();
    private ProductProvider _productProvider = new ProductProvider();

    private static boolean CheckQuantityEnter(String quantity) {
        try {
            int Quantity = Integer.parseInt(quantity);
            return (Quantity > 0);
        } catch (Exception e) {
            return false;
        }
    }

    public void ShowBooksTable(NewOrderFrame frame) {
        BookTableModel model = new BookTableModel(_bookProvider.getAll());
        _inventoryProvider.addInventoryInformation(model);

        frame.booksTable.setModel(model);
        TableColumnAdjuster(frame.booksTable, 30);
    }

    public void Find(NewOrderFrame frame, String text) {
        List<Book> list = _bookProvider.Find(text);
        BookTableModel model = new BookTableModel(list);
        _inventoryProvider.addInventoryInformation(model);

        frame.booksTable.setModel(model);
        TableColumnAdjuster(frame.booksTable, 30);
        JOptionPane.showMessageDialog(
                frame,
                "Tìm thấy " + list.size() + " kết quả",
                "Tìm kiếm Sách",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public void AddOrderLineToTable(NewOrderFrame frame, Product product, String quantity) {
        Integer Quantity = VNDToInt(quantity);
        if (!(Quantity != null && Quantity >= 1 && Quantity <= 100)) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Số lượng không hợp lệ.",
                    "Thêm sản phẩm vào hóa đơn thất bại",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer InStock = _inventoryProvider.getInStockByProductId(product.getId());
        Integer newQuantity = Quantity;
        
        NewOrderTableModel model = (NewOrderTableModel) frame.orderLinesTable.getModel();
        if (model.contains(product.getIdCode())) {
            newQuantity = Quantity + model.getOrderLineByProductId(product.getId()).getQuantity();
        }

        if (InStock.compareTo(newQuantity) < 0) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Không đủ số lượng hàng yêu cầu." + '\n'
                    + "Tổng Yêu cầu: " + newQuantity + '\n'
                    + "Tồn kho: " + InStock,
                    "Thêm sản phẩm vào hóa đơn thất bại",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (product.getPrice() == null) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Sản phẩm chưa được thiết lập giá.",
                    "Thêm sản phẩm vào hóa đơn thất bại",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        OrderLine line = new OrderLine();
        line.setProductId(product);
        line.setQuantity(Quantity);
        line.setUnitPrice(product.getPrice().longValue());
        line.setTotalPrice(line.getQuantity() * line.getUnitPrice());
        line.setDiscountPrice(0);
        line.setVATPrice((long) (0.1 * line.getTotalPrice()));
        line.setPaidPrice(line.getTotalPrice());

        ((NewOrderTableModel) frame.orderLinesTable.getModel()).fireTableDataChanged(line);
        TableColumnAdjuster(frame.orderLinesTable, 50);

    }
}
