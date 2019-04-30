package entities;

import entities.Categories;
import entities.OrderDetails;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

<<<<<<< HEAD
@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-04-30T10:53:15")
=======
@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-04-30T09:07:03")
>>>>>>> 54925b3d78eb640d87dcf0c6ec9c37c0d5a68220
@StaticMetamodel(Products.class)
public class Products_ { 

    public static volatile ListAttribute<Products, OrderDetails> orderDetails;
    public static volatile SingularAttribute<Products, Float> cost;
    public static volatile SingularAttribute<Products, Integer> quantityInStore;
    public static volatile SingularAttribute<Products, String> name;
    public static volatile SingularAttribute<Products, String> description;
    public static volatile SingularAttribute<Products, Long> id;
    public static volatile SingularAttribute<Products, Categories> category;

}