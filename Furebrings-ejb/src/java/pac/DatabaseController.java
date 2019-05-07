package pac;

import classes.CartProduct;
import entities.Account;
import entities.Customer;
import entities.OrderDetails;
import entities.Orders;
import entities.Products;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author 224Mi
 */
@Stateful
public class DatabaseController implements DatabaseControllerLocal {

    @PersistenceContext(unitName = "Furebrings-ejbPU")
    private EntityManager em;

    private Account accountDB;
    private List<OrderDetails> cartProductRow = new ArrayList<>();
    
    public void persist(Object object) {
        em.persist(object);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public boolean addCustomerInformation(Customer cust, String mail) {
        try {
            persist(cust);
            
            Query q = em.createQuery("SELECT e FROM Account e WHERE e.mail =:mail");
            q.setParameter("mail", mail);
            Account acc = (Account) q.getSingleResult();
            acc.setCustomer(cust);
            em.merge(acc);
            
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public String checkLogin(Account acc) {
        Query q = em.createQuery("SELECT a FROM Account a WHERE a.mail = :mail AND a.pass = :pass");
        q.setParameter("mail", acc.getMail());
        q.setParameter("pass", acc.getPass());
        try {
            Account accDB = (Account) q.getSingleResult();
            
            if (accDB != null) {
                accountDB = accDB;
                String role = accDB.getAccRole();
                System.out.println(accDB.getAccRole());
                
                if (role.equals("regular") || role.equals("premium")) {
                    //order = new Orders(accDB.getCustomer());
                    return "index";
                }
                else if (role.equals("admin")) {
                    //order = new Orders(accDB.getCustomer());
                    return "admin"; 
                }
            }
        }
        catch (Exception e) {
            //e.printStackTrace();
            String message = "Inloggning misslyckades, se över dina uppgifter.";
            FacesMessage msg= new FacesMessage(FacesMessage.SEVERITY_ERROR,message,null);
            FacesContext.getCurrentInstance().addMessage("form:loggain", msg);
            return null;
        }
        
        return null;
    }

    @Override
    public String getAccountRole() {
        return accountDB.getAccRole();
    }
    
    

    @Override
    public boolean addProductToCart(Products prod, int quantity) {
        int sizeBefore = cartProductRow.size();
        
        for (int i = 0; i < cartProductRow.size(); i++) {
            OrderDetails item = cartProductRow.get(i);
            if (item.getProduct().getName().equals(prod.getName())) {
                if (getQuantity(prod) >= item.getQuantity() + quantity){
                    item.setQuantity(item.getQuantity() + quantity);
                    return true;
                }
            }
        }
        
        cartProductRow.add(new OrderDetails(quantity, prod));
        
        return cartProductRow.size() > sizeBefore;
    }
    
    

    

    /**
     * Lägger till en i kundkorgen på den specifika produkten
     * @param prod
     * @return boolean : false vid problem, annars true
     */
    @Override
    public boolean cartQuantityIncrement(Products prod) {
        for (int i = 0; i < cartProductRow.size(); i++) {
            OrderDetails item = cartProductRow.get(i);
            if (item.getProduct().getName().equals(prod.getName())) {
                if (getQuantity(prod) > item.getQuantity()) {
                    item.setQuantity(item.getQuantity() + 1);
                    return true;
                }
                return false;
            }
        }
        
        return false;
    }

    /**
     * Tar bort en i kundkorgen på den specifika produkten
     * Tar man bort den sista tas även hela produkten bort ur kundkorgen
     * @param prod
     * @return boolean : false vid problem, annars true
     */
    @Override
    public boolean cartQuantityDecrement(Products prod) {
        for (int i = 0; i < cartProductRow.size(); i++) {
            OrderDetails item = cartProductRow.get(i);
            if (item.getProduct().getName().equals(prod.getName())) {
                item.setQuantity(item.getQuantity() - 1);
                
                if (item.getQuantity() == 0)
                    cartProductRow.remove(item);
                
                return true;
            }
        }
        
        return false;
    }

    @Override
    public int getQuantity(Products prod) {
        Query q = em.createQuery("SELECT a FROM Products a WHERE a.name =:name");
        q.setParameter("name", prod.getName());
        try {
            Products p = (Products) q.getSingleResult();
            return p.getQuantityInStore();
        }
        catch (Exception e) {
            return 0;
        }
    }
    
    

    @Override
    public List<CartProduct> getProductsFromCart() {
        List<CartProduct> cartProducts = new ArrayList<>();

        for (int i = 0; i < cartProductRow.size(); i++) {
            OrderDetails item = cartProductRow.get(i);
            CartProduct prod = new CartProduct(item.getProduct().getName(), 
            item.getProduct().getCost(), item.getQuantity());
            cartProducts.add(prod);
        }
        
        return cartProducts;
    }

    @Override
    public int getQuantityOfProductInCart(Products prod) {

        for (int i = 0; i < cartProductRow.size(); i++) {
            OrderDetails item = cartProductRow.get(i);
            if (item.getProduct().getName().equals(prod.getName()))
                return item.getQuantity();
        }
    
        return -1;
    }

    @Override
    public Account getAccountDB() {
        return accountDB;
    }

    @Override
    public void setAccountDB(Account accountDB) {
        this.accountDB = accountDB;
    }

    @Override
    public boolean placeOrder(Account acc) {
        Orders order;
        
        if (acc.getAccRole().equals("premium")) {
            order = new Orders(true, acc.getCustomer());
            order.setOrderDetails(cartProductRow);
            persist(order);
            
            /*
                Ändra kostnaden för premiumkunder
            tempOrder.getOrderDetails().forEach((tempOD) -> {
                tempOD.getProduct().setCost(tempOD.getProduct().getCost() * 0.9f);
            });*/
            
            cartProductRow.forEach((prod) -> {
                prod.setOrder(order);
                persist(prod);
            });
        }
        else {
            order = new Orders(false, acc.getCustomer());
            order.setOrderDetails(cartProductRow);
            persist(order);
            
            cartProductRow.forEach((prod) -> {
                prod.setOrder(order);
                persist(prod);
            });
        }
        
        return true;
    }

    @Override
    public boolean placeOrder(Account acc, String paymentOption, String shipment) {
        Orders order;
        
        if (acc.getAccRole().equals("premium")) {
            order = new Orders(true, paymentOption, shipment, acc.getCustomer());
            order.setOrderDetails(cartProductRow);
            persist(order);
            
            /*
                Ändra kostnaden för premiumkunder
            tempOrder.getOrderDetails().forEach((tempOD) -> {
                tempOD.getProduct().setCost(tempOD.getProduct().getCost() * 0.9f);
            });*/
            
            cartProductRow.forEach((prod) -> {
                prod.setOrder(order);
                persist(prod);
            });
        }
        else {
            order = new Orders(false, paymentOption, shipment, acc.getCustomer());
            order.setOrderDetails(cartProductRow);
            persist(order);
            
            cartProductRow.forEach((prod) -> {
                prod.setOrder(order);
                persist(prod);
            });
        }
        
        return true;
    }
    
    
    
    
    
    
}




/*
    Tar bort 10 % när produkterna skrivs ut
    Tar bort 10 % precis innan ordern läggs

    Loggar man ut ska orginalpriset visas i t.ex. varukorgen
*/