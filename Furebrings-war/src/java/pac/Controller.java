package pac;

import ejb.AccountFacade;
import ejb.CategoriesFacade;
import ejb.ProductsFacade;
import entities.Account;
import entities.Categories;
import entities.Customer;
import entities.OrderDetails;
import entities.Orders;
import entities.Products;
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
    private CategoriesFacade categoriesFacade;

    @EJB
    private ProductsFacade productsFacade;

    @EJB
    private AccountFacade accountFacade;

    @EJB
    private DatabaseControllerLocal databaseController;
    
    

    private Account acc;
    private String mail;
    private String pass;
    private String pass2;
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

    public String getPass2() {
        return pass2;
    }

    public void setPass2(String pass2) {
        this.pass2 = pass2;
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
    
    /*public void addCustomer() {
        databaseController.addCustomer(acc);
    }*/
    
    public void addAccount() {
        acc = new Account(mail, pass, "regular", null);
        accountFacade.register(acc);
    }
    
    public void addCustomerInformation() {
        cust = new Customer(fname, ename, phoneNumber, address, postalCode, city, country, 0);
        databaseController.addCustomerInformation(cust, mail);
    }
    
    public void addAccountWithCustomerInformation() {
        cust = new Customer(fname, ename, phoneNumber, address, postalCode, city, country, 0);
        acc = new Account(mail, pass, "regular", cust);
        accountFacade.register(acc);
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
        
        Categories cat1 = new Categories("Leksaker");
        Categories cat2 = new Categories("Cyklar");
        Categories cat3 = new Categories("Mat");
        
        Products prod1 = new Products("Brandbil", 29, 15, "Röd brandbil för barn mellan 0-5 år.", cat1);
        Products prod2 = new Products("Docka", 12, 3, "Docka för barn mellan 3-10 år.", cat2);
        Products prod3 = new Products("Studsboll", 20, 35, "Studsboll för barn mellan 10-15 år.", cat3);
        Products prod4 = new Products("Snabb cykel", 1999, 4, "En väldigt snabb cykel för de som gillar fart!", cat2);
        Products prod5 = new Products("Långsam cykel", 999, 5, "En mycket långsam cykel.", cat2);
        Products prod6 = new Products("Tandemcykel", 1490, 2, "Tandemcykel som är perfekt för par.", cat2);
        Products prod7 = new Products("Potatis", 2, 78, "Färskpotatis, perfekt för potatissallader!", cat3);
        Products prod8 = new Products("Gurka", 15, 28, "Gurka från Litauen.", cat3);
        Products prod9 = new Products("Paprika", 8, 17, "Paket innehållande en röd, en grön och en gul paprika.", cat3);
        Products prod10 = new Products("Mjölk", 12, 32, "Lättmjölk från ARLA.", cat3);
        Products prod11 = new Products("Sirap", 27, 16, "Seg sirap. Perfekt för matlagning!", cat3);
        Products prod12 = new Products("Skogaholmslimpa", 18f, 12, "Bröd bakat på vete, siktat rågmjöl och sirap, "
                + "vilket ger en mjuk och saftig limpa som smakar som en limpa ska smaka.", cat3);
        
        OrderDetails det1 = new OrderDetails(prod1, 4, order1);
        OrderDetails det2 = new OrderDetails(prod2, 2, order1);
        OrderDetails det3 = new OrderDetails(prod3, 5, order1);
        OrderDetails det4 = new OrderDetails(prod4, 4, order1);
        OrderDetails det5 = new OrderDetails(prod5, 2, order1);
        OrderDetails det6 = new OrderDetails(prod6, 5, order1);
        OrderDetails det7 = new OrderDetails(prod7, 4, order2);
        OrderDetails det8 = new OrderDetails(prod8, 2, order2);
        OrderDetails det9 = new OrderDetails(prod9, 5, order2);
        OrderDetails det10 = new OrderDetails(prod10, 4, order2);
        OrderDetails det11 = new OrderDetails(prod11, 2, order2);
        OrderDetails det12 = new OrderDetails(prod12, 5, order2);
        
        List<OrderDetails> details1 = new ArrayList<>();
        details1.add(det1);
        details1.add(det2);
        details1.add(det3);
        details1.add(det4);
        details1.add(det5);
        details1.add(det6);
        
        List<OrderDetails> details2 = new ArrayList<>();
        details2.add(det7);
        details1.add(det8);
        details1.add(det9);
        details1.add(det10);
        details1.add(det11);
        details1.add(det12);
        
        order1.setOrderDetails(details1);
        order2.setOrderDetails(details2);
        
        List<Orders> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);
        
        cust.setOrders(orders);
        
        acc = new Account("224Mikael@gmail.com", "asd123", "premium", cust);
        accountFacade.register(acc);

        // regular - premium - admin
    }
}
