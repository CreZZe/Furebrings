package pac;

import ejb.OrdersFacade;
import entities.Customer;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author 224Mi
 */
@Named(value = "orderController")
@Dependent
public class OrderController {

    @EJB
    private OrdersFacade ordersFacade;

    
    
    /**
     * Creates a new instance of OrderController
     */
    public OrderController() {
    }
    
    public void findOrdersByCustomer(Customer cust) {
        ordersFacade.findOrdersByCustomer(cust);
    }
}
