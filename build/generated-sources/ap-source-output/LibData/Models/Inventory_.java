package LibData.Models;

import LibData.Models.Product;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-29T16:57:15")
@StaticMetamodel(Inventory.class)
public class Inventory_ { 

    public static volatile SingularAttribute<Inventory, String> createBy;
    public static volatile SingularAttribute<Inventory, Integer> quantity;
    public static volatile SingularAttribute<Inventory, Product> productId;
    public static volatile SingularAttribute<Inventory, Date> createTime;
    public static volatile SingularAttribute<Inventory, String> idCode;
    public static volatile SingularAttribute<Inventory, String> id;
    public static volatile SingularAttribute<Inventory, Integer> status;

}