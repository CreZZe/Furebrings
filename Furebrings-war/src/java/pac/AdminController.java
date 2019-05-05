package pac;

import ejb.CustomerFacade;
import ejb.OrdersFacade;
import entities.Customer;
import entities.Orders;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author 224Mi
 */
@Named(value = "adminController")
@SessionScoped
public class AdminController implements Serializable {

    @EJB
    private OrdersFacade ordersFacade;

    @EJB
    private CustomerFacade customerFacade;

    private List<Customer> customers;
    
    private List<Orders> orders;
    
    private Customer currentCustomer;
    private Orders currentOrder;
    
    private float totalPrice;
    
    /**
     * Creates a new instance of AdminController
     */
    public AdminController() {
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }
    
    @PostConstruct
    public void fetchAllCustomers() {
        customers = customerFacade.findAll();
    }
    
    public String profile(Customer cust) {
        currentCustomer = cust;
        return "user";
    }

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    public Orders getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Orders currentOrder) {
        this.currentOrder = currentOrder;
    }
    
    public String order(Orders order) {
        currentOrder = order;
        calcTotalPrice();
        return "userOrder";
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    private void calcTotalPrice() {
        totalPrice = 0;
        currentOrder.getOrderDetails().forEach((row) -> {
            totalPrice += (row.getProduct().getCost()*row.getQuantity());
        });
    }
}
