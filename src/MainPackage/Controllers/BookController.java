/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage.Controllers;

import LibData.Business.Configs.BookConfigs;
import LibData.Models.Account;
import LibData.Models.Book;
import LibData.Providers.AccountProvider;
import LibData.Providers.BookProvider;
import static LimitedSolution.Utilities.JTableHelper.TableColumnAdjuster;
import MainPackage.Models.Book.AddBookModel;
import MainPackage.Models.Book.BookTableModel;
import MainPackage.Models.Book.UpdateBookModel;
import MainPackage.Views.Book.AddBookDialog;
import MainPackage.Views.Book.BooksFrame;
import MainPackage.Views.Book.UpdateBookDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Limited
 */
public class BookController {

    private BookProvider _bookProvider = new BookProvider();
    private AccountProvider _accountProvider = new AccountProvider();

    public void ShowBooksTable(BooksFrame booksFrame) {
        booksFrame.booksTable.setModel(new BookTableModel(_bookProvider.getAll()));
        TableColumnAdjuster(booksFrame.booksTable);
    }

    public void AddBook(BooksFrame booksFrame, Account account) {
        AddBookDialog addBookDialog = new AddBookDialog(booksFrame, true, account);
        addBookDialog.show();
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

}
