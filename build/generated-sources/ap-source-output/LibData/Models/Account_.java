package LibData.Models;

import LibData.Models.Book;
import LibData.Models.OrderLine;
import LibData.Models.Orders;
import LibData.Models.Product;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-29T16:57:15")
@StaticMetamodel(Account.class)
public class Account_ { 

    public static volatile CollectionAttribute<Account, OrderLine> orderLineCollection;
    public static volatile CollectionAttribute<Account, Product> productCollection;
    public static volatile SingularAttribute<Account, Date> createTime;
    public static volatile SingularAttribute<Account, String> id;
    public static volatile CollectionAttribute<Account, Orders> ordersCollection;
    public static volatile CollectionAttribute<Account, Book> bookCollection;
    public static volatile SingularAttribute<Account, String> passwordHash;
    public static volatile SingularAttribute<Account, String> email;
    public static volatile SingularAttribute<Account, String> username;

}