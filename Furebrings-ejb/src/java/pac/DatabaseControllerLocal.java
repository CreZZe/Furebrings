package pac;

import entities.Account;
import entities.Customer;
import javax.ejb.Local;

/**
 *
 * @author 224Mi
 */
@Local
public interface DatabaseControllerLocal {

    boolean addCustomer(Account acc);

    boolean addAccount(Account acc);

    boolean addCustomerInformation(Customer cust, String mail);

    boolean addAccountWithCustomerInformation(Account acc);

    String checkLogin(Account acc);

    String getAccountRole();
    
}
