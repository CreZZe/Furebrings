package pac;

import classes.CartProduct;
import entities.Products;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author 224Mi
 */
@Named(value = "cartController")
@SessionScoped
public class CartController implements Serializable {

    @EJB
    private DatabaseControllerLocal databaseController;

    private List<CartProduct> cartProducts = new ArrayList<>(Arrays.asList(
            new CartProduct("Lägg till produkt", 0, 0)
    ));
        
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
        if (databaseController.addProductToCart(item, 1)) {
            cartProducts = databaseController.getProductsFromCart();
            return "cart";
        }
        return "index";
    }
    
    public boolean cartQuantityIncrement(Products prod) {
        return databaseController.cartQuantityIncrement(prod);
    }
    
    public boolean cartQuantityDecrement(Products prod) {
        return databaseController.cartQuantityDecrement(prod);
    }
}
