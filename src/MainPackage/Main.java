/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage;

import LibData.Models.Account;
import MainPackage.Controllers.AccountController;
import MainPackage.Controllers.MainController;
import MainPackage.Views.Orders.NewOrderFrame;
import java.util.Optional;

/**
 *
 * @author Limited
 */
public class Main {

    private static Optional<Account> _account = null;
    private static MainController _mainController = new MainController();
    private static AccountController _accountController = new AccountController();
    
    private static NewOrderFrame _mainFrame = new NewOrderFrame();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        _mainController.Start();

        if (_account == null) {
            _account = Optional.of(new Account());
        }

        
        
        while (!_accountController.CheckAccount(_account.get())) {
            _accountController.Login(_mainFrame, _account);
        }

        _mainFrame = new NewOrderFrame(_account.get());
        _mainFrame.setVisible(true);

//        _mainController.ShowAccountTable(_mainFrame);
    }

}
