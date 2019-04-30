package entities;

import entities.Products;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

<<<<<<< HEAD
@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-04-30T10:53:15")
=======
@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-04-30T09:07:03")
>>>>>>> 54925b3d78eb640d87dcf0c6ec9c37c0d5a68220
@StaticMetamodel(Categories.class)
public class Categories_ { 

    public static volatile SingularAttribute<Categories, String> name;
    public static volatile SingularAttribute<Categories, Long> id;
    public static volatile ListAttribute<Categories, Products> products;

}