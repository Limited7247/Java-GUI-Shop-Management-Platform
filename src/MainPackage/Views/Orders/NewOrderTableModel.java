/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage.Views.Orders;

import LibData.Models.OrderLine;
import LibData.Models.Product;
import LimitedSolution.Utilities.JTableHelper;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import LimitedSolution.Utilities.CurrencyHelper.*;
import static LimitedSolution.Utilities.CurrencyHelper.*;

/**
 *
 * @author Limited
 */
public class NewOrderTableModel extends AbstractTableModel {

    List<OrderLine> list;

    public NewOrderTableModel() {
        this.list = new ArrayList<OrderLine>();
    }

    public NewOrderTableModel(List<OrderLine> list) {
        this.list = list;
    }

    public static ArrayList<JTableHelper.Column> columnsList = new ArrayList() {
        {
            add(new JTableHelper.Column(0, "STT", 30));
            add(new JTableHelper.Column(1, "Mã Sản phẩm", 30));
            add(new JTableHelper.Column(2, "Tên Sản phẩm", 30));

            add(new JTableHelper.Column(3, "Đơn vị", 30));
            add(new JTableHelper.Column(4, "Số lượng", 30));
            add(new JTableHelper.Column(5, "Giá tiền", 30));
            add(new JTableHelper.Column(6, "Thành tiền", 30));
        }
    };

    @Override
    public int getRowCount() {
        return list != null ? list.size() : 0;
    }

    public boolean contains(String productIdCode) {
        for (OrderLine orderLine : list) {
            if (orderLine.getProductId().getIdCode().toUpperCase().equals(productIdCode.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    public OrderLine getOrderLineByProductId(String productId)
    {
        try {
            for (OrderLine orderLine : list) {
                if (orderLine.getProductId().getId().toUpperCase().equals(productId.toUpperCase()))
                {
                    return orderLine;
                }
            }
            
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    
    public Integer getTotalPrice()
    {
        try {
            Integer total = 0;
            for (OrderLine orderLine : list) {
                total += (int) orderLine.getTotalPrice();
            }
            return total;
        } catch (Exception e) {
            return 0;
        }
    }
    
    public void fireTableDataChanged(OrderLine newLine) {
        if (!this.contains(newLine.getProductId().getIdCode())) {
            this.list.add(newLine);
        }
        else
        {
            for (OrderLine orderLine : list) {
                if (orderLine.getProductId().getIdCode().toUpperCase().equals(newLine.getProductId().getIdCode()))
                {
                    orderLine.setQuantity(orderLine.getQuantity() + newLine.getQuantity());
                    orderLine.setTotalPrice(orderLine.getQuantity() * orderLine.getUnitPrice());
                    orderLine.setVATPrice(orderLine.getTotalPrice()/10);
                    orderLine.setPaidPrice(orderLine.getTotalPrice());
                }
            }
        }
        super.fireTableDataChanged(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getColumnName(int column) {
        return columnsList != null ? columnsList.get(column).Name : "";
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            OrderLine line = list.get(rowIndex);

            switch (columnIndex) {
                case 0:
                    return rowIndex + 1;
                case 1:
                    return line.getProductId().getIdCode();
                case 2:
                    return line.getProductId().getName();
                case 3:
                    return "Quyển";
                case 4:
                    return line.getQuantity();
                case 5:
                    return IntToVND(line.getUnitPrice());
                case 6:
                    return IntToVND(line.getTotalPrice());
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

}
