/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage.Controllers;

import LibData.Models.Account;
import LibData.Models.Book;
import LibData.Models.OrderLine;
import LibData.Models.Orders;
import LibData.Models.Product;
import LibData.Providers.BookProvider;
import LibData.Providers.InventoryProvider;
import LibData.Providers.OrdersProvider;
import LibData.Providers.ProductProvider;
import static LimitedSolution.Utilities.CurrencyHelper.VNDToInt;
import static LimitedSolution.Utilities.JTableHelper.TableColumnAdjuster;
import MainPackage.Models.Book.BookTableModel;
import MainPackage.Views.Book.BooksFrame;
import MainPackage.Views.Orders.NewOrderFrame;
import java.util.List;
import javax.swing.JOptionPane;
import static LimitedSolution.Utilities.JTableHelper.TableColumnAdjuster;
import MainPackage.Models.Orders.NewOrderTableModel;
import MainPackage.Models.Orders.NewOrderViewModel;
import MainPackage.Views.Orders.OrderInformationsFrame;
import javax.persistence.criteria.Order;

/**
 *
 * @author Limited
 */
public class OrderController {

    private BookProvider _bookProvider = new BookProvider();
    private InventoryProvider _inventoryProvider = new InventoryProvider();
    private ProductProvider _productProvider = new ProductProvider();
    private OrdersProvider _ordersProvider = new OrdersProvider();

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

        model.fireTableDataChanged(line);
        TableColumnAdjuster(frame.orderLinesTable, 30);

    }

    public void UpdateOrderLineToOrderLinesTable(NewOrderFrame frame, OrderLine orderLine, String quantity) {
        Integer Quantity = VNDToInt(quantity);
        if (!(Quantity != null && Quantity >= 1 && Quantity <= 100)) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Số lượng không hợp lệ.",
                    "Cập nhật hóa đơn thất bại",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer InStock = _inventoryProvider.getInStockByProductId(orderLine.getProductId().getId());

        if (InStock.compareTo(Quantity) < 0) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Không đủ số lượng hàng yêu cầu." + '\n'
                    + "Tổng Yêu cầu: " + Quantity + '\n'
                    + "Tồn kho: " + InStock,
                    "Cập nhật hóa đơn thất bại",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        orderLine.setQuantity(Quantity);
        orderLine.setTotalPrice(orderLine.getQuantity() * orderLine.getUnitPrice());
        orderLine.setDiscountPrice(0);
        orderLine.setVATPrice((long) (0.1 * orderLine.getTotalPrice()));

        ((NewOrderTableModel) frame.orderLinesTable.getModel()).fireTableDataChanged();
        TableColumnAdjuster(frame.orderLinesTable, 30);
    }

    public void DeleteOrderLineFromOrderLinesTable(NewOrderFrame frame, OrderLine orderLine) {
        NewOrderTableModel model = (NewOrderTableModel) frame.orderLinesTable.getModel();
        model.removeLine(orderLine);

        model.fireTableDataChanged();
        TableColumnAdjuster(frame.orderLinesTable, 30);
    }

    public boolean MakeOrder(NewOrderFrame frame, NewOrderViewModel model, Account _account) {
        if (!model.IsValidate()) {
            JOptionPane.showMessageDialog(
                    frame,
                    model.MessageValidate(),
                    "Dữ liệu không hợp lệ",
                    JOptionPane.WARNING_MESSAGE
            );
            return false;
        }

        if (JOptionPane.showConfirmDialog(
                frame,
                "Bạn chắc chắn muốn lập hóa đơn?" + '\n'
                + "Tổng tiền thanh toán: " + model.PaidPrice,
                "Lập hóa đơn",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            
            Orders order = model.getOrders();
            order.setCreateBy(_account);
            if (_ordersProvider.Insert(order)) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Lập hóa đơn thành công",
                        "Lập hóa đơn",
                        JOptionPane.INFORMATION_MESSAGE
                );
                
                OrderInformationsFrame orderInformationFrame 
                        = new OrderInformationsFrame(_ordersProvider.getById(order.getId()));
                orderInformationFrame.show();
                
                return true;
            }

            JOptionPane.showMessageDialog(
                    frame,
                    "Lập hóa đơn thất bại",
                    "Lập hóa đơn",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }

        return false;
    }
}
