package pac;

import classes.CartProduct;
import ejb.ProductsFacade;
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
    private ProductsFacade productsFacade;

    @EJB
    private DatabaseControllerLocal databaseController;
    
    @Inject
    private Controller controller;
    
    private List<CartProduct> cartProducts = new ArrayList<>(Arrays.asList(
            new CartProduct("Lägg till produkt", 0, 0)
    ));
    
    private float totalPrice = 0;
        
    /**
     * Creates a new instance of CartController
     */
    public CartController() {
    }

    public List<CartProduct> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<CartProduct> cartProducts) {
        this.cartProducts = cartProducts;
    }
    
    
    public String productsFromCart() {
        cartProducts = databaseController.getProductsFromCart();
        return "cart";
    }
    /*
        Jag måste ha en knapp för att kunna calla på metoden för att hämta
            informationen från databassidan.
        Varukorgsknappen uppe i hörnet bör calla på funktionen för att hämta
            informationen från databasen
    */
    
    // Bygga ihop prod-objektet på frontend eller backend??
    public String addToCart(Products item) {
        if (databaseController.getQuantity(item) > databaseController.getQuantityOfProductInCart(item)) {
            if (databaseController.addProductToCart(item, 1)) {
                cartProducts = databaseController.getProductsFromCart();
                calcTotalPrice();
                return "cart";
            }
        }
        return "productpage";
    }
    
    public String cartQuantityIncrement(CartProduct cartProd) {
        if (databaseController.cartQuantityIncrement(productsFacade.findProductsByName(cartProd.getName()).get(0))) {
            cartProducts = databaseController.getProductsFromCart();
            calcTotalPrice();
        }
        return "cart";
    }
    
    public String cartQuantityDecrement(CartProduct cartProd) {
        if (databaseController.cartQuantityDecrement(productsFacade.findProductsByName(cartProd.getName()).get(0))) {
            cartProducts = databaseController.getProductsFromCart();
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
    
    public String createOrder() {
        if ( databaseController.placeOrder(controller.getAccountDB()) )
            return "order";
        return "cart";
    }
}
