package entities;

import entities.Customer;
import entities.OrderDetails;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-02T13:43:49")
@StaticMetamodel(Orders.class)
public class Orders_ { 

    public static volatile ListAttribute<Orders, OrderDetails> orderDetails;
    public static volatile SingularAttribute<Orders, Long> id;
    public static volatile SingularAttribute<Orders, Customer> customer;

}