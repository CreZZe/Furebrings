package pac;

import classes.CartProduct;
import ejb.AccountFacade;
import ejb.CustomerFacade;
import ejb.ProductsFacade;
import entities.Account;
import entities.Customer;
import entities.Products;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Inject;

/**
 *
 * @author 224Mi
 */
@Named(value = "cartController")
@SessionScoped
public class CartController implements Serializable {

    @EJB
    private AccountFacade accountFacade;

    @EJB
    private CustomerFacade customerFacade;

    @EJB
    private ProductsFacade productsFacade;

    @EJB
    private DatabaseControllerLocal databaseController;
    
    @Inject
    private Controller controller;
    
    @Inject
    private ProductController prodController;
    
    private String phoneNumber;
    private String address;
    private String postalCode;
    private String city;
    private String country;
    
    private String paymentOption;
    private String shipment;
    
    private List<CartProduct> cartProducts = new ArrayList<>(Arrays.asList(
            new CartProduct("Lägg till produkt", 0, 0)
    ));
    
    private float totalPrice = 0;
        
    /**
     * Creates a new instance of CartController
     */
    public CartController() {
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

    public List<CartProduct> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<CartProduct> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public String getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
    }

    public String getShipment() {
        return shipment;
    }

    public void setShipment(String shipment) {
        this.shipment = shipment;
    }
    
    
    public String productsFromCart() {
        //cartProducts = databaseController.getProductsFromCart();
        cartProducts = databaseController.getCartProducts(controller.getAccountDB());
        
        return "cart";
    }
    /*
        Jag måste ha en knapp för att kunna calla på metoden för att hämta
            informationen från databassidan.
        Varukorgsknappen uppe i hörnet bör calla på funktionen för att hämta
            informationen från databasen
    */
    
    // Bygga ihop prod-objektet på frontend eller backend??
    public String addToCart(Products prod) {
        Products item = productsFacade.findProductByName(prod.getName());
        if (item == null) {
            return "productpage";
        }
        
        if (databaseController.getQuantity(item) > databaseController.getQuantityOfProductInCart(item)) {
            if (databaseController.addProductToCart(item, 1)) {
                cartProducts = databaseController.getCartProducts(controller.getAccountDB());
                calcTotalPrice();
                return "cart";
            }
        }
        return "productpage";
    }
    
    public String cartQuantityIncrement(CartProduct cartProd) {
        if (databaseController.cartQuantityIncrement(productsFacade.findProductsByName(cartProd.getName()).get(0))) {
            cartProducts = databaseController.getCartProducts(controller.getAccountDB());
            calcTotalPrice();
        }
        return "cart";
    }
    
    public String cartQuantityDecrement(CartProduct cartProd) {
        if (databaseController.cartQuantityDecrement(productsFacade.findProductsByName(cartProd.getName()).get(0))) {
            cartProducts = databaseController.getCartProducts(controller.getAccountDB());
            calcTotalPrice();
        }
        return "cart";
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    private void calcTotalPrice() {
        totalPrice = 0;
        cartProducts.forEach((prod) -> {
            totalPrice += prod.getTotaltProductPrice();
        });
    }
    
    public String toOrderPage() {        
        if (controller.getAccountDB() == null)
            return "login";
        
        return "order";
    }
    
    public String createOrder() {
        System.out.println(phoneNumber + " " + address + " " + postalCode + " " + city + " " + country);
        
        Account accDB = accountFacade.findAccountByMail(controller.getAccountDB().getMail());
        Customer cust = accDB.getCustomer();
        System.out.println(accDB.getCustomer());
        

        cust.setAddress(address);
        cust.setCity(city);
        cust.setCountry(country);
        cust.setPhoneNumber(phoneNumber);
        cust.setPostalCode(postalCode);
        customerFacade.edit(cust);
                
        if ( databaseController.placeOrder(controller.getAccountDB(), paymentOption, shipment) ) {
            customerFacade.addTotalOrderValueToCustomerByName(cust.getFirstName(), cust.getLastName(), getTotalPrice());
            prodController.fetchAllProducts();
            return "orderconfirm";
        }
        
        return "order";
    }
}
