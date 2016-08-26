/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage.Models.Account;

import LibData.Models.Account;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Limited
 */
public class AccountTableModel extends AbstractTableModel {

    public List<Account> list;

    public AccountTableModel(List<Account> list) {
        this.list = list;
    }

    @Override
    public int getRowCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public int getColumnCount() {
        ///return 5;
        return 4; /// Hide Password Column
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Mã số";
            case 1:
                return "Tên tài khoản";
            /// Hide Password Column
//            case 2:
//                return "Mật khẩu";
//            case 3:
//                return "Thư điện tử";
//            case 4:
//                return "Thời gian tạo";
            case 2:
                return "Thư điện tử";
            case 3:
                return "Thời gian tạo";
            default:
                return null;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Account account = null;

        if (rowIndex <= list.size()) {
            account = list.get(rowIndex);

            switch (columnIndex) {
                case 0:
                    return account.getId() != null
                            ? "A-" + account.getId().substring(account.getId().length() - 5)
                            : "";
                case 1:
                    return account.getUsername() != null
                            ? account.getUsername()
                            : "";
                /// Hide Password Column
//                case 2:
//                    return account.getPasswordHash() != null
//                            ? account.getPasswordHash()
//                            : "";
//                case 3:
//                    return account.getEmail() != null
//                            ? account.getEmail()
//                            : "";
//                case 4:
//                    return account.getCreateTime() != null
//                            ? account.getCreateTime()
//                            : "";
                case 2:
                    return account.getEmail() != null
                            ? account.getEmail()
                            : "";
                case 3:
                    return account.getCreateTime() != null
                            ? account.getCreateTime()
                            : "";
                default:
                    return null;
            }

        }

        return null;
    }

    public void fireTableDataChanged(List<Account> list) {
        this.list = list;
        super.fireTableDataChanged(); //To change body of generated methods, choose Tools | Templates.
    }
}
