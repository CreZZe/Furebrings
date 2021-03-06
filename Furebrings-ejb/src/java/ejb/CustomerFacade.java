/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.Account;
import entities.Customer;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author isami
 */
@Stateless
public class CustomerFacade extends AbstractFacade<Customer> {

    @EJB
    private AccountFacade accountFacade;

    @PersistenceContext(unitName = "Furebrings-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomerFacade() {
        super(Customer.class);
    }
    
    public Customer findCustomerByName(String fName, String lName) {
        Query q = em.createQuery("SELECT c FROM Customer c WHERE c.firstName =:firstName AND c.lastName =:lastName");
        q.setParameter("firstName", fName);
        q.setParameter("lastName", lName);
        
        try {
            return (Customer) q.getSingleResult();
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public boolean addTotalOrderValueToCustomerByName(String fName, String lName, float amount) {
        Query q = em.createQuery("SELECT c FROM Customer c WHERE c.firstName =:firstName AND c.lastName =:lastName");
        q.setParameter("firstName", fName);
        q.setParameter("lastName", lName);
        
        try {
            Customer cust = (Customer) q.getSingleResult();
            cust.setTotalSpent(cust.getTotalSpent() + amount);
            edit(cust);
            
            if (cust.getTotalSpent() >= 500000) {
                Account acc = accountFacade.findAccountByCustomerID(cust.getId());
                acc.setAccRole("premium");
                accountFacade.edit(acc);
            }
            
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Customer> findAll() {
        Query q = em.createQuery("select o from Customer o");
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        return q.getResultList();
    }
}
