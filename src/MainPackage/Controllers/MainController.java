/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage.Controllers;

import LibData.Models.Account;
import LibData.Models.Product;
import LibData.Providers.AccountProvider;
import LimitedSolution.Utilities.DateTimeHelper;
import static LimitedSolution.Utilities.JTableHelper.TableColumnAdjuster;
import MainPackage.Models.Account.AccountTableModel;
import MainPackage.Views.MainFrame;
import java.awt.Component;
import static java.lang.System.exit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author Limited
 */
public class MainController {

    private AccountProvider _accountProvider = new AccountProvider();

    public void Start() {
//        System.out.println(DateTimeHelper.getCurrentDateString());
//        System.out.println(DateTimeHelper.getCurrentTimeString());
//        System.out.println(DateTimeHelper.getCurrentDateTimeString());
//        
//        System.out.println(DateTimeHelper.getDateString(new Date()));
//        System.out.println(DateTimeHelper.getTimeString(new Date()));
//        System.out.println(DateTimeHelper.getDateTimeString(new Date()));
//        
//        System.out.println(DateTimeHelper.getDateTimeString(new Date(), DateTimeHelper.FORMAT_DATE_DEFAULT));
//        System.out.println(DateTimeHelper.getDateTimeString(new Date(), DateTimeHelper.FORMAT_TIME_DEFAULT));
//        System.out.println(DateTimeHelper.getDateTimeString(new Date(), DateTimeHelper.FORMAT_DATETIME_DEFAULT));

    }

    public void Exit() {
        if (JOptionPane.showConfirmDialog(
                null,
                "Bạn muốn thoát chương trình?",
                "Thoát chương trình",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            exit(0);
        }
    }

    public void ShowAccountTable(MainFrame frmMain) {
        frmMain.tableAccount.setModel(new AccountTableModel(_accountProvider.getAll()));

        TableColumnAdjuster(frmMain.tableAccount);
    }

    public void UpdateAccountTable(MainFrame frmMain) {
        ((AccountTableModel) frmMain.tableAccount.getModel()).fireTableDataChanged(_accountProvider.getAll());

        TableColumnAdjuster(frmMain.tableAccount);
    }


}
