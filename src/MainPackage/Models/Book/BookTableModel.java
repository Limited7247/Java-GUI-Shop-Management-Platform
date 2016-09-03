/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage.Models.Book;

import LibData.Business.Configs.BookConfigs;
import LibData.Models.Book;
import static LimitedSolution.Utilities.CurrencyHelper.IntToVND;
import LimitedSolution.Utilities.JTableHelper;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Limited
 */
public class BookTableModel extends AbstractTableModel {

    public List<Book> list;

    public static ArrayList<JTableHelper.Column> columnsList = new ArrayList() {{
        add(new JTableHelper.Column(0, "STT", 30));
        add(new JTableHelper.Column(0, "Mã sách", 30));
        add(new JTableHelper.Column(0, "Mã ISBN", 30));
        
        add(new JTableHelper.Column(0, "Tên sách", 30));
        add(new JTableHelper.Column(0, "Tác giả", 30));
        add(new JTableHelper.Column(0, "Năm phát hành", 30));
        add(new JTableHelper.Column(0, "Giá bán", 30));
        add(new JTableHelper.Column(0, "Tình trạng", 30));
    }};

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
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                return columnsList.get(column).Name;
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

    public static class BookTableColumnModel extends DefaultTableColumnModel {

        public BookTableColumnModel() {
        }

        @Override
        public TableColumn getColumn(int columnIndex) {
            TableColumn tableColumn = new TableColumn(columnIndex);
            tableColumn.setHeaderValue(columnsList.get(columnIndex).Name);
            tableColumn.setMinWidth(columnsList.get(columnIndex).MinWidth);
            tableColumn.setMaxWidth(200);
            return tableColumn;
        }

        @Override
        public int getColumnMargin() {
            return 5; //To change body of generated methods, choose Tools | Templates.
        }
    }
}
