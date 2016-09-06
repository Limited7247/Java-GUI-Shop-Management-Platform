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
import LibData.Providers.AccountProvider;
import LibData.Providers.BookProvider;
import LibData.Providers.InventoryProvider;
import LibData.Providers.OrdersProvider;
import LibData.Providers.ProductProvider;
import static LimitedSolution.Utilities.CurrencyHelper.VNDToInt;
import static LimitedSolution.Utilities.DateTimeHelper.getCurrentDateString;
import static LimitedSolution.Utilities.DateTimeHelper.getCurrentTimeString;
import static LimitedSolution.Utilities.JTableHelper.TableColumnAdjuster;
import MainPackage.Models.Book.BookTableModel;
import MainPackage.Views.Book.BooksFrame;
import MainPackage.Views.Orders.NewOrderFrame;
import java.util.List;
import javax.swing.JOptionPane;
import MainPackage.Models.Orders.NewOrderTableModel;
import MainPackage.Models.Orders.NewOrderViewModel;
import MainPackage.Models.Orders.OrdersTableModel;
import MainPackage.Views.Orders.OrderInformationsFrame;
import MainPackage.Views.Orders.OrdersFrame;
import javax.persistence.criteria.Order;
import static LimitedSolution.Utilities.JTableHelper.TableColumnAdjuster;
import MainPackage.Models.Book.BookViewModel;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import static LimitedSolution.Utilities.DateTimeHelper.*;
import static LimitedSolution.Utilities.CurrencyHelper.*;
import java.awt.Frame;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;

/**
 *
 * @author Limited
 */
public class OrderController {

    private BookProvider _bookProvider = new BookProvider();
    private InventoryProvider _inventoryProvider = new InventoryProvider();
    private ProductProvider _productProvider = new ProductProvider();
    private OrdersProvider _ordersProvider = new OrdersProvider();
    private AccountProvider _accountProvider = new AccountProvider();

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
                        = new OrderInformationsFrame(_ordersProvider.getById(order.getId()), _account);
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

    public void ShowOrdersTable(OrdersFrame frame) {
        OrdersTableModel model = new OrdersTableModel(_ordersProvider.getAll());

        frame.ordersTable.setModel(model);
        TableColumnAdjuster(frame.ordersTable, 30);
    }

    public void FindOrders(OrdersFrame frame, String text) {
        List<Orders> list = _ordersProvider.Find(text);
        OrdersTableModel model = new OrdersTableModel(list);

        frame.ordersTable.setModel(model);
        TableColumnAdjuster(frame.ordersTable, 30);
        JOptionPane.showMessageDialog(
                frame,
                "Tìm thấy " + list.size() + " kết quả",
                "Tìm kiếm Hóa đơn",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public void Print(Frame frame, Orders order, Account _account) {
        try {
            XWPFDocument document = new XWPFDocument();
            File file = new File("Hóa đơn " + order.getIdCode() + ".doc");
            if (file.exists()) {
                file.createNewFile();
            }

            FileOutputStream out
                    = new FileOutputStream(file);

            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.CENTER);

            XWPFRun run;
//            BookViewModel bookView = new BookViewModel(book);

            /////////////////
            run = createFieldRun(paragraph, "CHI TIẾT HÓA ĐƠN");
            run.setFontSize(24);

            paragraph = createPrintInformation(document, _account);
            paragraph = createBookProductInformation(document, order);
            paragraph = createBookInformation(document, order);

            //create table
            XWPFTable table = document.createTable();
            setTableAlignment(table, STJc.CENTER);
            table.setCellMargins(50, 50, 50, 50);
            table.setInsideHBorder(XWPFTable.XWPFBorderType.SINGLE, 10, 10, "");
            table.setInsideVBorder(XWPFTable.XWPFBorderType.NONE, 20, 20, "");
            //create first row
            XWPFTableRow row = table.getRow(0);
            row.setHeight(40);
            row.getCell(0).setText("STT");
            row.addNewTableCell().setText("Mã Sản phẩm");
            row.addNewTableCell().setText("Tên Sản phẩm");
            row.addNewTableCell().setText("Đơn vị");
            row.addNewTableCell().setText("Số lượng");
            row.addNewTableCell().setText("Giá tiền");
            row.addNewTableCell().setText("Thành tiền");

            List<OrderLine> list = (List<OrderLine>) order.getOrderLineCollection();
            for (int i = 0; i < list.size(); i++) {
                OrderLine line = list.get(i);

                row = table.createRow();
                row.getCell(0).setText((i + 1) + "");
                row.getCell(1).setText(line.getProductId().getIdCode());
                row.getCell(2).setText(line.getProductId().getName());
                row.getCell(3).setText("Quyển     ");
                row.getCell(4).setText(line.getQuantity() + "     ");
                row.getCell(5).setText(IntToVND(line.getUnitPrice()) + "     ");
                row.getCell(6).setText(IntToVND(line.getTotalPrice()) + "     ");
            }

            document.write(out);
            out.close();

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }

            JOptionPane.showMessageDialog(
                    frame,
                    "Xuất file " + file.getName() + " thành công" + '\n'
                    + "Tại ví trí: " + file.getAbsolutePath(),
                    "In thông tin Hóa đơn",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            System.out.println(e);

            JOptionPane.showMessageDialog(
                    frame,
                    "Xuất file thất bại." + '\n'
                    + "Vui lòng đóng cửa sổ đang sử dụng file",
                    "In thông tin Hóa đơn",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private XWPFRun createValueRun(XWPFParagraph paragraph, String text) {
        XWPFRun run = createRun(paragraph, text);
        run.setBold(false);
        return run;
    }

    private XWPFRun createFieldRun(XWPFParagraph paragraph, String text) {
        XWPFRun run = createRun(paragraph, text);
        run.setBold(true);
        return run;
    }

    private XWPFRun createRun(XWPFParagraph paragraph, String text) {
        XWPFRun run;
        run = paragraph.createRun();
        run.setText(text);
        run.addTab();
        return run;
    }

    private XWPFParagraph createPrintInformation(XWPFDocument document, Account _account) {
        XWPFParagraph paragraph = document.createParagraph();

        paragraph.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun run = createFieldRun(paragraph, "Ngày in: ");
        run.setFontSize(10);
        run = createValueRun(paragraph, getCurrentDateString());
        run.setFontSize(10);
        run.addCarriageReturn();
        run = createFieldRun(paragraph, "Thời gian in: ");
        run.setFontSize(10);
        run = createValueRun(paragraph, getCurrentTimeString());
        run.setFontSize(10);
        run.addCarriageReturn();
        run = createFieldRun(paragraph, "Tài khoản in: ");
        run.setFontSize(10);
        run = createValueRun(paragraph, _accountProvider.getById(_account.getId()).getUsername());
        run.setFontSize(10);
        run.addCarriageReturn();

        return paragraph;
    }

    private XWPFParagraph createBookProductInformation(XWPFDocument document, Orders order) {
        XWPFParagraph paragraph = document.createParagraph();

        paragraph.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun run = createFieldRun(paragraph, "Thông tin Hóa đơn: ");
        run.setFontSize(16);
        run.addCarriageReturn();

        ArrayList<BookController.PrintRunField> productInformations = new ArrayList<BookController.PrintRunField>() {
            {
                add(new BookController.PrintRunField("Mã Hóa đơn: ", order.getIdCode()));
                add(new BookController.PrintRunField("Tài khoản lập: ", order.getCreateBy().getUsername()));
                add(new BookController.PrintRunField("Thời gian lập: ", getDateTimeString(order.getCreateTime())));
                add(new BookController.PrintRunField(" ", ""));
                add(new BookController.PrintRunField("Tổng tiền: ", IntToVND(order.getTotalPrice())));
                add(new BookController.PrintRunField("Thuế VAT: ", IntToVND(order.getVATPrice())));
                add(new BookController.PrintRunField("Khuyến mãi: ", IntToVND(order.getDiscount())));
                add(new BookController.PrintRunField("Thanh toán: ", IntToVND(order.getPaidPrice())));
            }
        };

        for (int i = 0; i < productInformations.size(); i++) {

            run = createFieldRun(paragraph, productInformations.get(i).Field);
            run.addTab();
            run = createValueRun(paragraph, productInformations.get(i).Value);
            run.addCarriageReturn();
        }

        return paragraph;
    }

    private XWPFParagraph createBookInformation(XWPFDocument document, Orders order) {
        XWPFParagraph paragraph = document.createParagraph();

        XWPFRun run = createFieldRun(paragraph, "Thông tin Khách hàng: ");
        run.setFontSize(16);
        run.addCarriageReturn();

        ArrayList<BookController.PrintRunField> bookInformations = new ArrayList<BookController.PrintRunField>() {
            {
                add(new BookController.PrintRunField("Tên khách: ", order.getGuestName()));
                add(new BookController.PrintRunField("Địa chỉ: ", order.getGuestPhone()));
                add(new BookController.PrintRunField("Điện thoại: ", order.getGuestPhone()));
                add(new BookController.PrintRunField("Email:      ", order.getGuestEmail()));
                add(new BookController.PrintRunField(" ", ""));
                add(new BookController.PrintRunField("Ghi chú: ", order.getDetails()));
            }
        };

        for (BookController.PrintRunField bookInformation : bookInformations) {
            run = createFieldRun(paragraph, bookInformation.Field);
            run.addTab();
            run = createValueRun(paragraph, bookInformation.Value);
            run.addCarriageReturn();
        }

        return paragraph;
    }

    public void setTableAlignment(XWPFTable table, STJc.Enum justification) {
        CTTblPr tblPr = table.getCTTbl().getTblPr();
        CTJc jc = (tblPr.isSetJc() ? tblPr.getJc() : tblPr.addNewJc());
        jc.setVal(justification);
    }
}
