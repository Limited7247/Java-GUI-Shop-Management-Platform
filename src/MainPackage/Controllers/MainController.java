/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage.Controllers;

import LibData.Providers.AccountProvider;
import MainPackage.Models.Account.AccountTableModel;
import MainPackage.Views.MainFrame;
import java.awt.Component;
import static java.lang.System.exit;
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

    public void TableColumnAdjuster(JTable table) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int column = 0; column < table.getColumnCount(); column++) {
            TableColumn tableColumn = table.getColumnModel().getColumn(column);
            int preferredWidth = tableColumn.getMinWidth();
            int maxWidth = tableColumn.getMaxWidth();

            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
                Component c = table.prepareRenderer(cellRenderer, row, column);
                int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
                preferredWidth = Math.max(preferredWidth, width);

                //  We've exceeded the maximum width, no need to check other rows
                if (preferredWidth >= maxWidth) {
                    preferredWidth = maxWidth;
                    break;
                }
            }

            tableColumn.setPreferredWidth(preferredWidth);
        }
    }
}
