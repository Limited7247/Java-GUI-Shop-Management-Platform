/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage.Controllers;

import LimitedSolution.Classes.StringWrapper;
import LibData.Models.Account;
import LibData.Models.Factories.AccountFactory;
import LibData.Providers.AccountProvider;
import LimitedSolution.Utilities.SecurityHelper;
import MainPackage.Models.Account.AddAccountModel;
import MainPackage.Models.Account.LoginViewModel;
import MainPackage.Views.Account.LoginDialog;
import MainPackage.Views.AddAccount.AddAccountDialog;
import static java.lang.System.exit;
import java.util.List;
import java.util.Optional;
import javax.swing.JOptionPane;
import sun.security.provider.MD5;

/**
 *
 * @author Limited
 */
public class AccountController {

    private AccountProvider _accountProvider = null;

    public AccountController() {
        _accountProvider = new AccountProvider();
    }

    public String MD5(String plaintext) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(plaintext.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public boolean CheckAccount(Account account) {
        if (account == null) {
            return false;
        }

        if (account.getUsername() == null || account.getUsername().isEmpty()) {
            return false;
        }
        if (account.getPasswordHash() == null 
                || account.getPasswordHash().isEmpty()
                || account.getPasswordHash().equals(SecurityHelper.MD5EmptyString)) {
            return false;
        }

        return (_accountProvider.CheckByUsernameAndPasswordHash(
                account.getUsername(),
                account.getPasswordHash()));
    }

    public boolean CheckAccount(Account account, StringWrapper warning) {
        warning.setString("");
        boolean key = true;

        if (account == null) {
            return false;
        }

        if (account.getUsername() == null || account.getUsername().isEmpty()) {
            warning.setString("Vui lòng nhập tên tài khoản" + '\n');
            key = false;
        }
        if (account.getPasswordHash() == null 
                || account.getPasswordHash().isEmpty()
                || account.getPasswordHash().equals(SecurityHelper.MD5EmptyString)) {
            warning.appendString("Vui lòng nhập mật khẩu" + '\n');
            key = false;
        }

        if (!key) {
            return key;
        }

        if (!_accountProvider.CheckByUsernameAndPasswordHash(
                account.getUsername(),
                account.getPasswordHash())) {
            key = false;
            warning.setString("Tên tài khoản hoặc mật khẩu không đúng");
        }

        return key;
    }

    public boolean HasUsername(Account account) {
        if (account == null) {
            return false;
        }

        if (account.getUsername() == null) {
            return false;
        }

        return _accountProvider.CheckByUsername(account.getUsername());
    }
    
    public boolean HasPassword(Account account) {
        if (account == null) {
            return false;
        }

        if (account.getPasswordHash().equals(MD5(""))) {
            return false;
        }

        return true;
    }
    
    public boolean HasEmail(Account account) {
        if (account == null) {
            return false;
        }

        if (account.getUsername() == null) {
            return false;
        }

        return _accountProvider.CheckByEmail(account.getEmail());
    }

    public void Login(Optional<Account> account) {
        if (account != null) {
            LoginDialog frmLogin = new LoginDialog(null, true, account);
            frmLogin.show();
        }

//        JOptionPane.showMessageDialog(
//                null,
//                "Lỗi: Chưa khởi tạo account",
//                "Lỗi đăng nhập",
//                JOptionPane.ERROR_MESSAGE);
    }

    public boolean Login(LoginViewModel model, Optional<Account> account) {
//        System.out.println(model.Username + " " + model.PasswordHash);

        StringWrapper warning = new StringWrapper();

        if (!CheckAccount(model.getAccount(), warning)) {
            JOptionPane.showMessageDialog(
                    null,
                    warning.getString(),
                    "Đăng nhập thất bại",
                    JOptionPane.WARNING_MESSAGE);
            return false;

        }
        
        AccountFactory.transfer(model, account.get());

        JOptionPane.showMessageDialog(
                null,
                "Đăng nhập thành công",
                "Đăng nhập",
                JOptionPane.INFORMATION_MESSAGE);
        return true;
    }

    public void showAllAccount() {
        List<Account> list = new AccountProvider().getAll();

        for (Account account : list) {
            System.out.println(
                    account.getId()
                    + " " + account.getUsername()
                    + " " + account.getPasswordHash()
                    + " " + account.getEmail()
                    + " " + account.getCreateTime()
            );
        }
    }

    public boolean AddAccount(AddAccountModel model) {

        Account newaccount = new Account();
        newaccount.setUsername(model.Username);
        newaccount.setPasswordHash(model.PasswordHash);
        newaccount.setEmail(model.Email);

        if (HasUsername(newaccount)) {
            if (HasEmail(newaccount)) {
                if (HasPassword(newaccount)) {
                    _accountProvider.Insert(newaccount);
                    JOptionPane.showMessageDialog(
                            null,
                            "Thêm tài khoản thành công",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                    return true;
                }
                else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Vui lòng nhập mật khẩu",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
            else {
                JOptionPane.showMessageDialog(
                        null,
                        "Đã có email " + newaccount.getEmail(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }

        }
        else {

            JOptionPane.showMessageDialog(
                    null,
                    "Đã có tài khoản " + newaccount.getUsername(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public void DelAccount(Account delaccount) {
       
        if (this._accountProvider.Delete(delaccount))
        {
            JOptionPane.showMessageDialog(
                    null, 
                    "Đã xóa tài khoản ", 
                    "Thông báo", 
                    JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(
                    null, 
                    "Không có tài khoản ", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}
