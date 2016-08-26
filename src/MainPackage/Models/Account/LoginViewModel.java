/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage.Models.Account;

import LibData.Models.Account;
import LibData.Models.Factories.AccountFactory;
import LimitedSolution.Utilities.SecurityHelper;
import MainPackage.Models.IModelValidate;

/**
 *
 * @author Limited
 */
public class LoginViewModel implements IModelValidate {

    public String Username;
    public String PasswordHash;

    public Account getAccount() {
        return AccountFactory.createAccount(Username, PasswordHash);
    }

    public LoginViewModel() {
        this.Username = "";
        this.PasswordHash = "";
    }

    public LoginViewModel(String Username, String PasswordHash) {
        this.Username = Username;
        this.PasswordHash = PasswordHash;
    }

    @Override
    public boolean IsValidate() {
        if (this.Username == null || this.Username.isEmpty()) {
            return false;
        }
        
        return !(this.PasswordHash == null
                || this.PasswordHash.isEmpty()
                || this.PasswordHash.equals(SecurityHelper.MD5EmptyString));
    }

    @Override
    public String MessageValidate() {
        if (this.IsValidate()) return "OK";
        
        String message = "";
        
        if (this.Username == null || this.Username.isEmpty()) {
            message = "Vui lòng nhập tên tài khoản" + '\n';
        }
        
        if (this.PasswordHash == null
                || this.PasswordHash.isEmpty()
                || this.PasswordHash.equals(SecurityHelper.MD5EmptyString)) {
            message += "Vui lòng nhập mật khẩu" + '\n';
        }
        
        return message;
    }

    @Override
    public String ToLogString() {
        return "";
    }
}
