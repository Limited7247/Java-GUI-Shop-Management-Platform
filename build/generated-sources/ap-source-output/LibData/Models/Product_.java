package LibData.Models;

import LibData.Models.Account;
import LibData.Models.Book;
import LibData.Models.Inventory;
import LibData.Models.OrderLine;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-29T16:57:15")
@StaticMetamodel(Product.class)
public class Product_ { 

    public static volatile CollectionAttribute<Product, OrderLine> orderLineCollection;
    public static volatile SingularAttribute<Product, Date> createTime;
    public static volatile SingularAttribute<Product, Account> createdBy;
    public static volatile SingularAttribute<Product, String> idCode;
    public static volatile SingularAttribute<Product, Long> price;
    public static volatile CollectionAttribute<Product, Inventory> inventoryCollection;
    public static volatile SingularAttribute<Product, String> name;
    public static volatile SingularAttribute<Product, String> id;
    public static volatile SingularAttribute<Product, String> type;
    public static volatile CollectionAttribute<Product, Book> bookCollection;
    public static volatile SingularAttribute<Product, Integer> status;

}