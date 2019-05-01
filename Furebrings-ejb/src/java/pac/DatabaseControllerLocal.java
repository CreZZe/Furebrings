package pac;

import classes.CartProduct;
import entities.Account;
import entities.Customer;
import entities.OrderDetails;
import entities.Products;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author 224Mi
 */
@Local
public interface DatabaseControllerLocal {

    boolean addCustomerInformation(Customer cust, String mail);

    String checkLogin(Account acc);

    String getAccountRole();

    boolean addProductToCart(Products prod, int quantity);    

    boolean cartQuantityIncrement(Products prod);

    boolean cartQuantityDecrement(Products prod);

    int getQuantity(Products prod);

    List<CartProduct> getProductsFromCart();

    int getQuantityOfProductInCart(Products prod);

}
