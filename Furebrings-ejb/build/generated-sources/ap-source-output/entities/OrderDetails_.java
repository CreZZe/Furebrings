package entities;

import entities.Orders;
import entities.Products;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

<<<<<<< HEAD
@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-04-30T10:53:15")
=======
@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-04-30T09:07:03")
>>>>>>> 54925b3d78eb640d87dcf0c6ec9c37c0d5a68220
@StaticMetamodel(OrderDetails.class)
public class OrderDetails_ { 

    public static volatile SingularAttribute<OrderDetails, Products> product;
    public static volatile SingularAttribute<OrderDetails, Integer> quantity;
    public static volatile SingularAttribute<OrderDetails, Long> id;
    public static volatile SingularAttribute<OrderDetails, Orders> order;

}