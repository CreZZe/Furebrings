package entities;

import entities.Customer;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-10T10:10:50")
@StaticMetamodel(Account.class)
public class Account_ { 

    public static volatile SingularAttribute<Account, String> accRole;
    public static volatile SingularAttribute<Account, String> mail;
    public static volatile SingularAttribute<Account, String> pass;
    public static volatile SingularAttribute<Account, Long> id;
    public static volatile SingularAttribute<Account, Customer> customer;

}