package LibData.Models;

import LibData.Models.Account;
import LibData.Models.Orders;
import LibData.Models.Product;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-29T16:57:15")
@StaticMetamodel(OrderLine.class)
public class OrderLine_ { 

    public static volatile SingularAttribute<OrderLine, Long> unitPrice;
    public static volatile SingularAttribute<OrderLine, Account> createBy;
    public static volatile SingularAttribute<OrderLine, Integer> quantity;
    public static volatile SingularAttribute<OrderLine, Long> vATPrice;
    public static volatile SingularAttribute<OrderLine, Product> productId;
    public static volatile SingularAttribute<OrderLine, Long> totalPrice;
    public static volatile SingularAttribute<OrderLine, Date> createTime;
    public static volatile SingularAttribute<OrderLine, Orders> orderId;
    public static volatile SingularAttribute<OrderLine, Long> discountPrice;
    public static volatile SingularAttribute<OrderLine, String> id;
    public static volatile SingularAttribute<OrderLine, Long> paidPrice;

}