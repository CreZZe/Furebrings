/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.Products;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author isami
 */
@Stateless
public class ProductsFacade extends AbstractFacade<Products> {

    @PersistenceContext(unitName = "Furebrings-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductsFacade() {
        super(Products.class);
    }
    
    public List<Products> findProductsByName(String name){
        return em.createQuery("select object(o) from Products o " +
                    "where lower(o.name) like :name")
                    .setParameter("name","%" + name.toLowerCase() + "%")
                    .getResultList();       
    }
    
    public Products findProductByName(String name) {
        Query q = em.createQuery("SELECT p FROM Products p WHERE p.name =:name");
        q.setParameter("name", name);
        
        try {
            return (Products) q.getSingleResult();
        }
        catch (Exception e) {
            return null;
        }
    }
}
