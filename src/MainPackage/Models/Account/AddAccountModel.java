/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage.Models.Account;

import LibData.Models.Account;
import LibData.Models.Factories.AccountFactory;

/**
 *
 * @author yang
 */
public class AddAccountModel {
    
    public String Username;
    public String PasswordHash;
    public String Email;

    public Account getAccount()
    {
        return AccountFactory.createAccount(Username, PasswordHash, Email);
    }
    
    public AddAccountModel() {
        this.Username = "";
        this.PasswordHash = "";
        this.Email = "";
    }
    
    public AddAccountModel(String Username, String PasswordHash, String Email) {
        this.Username = Username;
        this.PasswordHash = PasswordHash;
        this.Email = Email;
    }
}
