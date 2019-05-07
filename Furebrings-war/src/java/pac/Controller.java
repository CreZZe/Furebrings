package pac;

import classes.CartProduct;
import ejb.AccountFacade;
import entities.Account;
import entities.Customer;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;

/**
 *
 * @author 224Mi
 */
@Named(value = "controller")
@SessionScoped
public class Controller implements Serializable {

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
    
    private UICommand logoutLink;
    private UICommand loginLink;
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
    
    public UICommand getLogoutLink() {
        return logoutLink;
    }

    public void setLogoutLink(UICommand logoutLink) {
        this.logoutLink = logoutLink;
    }

    public UICommand getLoginLink() {
        return loginLink;
    }

    public void setLoginLink(UICommand loginLink) {
        this.loginLink = loginLink;
    }
    
    public void addAccount() {
        acc = new Account(mail, pass, "regular", null);
        accountFacade.register(acc);
    }
    
    public void addCustomerInformation() {
        cust = new Customer(fname, ename, phoneNumber, address, postalCode, city, country, 0);
        databaseController.addCustomerInformation(cust, mail);
    }
    
    public String addAccountWithCustomerInformation() {
        cust = new Customer(fname, ename, phoneNumber, address, postalCode, city, country, 0);
        acc = new Account(mail, pass, "regular", cust);
        if (!accountFacade.register(acc)) {
            String message = "Registrering misslyckades! E-postadressen är upptagen!";
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
            FacesContext.getCurrentInstance().addMessage("form:email", msg);
            return "register";
        }
        else {
            databaseController.checkLogin(acc);
            checkLogin();
            return "index";
        }
    }
    
    public String login() {
        acc = new Account(mail, pass);
            /*
                if str == customer --> Inloggningen gick igenom, vanlig eller premiumkund inloggad
                if str == admin --> Inloggningen gick igenom, admin inloggad
                if str == null --> Inloggningen lyckades inte, ingen inloggad
            */ 
        String result = databaseController.checkLogin(acc);
        checkLogin();
        return result;
    }
    
    public void dbRole() {
        fname = databaseController.getAccountRole();
    }
    
    public boolean isLoggedIn(){
        Account account = databaseController.getAccountDB();
        System.out.println(account == null);
        return account != null;
    }
    
    public void checkLogin(){        
        if(!isLoggedIn()){
            loginLink.setRendered(true);
            logoutLink.setRendered(false);
        } else {
            logoutLink.setValue(databaseController.getAccountDB().getMail() + "/logga ut");
            logoutLink.setRendered(true);
            loginLink.setRendered(false);

        }   
    }
    
    public String logout(){
        databaseController.setAccountDB(null);
        checkLogin();
        return "index";
    }
     
    public Account getAccountDB() {
        return databaseController.getAccountDB();
    }
}
