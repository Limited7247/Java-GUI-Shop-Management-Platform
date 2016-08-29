package LibData.Models;

import LibData.Models.Account;
import LibData.Models.Product;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-29T16:57:15")
@StaticMetamodel(Book.class)
public class Book_ { 

    public static volatile CollectionAttribute<Book, Product> productCollection;
    public static volatile SingularAttribute<Book, Integer> year;
    public static volatile SingularAttribute<Book, String> idCode;
    public static volatile SingularAttribute<Book, String> author;
    public static volatile SingularAttribute<Book, String> isbn;
    public static volatile SingularAttribute<Book, String> type;
    public static volatile SingularAttribute<Book, String> picture;
    public static volatile SingularAttribute<Book, Date> createTime;
    public static volatile SingularAttribute<Book, Account> createdBy;
    public static volatile SingularAttribute<Book, BigInteger> price;
    public static volatile SingularAttribute<Book, String> name;
    public static volatile SingularAttribute<Book, String> publisher;
    public static volatile SingularAttribute<Book, String> details;
    public static volatile SingularAttribute<Book, String> id;
    public static volatile SingularAttribute<Book, Integer> status;

}