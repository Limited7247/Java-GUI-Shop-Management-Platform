/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LimitedSolution.Utilities;

import java.util.StringTokenizer;
import javax.swing.JOptionPane;

/**
 *
 * @author Limited
 */
public class StringHelper {

    public static String getFromDialog() {
        return JOptionPane.showInputDialog(
                null,
                "Nhập vào xâu ký tự",
                "Nhập xâu",
                JOptionPane.QUESTION_MESSAGE
        );
    }

    public static void showDialog(String string) {
        JOptionPane.showMessageDialog(
                null,
                "Xâu ký tự: \"" + string + "\"",
                "Hiển thị xâu ký tự",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    public static String toStandardizeString(String string) {
        String strTemp = "";
        String strWorking = "";
        StringTokenizer tokensTemp = new StringTokenizer(string);

        while (tokensTemp.hasMoreTokens()) {
            strWorking = tokensTemp.nextToken();
            strWorking
                    = strWorking.substring(0, 1).toUpperCase()
                    + strWorking.substring(1, strWorking.length()).toLowerCase();
            strTemp += strWorking + " ";
        }

        return strTemp.substring(0, strTemp.length() - 1);
    }

    public static String toReverseString(String string) {
        return new StringBuffer(string).reverse().toString();
    }
}
