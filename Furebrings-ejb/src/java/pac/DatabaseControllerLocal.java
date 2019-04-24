package pac;

import entities.Account;
import javax.ejb.Local;

/**
 *
 * @author 224Mi
 */
@Local
public interface DatabaseControllerLocal {

    boolean addCustomer(Account acc);
    
}
