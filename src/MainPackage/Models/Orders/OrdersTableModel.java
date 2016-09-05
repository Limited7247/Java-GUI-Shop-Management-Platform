/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage.Models.Orders;

import LibData.Models.OrderLine;
import LibData.Models.Orders;
import LimitedSolution.Utilities.JTableHelper;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import static LimitedSolution.Utilities.CurrencyHelper.*;
import static LimitedSolution.Utilities.DateTimeHelper.getDateTimeString;

/**
 *
 * @author Limited
 */
public class OrdersTableModel extends AbstractTableModel {

    public List<Orders> list;

    public OrdersTableModel() {
        this.list = new ArrayList<Orders>();
    }

    public OrdersTableModel(List<Orders> list) {
        this.list = list;
    }

    public static ArrayList<JTableHelper.Column> columnsList = new ArrayList() {
        {
            add(new JTableHelper.Column(0, "STT", 30));
            add(new JTableHelper.Column(1, "Mã Hóa đơn", 30));
            add(new JTableHelper.Column(2, "Người bán", 30));

            add(new JTableHelper.Column(3, "Khách hàng", 30));
            add(new JTableHelper.Column(4, "Thuế VAT", 30));
            add(new JTableHelper.Column(5, "Thanh toán", 30));
            add(new JTableHelper.Column(6, "Thời gian lập", 30));
        }
    };

    @Override
    public int getRowCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public String getColumnName(int column) {
        return columnsList != null ? columnsList.get(column).Name : "";
    }
    
    @Override
    public int getColumnCount() {
        return columnsList != null ? columnsList.size() : 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Orders order = list.get(rowIndex);
            
            switch(columnIndex)
            {
                case 0:
                    return rowIndex + 1;
                case 1:
                    return order.getIdCode();
                case 2:
                    return order.getCreateBy().getUsername();
                case 3:
                    return order.getGuestName();
                case 4:
                    return IntToVND(order.getVATPrice());
                case 5:
                    return IntToVND(order.getPaidPrice());
                case 6:
                    return getDateTimeString(order.getCreateTime());
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
