/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibData.Providers;

import LibData.JPAControllers.AccountJpaController;
import LibData.Models.Account;
import static LibData.Providers.ProviderHelper.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.eclipse.persistence.internal.jpa.EntityManagerFactoryProvider;
import org.jinq.jpa.JPAJinqStream;
import org.jinq.jpa.JinqJPAStreamProvider;

/**
 *
 * @author Limited
 */
public class AccountProvider {

    private AccountJpaController jpaAccount = new AccountJpaController(getEntityManagerFactory());

    private AccountJpaController getJPAAccount() {
        RefreshDatabase(jpaAccount.getEntityManager());
        return jpaAccount;
    }

    /// Streams will close after used. Need to reuse it.
    private JPAJinqStream<Account> getJinqAccounts() {
        return getJinqStream().streamAll(getEntityManager(), Account.class);
    }

    public List<Account> getAll() {
        try {
            return getJinqAccounts().sortedDescendingBy(m -> m.getCreateTime()).toList();

        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }

    public Account getById(String Id) {
        try {
            return getJPAAccount().findAccount(Id);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public boolean CheckByUsernameAndPasswordHash(String Username, String PasswordHash) {
        try {
            long counter
                    = getJinqAccounts()
                            .where(m -> m.getUsername().equals(Username) && m.getPasswordHash().equals(PasswordHash))
                            .count();

            return counter == 1;

        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }

    public boolean CheckByUsername(String Username) {
        try {
            long counter
                    = getJinqAccounts()
                    .where(m -> m.getUsername().equals(Username))
                    .count();

            return counter == 0;
        } catch (Exception ex) {
            System.out.println(ex);
            return true;
        }
    }

    public boolean CheckByEmail(String Email) {
        try {
            long counter = getJinqAccounts().where(m -> m.getEmail().equals(Email)).count();

            return counter == 0;
        } catch (Exception ex) {
            System.out.println(ex);
            return true;
        }
    }

    public boolean Insert(Account account) {
        try {
            account.setId(UUID.randomUUID().toString());
            account.setPasswordHash(account.getPasswordHash());
            account.setCreateTime(new Date());
            this.getJPAAccount().create(account);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean Delete(Account account) {
        try {
            getJPAAccount().destroy(account.getId());
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
