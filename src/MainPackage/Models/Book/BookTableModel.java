/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage.Models.Book;

import LibData.Business.Configs.BookConfigs;
import LibData.Models.Book;
import static LimitedSolution.Utilities.CurrencyHelper.IntToVND;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Limited
 */
public class BookTableModel extends AbstractTableModel {

    public List<Book> list;

    public BookTableModel(List<Book> list) {
        this.list = list;
    }

    @Override
    public int getRowCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Stt.";  /// Số thứ tự
            case 1:
                return "Mã sách";
            case 2:
                return "Mã ISBN";
            case 3:
                return "Tên sách";
            case 4:
                return "Tác giả";
            case 5:
                return "Năm phát hành";
            case 6:
                return "Giá bán";
            case 7:
                return "Tình trạng";
            default:
                return "";
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Book book = list.get(rowIndex);

            switch (columnIndex) {
                case 0:
                    return rowIndex + 1;  /// Số thứ tự
                case 1:
                    return book.getIdCode();
                case 2:
                    return book.getIsbn();
                case 3:
                    return book.getName();
                case 4:
                    return book.getAuthor();
                case 5:
                    return book.getPublishYear();
                case 6:
                    return book.getPrice() != null ? IntToVND(book.getPrice()) : "";
                case 7:
                    return BookConfigs.getBookStatus(book.getStatus());
                default:
                    return "";
            }
        } catch (Exception e) {
            return null;
        }
    }

}
