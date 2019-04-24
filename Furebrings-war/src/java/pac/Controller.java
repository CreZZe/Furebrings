package pac;

import entities.Account;
import entities.Customer;
import entities.OrderDetails;
import entities.Orders;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author 224Mi
 */
@Named(value = "controller")
@SessionScoped
public class Controller implements Serializable {

    @EJB
    private DatabaseControllerLocal databaseController;

    private Account acc;
    private String mail;
    private String pass;
    private String accRole;
    private Customer customer;
    /**
     * Creates a new instance of Controller
     */
    public Controller() {
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    
    public void addCustomer() {
        databaseController.addCustomer(acc);
    }
    
    public void addAccount() {
        acc = new Account(mail, pass, "Vanlig", null);
        databaseController.addAccount(acc);
    }
    
    public void test() {
        Customer cust = new Customer("Mikael", "Fredriksson", "0737777777", "Toppartorp 123", "19333", "Stockholm", "Sweden");

        Orders order1 = new Orders(cust);
        Orders order2 = new Orders(cust);
        
        OrderDetails det1 = new OrderDetails("Leksak", 4, order1);
        OrderDetails det2 = new OrderDetails("Gunga", 2, order1);
        OrderDetails det3 = new OrderDetails("Fotboll", 5, order2);
        
        List<OrderDetails> details1 = new ArrayList<>();
        details1.add(det1);
        details1.add(det2);
        
        List<OrderDetails> details2 = new ArrayList<>();
        details2.add(det3);
        
        order1.setOrderDetails(details1);
        order2.setOrderDetails(details2);
        
        List<Orders> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);
        
        cust.setOrders(orders);
        
        acc = new Account("224Mikael@gmail.com", "asd123", "Premium", cust);
        addCustomer();
    }
}
