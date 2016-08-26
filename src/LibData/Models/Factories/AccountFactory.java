/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibData.Models.Factories;

import LibData.Models.Account;
import MainPackage.Models.Account.LoginViewModel;
import java.util.Date;

/**
 *
 * @author Limited
 */
public class AccountFactory {

    public static Account createAccount() {
        Account account = new Account();

        account.setId("");
        account.setUsername("");
        account.setPasswordHash("");
        account.setEmail("");
        account.setCreateTime(new Date());

        return account;
    }

    public static Account createAccount(String Username, String PasswordHash) {
        Account account = createAccount();

        account.setUsername(Username);
        account.setPasswordHash(PasswordHash);

        return account;
    }

    public static Account createAccount(String Username, String PasswordHash, String Email) {
        Account account = createAccount(Username, PasswordHash);
        
        account.setEmail(Email);
        
        return account;
    }

    public static void transfer(Account accountSource, Account accountDestination) {
        try {
            if (accountSource != null && accountDestination != null) {
                if (accountSource.getId() != null) {
                    accountDestination.setId(accountSource.getId());
                }

                if (accountSource.getUsername() != null) {
                    accountDestination.setUsername(accountSource.getUsername());
                }

                if (accountSource.getPasswordHash() != null) {
                    accountDestination.setPasswordHash(accountSource.getPasswordHash());
                }

                if (accountSource.getEmail() != null) {
                    accountDestination.setEmail(accountSource.getEmail());
                }

                if (accountSource.getCreateTime() != null) {
                    accountDestination.setCreateTime(accountSource.getCreateTime());
                }
            }

        } catch (Exception e) {
        }
    }

    public static void transfer(LoginViewModel model, Account accountDestination) {
        Account accountSource = createAccount(model.Username, model.PasswordHash);
        transfer(accountSource, accountDestination);
    }

}
