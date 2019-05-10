/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.Customer;
import entities.Orders;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author isami
 */
@Stateless
public class OrdersFacade extends AbstractFacade<Orders> {

    @PersistenceContext(unitName = "Furebrings-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrdersFacade() {
        super(Orders.class);
    }

    public List<Orders> findOrdersByCustomer(Customer cust) {
        List<Orders> allOrders = findAll();
        List<Orders> filteredOrders = new ArrayList<>();
        allOrders.stream().filter((order) -> (order.getCustomer() == cust)).forEachOrdered((order) -> {
            filteredOrders.add(order);
        });
        
        return filteredOrders;
    }
    
    
}
