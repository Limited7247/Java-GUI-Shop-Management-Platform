/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibData.Providers;

import LibData.JPAControllers.AccountJpaController;
import LibData.Models.Account;
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

    private EntityManagerFactory entityManagerFactory
            = Persistence.createEntityManagerFactory("MVCDemoPU");

    private EntityManager em = entityManagerFactory.createEntityManager();

    private AccountJpaController jpaAccount = new AccountJpaController(entityManagerFactory);
    
    private JinqJPAStreamProvider streams
            = new JinqJPAStreamProvider(entityManagerFactory); 

//    private JPAJinqStream<Account> _accounts = streams.streamAll(em, Account.class);
    
    /// Streams will close after used. Need to reuse it.
    private JPAJinqStream<Account> getJinqAccounts()
    {
        return streams.streamAll(em, Account.class);
    }

    public List<Account> getAll() {
        try {
            return getJinqAccounts().sortedDescendingBy(m -> m.getCreateTime()).toList();

        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }

    public boolean CheckByUsernameAndPasswordHash(String Username, String PasswordHash) {
        try {
            long counter = getJinqAccounts().where(m -> m.getUsername().equals(Username) && m.getPasswordHash().equals(PasswordHash)).count();
             
            return counter == 1;

        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }
    
    public boolean CheckByUsername(String Username) {
        try {
            long counter = getJinqAccounts().where(m -> m.getUsername().equals(Username)).count();
             
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
            this.jpaAccount.create(account);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    
    public boolean Delete(Account account) {
        try {
            jpaAccount.destroy(account.getId());
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
