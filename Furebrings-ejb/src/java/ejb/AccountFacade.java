/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.Account;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author isami
 */
@Stateless
public class AccountFacade extends AbstractFacade<Account> {

    @PersistenceContext(unitName = "Furebrings-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccountFacade() {
        super(Account.class);
    }

    public boolean register(Account acc) {
        Query q = em.createQuery("SELECT a FROM Account a WHERE a.mail =:mail");
        q.setParameter("mail", acc.getMail());
        try {
            Account accDB = (Account) q.getSingleResult();
            return false;
        }
        catch (Exception e) {
            try {
                create(acc);
                return true;
            }
            catch (Exception ex) {
                return false;
            }
        }
    } 
}
