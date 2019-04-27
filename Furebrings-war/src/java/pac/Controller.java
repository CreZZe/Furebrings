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
    
    
    private String fname;
    private String ename;
    private String phoneNumber;
    private String address;
    private String postalCode;
    private String city;
    private String country;
    private Customer cust;
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

    public String getAccRole() {
        return accRole;
    }

    public void setAccRole(String accRole) {
        this.accRole = accRole;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    public void addCustomer() {
        databaseController.addCustomer(acc);
    }
    
    public void addAccount() {
        acc = new Account(mail, pass, "regular", null);
        databaseController.addAccount(acc);
    }
    
    public void addCustomerInformation() {
        cust = new Customer(fname, ename, phoneNumber, address, postalCode, city, country, 0);
        databaseController.addCustomerInformation(cust, mail);
    }
    
    public void addAccountWithCustomerInformation() {
        cust = new Customer(fname, ename, phoneNumber, address, postalCode, city, country, 0);
        acc = new Account(mail, pass, "regular", cust);
        databaseController.addAccountWithCustomerInformation(acc);
    }
    
    public String login() {
        acc = new Account(mail, pass);
            /*
                if str == customer --> Inloggningen gick igenom, vanlig eller premiumkund inloggad
                if str == admin --> Inloggningen gick igenom, admin inloggad
                if str == null --> Inloggningen lyckades inte, ingen inloggad
            */
        return databaseController.checkLogin(acc);
    }
    
    public void dbRole() {
        fname = databaseController.getAccountRole();
    }
    
    public void test() {
        cust = new Customer("Mikael", "Fredriksson", "0737777777", "Toppartorp 123", "19333", "Stockholm", "Sweden");

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
        
        acc = new Account("224Mikael@gmail.com", "asd123", "premium", cust);
        addCustomer();
    }
}
