/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage.Controllers;

import LibData.Models.Account;
import java.util.Optional;

/**
 *
 * @author Limited
 */
public class GlobalValues {
    public Optional<Account> _account;

    public Optional<Account> getAccount() {
        return _account;
    }

    public void setAccount(Optional<Account> _account) {
        this._account = _account;
    }
    
    private GlobalValues() {
        this._account = null;
    }
    
    public static GlobalValues getInstance() {
        return GlobalValuesHolder.INSTANCE;
    }
    
    private static class GlobalValuesHolder {

        private static final GlobalValues INSTANCE = new GlobalValues();
    }
}
