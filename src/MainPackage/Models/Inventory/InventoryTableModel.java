package MainPackage.Models.Inventory;

import LibData.Models.Inventory;
import LibData.Providers.ProductProvider;
import LimitedSolution.Utilities.JTableHelper;
import LimitedSolution.Utilities.JTableHelper.ITableColumnModel;
import static MainPackage.Models.Book.BookTableModel.columnsList;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Limited
 */
public class InventoryTableModel extends AbstractTableModel {

    public List<Inventory> list;
    private ProductProvider _productProvider = new ProductProvider();

    public InventoryTableModel(List<Inventory> list) {
        this.list = list;
    }

    public static ArrayList<JTableHelper.Column> columnsList = new ArrayList() {
        {
            add(new JTableHelper.Column(0, "STT", 30));
            add(new JTableHelper.Column(0, "Mã Kiểm kê", 30));
            add(new JTableHelper.Column(0, "Mã Sản phẩm", 30));
            add(new JTableHelper.Column(0, "Tên Sản phẩm", 30));
            add(new JTableHelper.Column(0, "Đơn vị", 30));
            add(new JTableHelper.Column(0, "Số lượng", 30));
            add(new JTableHelper.Column(0, "Tình trạng", 30));
        }
    };

    @Override
    public String getColumnName(int column) {
        return columnsList != null
                ? columnsList.get(column).Name
                : "";
    }

    @Override
    public int getRowCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return columnsList != null ? columnsList.size() : 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Inventory inventory = list.get(rowIndex);

            switch (columnIndex) {
                case 0:
                    return columnIndex + 1;
                case 1:
                    return inventory.getIdCode();
                case 2:
                    return _productProvider.getById(inventory.getId()).getIdCode();
                case 3:
                    return _productProvider.getById(inventory.getId()).getName();
                case 4:
                    return inventory.getUnit();
                case 5:
                    return inventory.getQuantity();
                case 6:
                    return inventory.getStatus();
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static class InventoryTableColumnModel extends DefaultTableColumnModel implements ITableColumnModel {
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
            return 5;
        }
    }

}
