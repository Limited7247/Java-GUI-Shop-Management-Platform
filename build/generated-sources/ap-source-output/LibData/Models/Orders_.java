package LibData.Models;

import LibData.Models.Account;
import LibData.Models.OrderLine;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-29T16:57:15")
@StaticMetamodel(Orders.class)
public class Orders_ { 

    public static volatile CollectionAttribute<Orders, OrderLine> orderLineCollection;
    public static volatile SingularAttribute<Orders, Long> totalPrice;
    public static volatile SingularAttribute<Orders, String> idCode;
    public static volatile SingularAttribute<Orders, Long> discount;
    public static volatile SingularAttribute<Orders, String> guestEmail;
    public static volatile SingularAttribute<Orders, String> guestName;
    public static volatile SingularAttribute<Orders, Long> paidPrice;
    public static volatile SingularAttribute<Orders, Account> createBy;
    public static volatile SingularAttribute<Orders, String> guestAddress;
    public static volatile SingularAttribute<Orders, Long> vATPrice;
    public static volatile SingularAttribute<Orders, Date> createTime;
    public static volatile SingularAttribute<Orders, String> guestPhone;
    public static volatile SingularAttribute<Orders, String> details;
    public static volatile SingularAttribute<Orders, String> id;
    public static volatile SingularAttribute<Orders, Integer> status;

}