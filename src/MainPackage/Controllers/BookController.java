/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage.Controllers;

import LibData.Business.Configs.BookConfigs;
import LibData.Models.Account;
import LibData.Models.Book;
import LibData.Models.Product;
import LibData.Providers.AccountProvider;
import LibData.Providers.BookProvider;
import LibData.Providers.InventoryProvider;
import LibData.Providers.ProductProvider;
import static LimitedSolution.Utilities.DateTimeHelper.getCurrentDateString;
import static LimitedSolution.Utilities.DateTimeHelper.getCurrentDateTimeString;
import static LimitedSolution.Utilities.DateTimeHelper.getCurrentTimeString;
import static LimitedSolution.Utilities.DateTimeHelper.getDateTimeString;
import static LimitedSolution.Utilities.JTableHelper.TableColumnAdjuster;
import MainPackage.Models.Book.AddBookModel;
import MainPackage.Models.Book.BookTableModel;
import MainPackage.Models.Book.BookTableModel.BookTableColumnModel;
import MainPackage.Models.Book.BookViewModel;
import MainPackage.Models.Book.UpdateBookModel;
import MainPackage.Views.Book.AddBookDialog;
import MainPackage.Views.Book.BooksFrame;
import MainPackage.Views.Book.UpdateBookDialog;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
//import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author Limited
 */
public class BookController {

    private BookProvider _bookProvider = new BookProvider();
    private AccountProvider _accountProvider = new AccountProvider();
    private InventoryProvider _inventoryProvider = new InventoryProvider();

    public void ShowBooksTable(BooksFrame booksFrame) {
        BookTableModel model = new BookTableModel(_bookProvider.getAll()); 
        _inventoryProvider.addInventoryInformation(model);
        
        booksFrame.booksTable.setModel(model);
        TableColumnAdjuster(booksFrame.booksTable, 30);
    }

    public void AddBook(BooksFrame booksFrame, Account account) {
        AddBookDialog addBookDialog = new AddBookDialog(booksFrame, true, account);
        addBookDialog.show();
        ShowBooksTable(booksFrame);
    }

    public boolean AddBook(AddBookModel model) {
        if (!model.IsValidate()) {
            JOptionPane.showMessageDialog(
                    null,
                    model.MessageValidate(),
                    "Thông tin không hợp lệ",
                    JOptionPane.WARNING_MESSAGE);

            return false;
        }

        Book book = model.getBook();
        book.setStatus(BookConfigs.BOOK_STATUS_ACTIVE);
        book.setCreatedBy(_accountProvider.getById(model.CreatedBy));

        if (_bookProvider.Insert(book)) {
            JOptionPane.showMessageDialog(
                    null,
                    "Thêm sách " + model.Name + " thành công",
                    "Thêm sách",
                    JOptionPane.INFORMATION_MESSAGE);
            return true;
        }

        JOptionPane.showMessageDialog(
                null,
                "Thêm sách " + model.Name + " thất bại",
                "Thêm sách",
                JOptionPane.ERROR_MESSAGE);
        return false;
    }

    public void ViewBook(BooksFrame booksFrame, Book book, Account account) {
        UpdateBookDialog dialog
                = new UpdateBookDialog(
                        booksFrame,
                        true,
                        new UpdateBookModel(book),
                        account
                );
        dialog.show();
        ShowBooksTable(booksFrame);
    }

    public boolean UpdateBook(UpdateBookModel model) {
        if (!model.IsValidate()) {
            JOptionPane.showMessageDialog(
                    null,
                    model.MessageValidate(),
                    "Thông tin không hợp lệ",
                    JOptionPane.WARNING_MESSAGE);

            return false;
        }

        Book book = model.getBook();

        if (_bookProvider.Update(book)) {
            JOptionPane.showMessageDialog(
                    null,
                    "Cập nhật thông tin sách " + model.Name + " thành công",
                    "Cập nhật thông tin sách",
                    JOptionPane.INFORMATION_MESSAGE);
            return true;
        }

        JOptionPane.showMessageDialog(
                null,
                "Cập nhật thông tin sách " + model.Name + " thất bại",
                "Cập nhật thông tin sách",
                JOptionPane.ERROR_MESSAGE);
        return false;
    }

    public boolean DeleteBook(BooksFrame booksFrame, Book book, Account _account) {
        if (JOptionPane.showConfirmDialog(
                booksFrame,
                "Bạn chắc chắn muốn xóa sách" + '\n'
                + book.getName() + "?",
                "Xóa sách",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

            if (_bookProvider.Delete(book.getId())) {

                JOptionPane.showMessageDialog(
                        booksFrame,
                        "Xóa sách " + book.getName() + " thành công",
                        "Xóa sách",
                        JOptionPane.INFORMATION_MESSAGE);
                return true;

            }

            JOptionPane.showMessageDialog(
                    booksFrame,
                    "Xóa sách " + book.getName() + " thất bại",
                    "Xóa sách",
                    JOptionPane.ERROR_MESSAGE);
            return false;

        }

        return true;
    }

    public void Find(BooksFrame booksFrame, String text) {
        List<Book> list = _bookProvider.Find(text);
        BookTableModel model = new BookTableModel(list);
        _inventoryProvider.addInventoryInformation(model);
        
        booksFrame.booksTable.setModel(model);
        TableColumnAdjuster(booksFrame.booksTable, 30);
        JOptionPane.showMessageDialog(
                booksFrame,
                "Tìm thấy " + list.size() + " kết quả",
                "Tìm kiếm Sách",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public BookViewModel GetBookInformations(Book selectedBook) {
        BookViewModel model = new BookViewModel(selectedBook);

        Integer inStock = _inventoryProvider.getInStockByProductId(selectedBook.getProductId());
        Integer in = _inventoryProvider.getInQuantitiesByProductId(selectedBook.getProductId());
        Integer out = _inventoryProvider.getOutQuantitiesByProductId(selectedBook.getProductId());

        model.InventoryInStock = inStock != null ? inStock + "" : "0";
        model.InventoryIn = in != null ? in + "" : "0";
        model.InventoryOut = out != null ? out + "" : "0";

        return model;
    }

    public static class PrintRunField {

        public String Field;
        public String Value;

        public PrintRunField(String Field, String Value) {
            this.Field = Field;
            this.Value = Value;
        }

    }

    public void PrintBook(BooksFrame booksFrame, Book book, Account _account) {
        try {
            XWPFDocument document = new XWPFDocument();
            File file = new File("Sách " + book.getIdCode() + ".doc");
            if (file.exists()) {
                file.createNewFile();
            }

            FileOutputStream out
                    = new FileOutputStream(file);

            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.CENTER);

            XWPFRun run;
            BookViewModel bookView = new BookViewModel(book);

            /////////////////
            run = createFieldRun(paragraph, "THÔNG TIN SÁCH");
            run.setFontSize(24);

            paragraph = createPrintInformation(document, _account);
            paragraph = createBookProductInformation(document, bookView);
            paragraph = createBookInformation(document, bookView);

            document.write(out);
            out.close();

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }

            JOptionPane.showMessageDialog(
                    booksFrame,
                    "Xuất file " + file.getName() + " thành công" + '\n'
                    + "Tại ví trí: " + file.getAbsolutePath(),
                    "In thông tin Sách",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            System.out.println(e);

            JOptionPane.showMessageDialog(
                    booksFrame,
                    "Xuất file thất bại." + '\n'
                    + "Vui lòng đóng cửa sổ đang sử dụng file",
                    "In thông tin Sách",
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

    private XWPFParagraph createBookProductInformation(XWPFDocument document, BookViewModel bookView) {
        XWPFParagraph paragraph = document.createParagraph();

        paragraph.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun run = createFieldRun(paragraph, "Thông tin Sản phẩm: ");
        run.setFontSize(16);
        run.addCarriageReturn();

        ArrayList<PrintRunField> productInformations = new ArrayList<PrintRunField>() {
            {
                add(new PrintRunField("Mã sách: ", bookView.IdCode));
                add(new PrintRunField("Mã ISBN: ", bookView.ISBN));
                add(new PrintRunField("Tài khoản tạo: ", bookView.CreatedBy));
                add(new PrintRunField("Thời gian tạo: ", bookView.CreateTime));
                add(new PrintRunField("Giá sách: ", bookView.Price));
                add(new PrintRunField("Tình trạng: ", bookView.Status));
            }
        };

        for (int i = 0; i < productInformations.size(); i++) {

            run = createFieldRun(paragraph, productInformations.get(i).Field);
            run.addTab();
            run = createValueRun(paragraph, productInformations.get(i).Value);
            if (i % 2 == 0) {
                run.addTab();
            } else {
                run.addCarriageReturn();
            }
        }

        return paragraph;
    }

    private XWPFParagraph createBookInformation(XWPFDocument document, BookViewModel bookView) {
        XWPFParagraph paragraph = document.createParagraph();

        XWPFRun run = createFieldRun(paragraph, "Thông tin Sách: ");
        run.setFontSize(16);
        run.addCarriageReturn();

        ArrayList<PrintRunField> bookInformations = new ArrayList<PrintRunField>() {
            {
                add(new PrintRunField("Tên sách: ", bookView.Name));
                add(new PrintRunField("Tác giả: ", bookView.Author));
                add(new PrintRunField("Nhà xuất bản: ", bookView.Publisher));
                add(new PrintRunField("Thể loại: ", bookView.Type));
                add(new PrintRunField("Phát hành: ", bookView.PublishMonth + "/" + bookView.PublishYear));
                add(new PrintRunField("Mô tả:      ", bookView.Details));
            }
        };

        for (PrintRunField bookInformation : bookInformations) {
            run = createFieldRun(paragraph, bookInformation.Field);
            run.addTab();
            run = createValueRun(paragraph, bookInformation.Value);
            run.addCarriageReturn();
        }

        return paragraph;
    }
}
