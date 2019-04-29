package pac;

import entities.Account;
import entities.Customer;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author 224Mi
 */
@Stateful
public class DatabaseController implements DatabaseControllerLocal {

    @PersistenceContext(unitName = "Furebrings-ejbPU")
    private EntityManager em;

    private Account accountDB;
    
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

    @Override
    public boolean addCustomerInformation(Customer cust, String mail) {
        try {
            persist(cust);
            
            Query q = em.createQuery("SELECT e FROM Account e WHERE e.mail =:mail");
            q.setParameter("mail", mail);
            Account acc = (Account) q.getSingleResult();
            acc.setCustomer(cust);
            em.merge(acc);
            
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean addAccountWithCustomerInformation(Account acc) {
        Query q = em.createQuery("SELECT a FROM Account a WHERE a.mail =:mail");
        q.setParameter("mail", acc.getMail());
        try {
            Account accDB = (Account) q.getSingleResult();
            return false;
        }
        catch (Exception e) {
            try {
                persist(acc);
                return true;
            }
            catch (Exception ex) {
                return false;
            }
        }
    }

    @Override
    public String checkLogin(Account acc) {
        Query q = em.createQuery("SELECT a FROM Account a WHERE a.mail =:mail AND a.pass =:pass");
        q.setParameter("mail", acc.getMail());
        q.setParameter("pass", acc.getPass());
        try {
            Account accDB = (Account) q.getSingleResult();
            
            if (accDB != null) {
                accountDB = accDB;
                String role = accDB.getAccRole();
                System.out.println(accDB.getAccRole());
                
                if (role.equals("regular") || role.equals("premium"))
                    return "customer";
                else if (role.equals("admin"))
                    return "admin"; 
            }
        }
        catch (Exception e) {
            return null;
        }
        
        return null;
    }

    @Override
    public String getAccountRole() {
        return accountDB.getAccRole();
    }
    
    
}
