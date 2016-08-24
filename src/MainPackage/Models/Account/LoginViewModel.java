/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage.Models.Account;

/**
 *
 * @author Limited
 */
public class LoginViewModel {

    public String Username;
    public String PasswordHash;
    
//    public String MD5(String plaintext) {
//        try {
//            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
//            byte[] array = md.digest(plaintext.getBytes());
//            StringBuffer sb = new StringBuffer();
//            for (int i = 0; i < array.length; ++i) {
//                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
//            }
//            return sb.toString();
//        } catch (java.security.NoSuchAlgorithmException e) {
//        }
//        return null;
//    }

    public LoginViewModel() {
        this.Username = "";
        this.PasswordHash = "";
    }

    public LoginViewModel(String Username, String PasswordHash) {
        this.Username = Username;
        this.PasswordHash = PasswordHash;
    }
}
