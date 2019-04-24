package pac;

import entities.Account;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 224Mi
 */
@Stateful
public class DatabaseController implements DatabaseControllerLocal {

    @PersistenceContext(unitName = "Furebrings-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public boolean addCustomer(Account acc) {
        try {
            persist(acc);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean addAccount(Account acc) {
        try {
            persist(acc);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    
}
