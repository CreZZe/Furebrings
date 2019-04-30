/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pac;

import entities.Products;
import ejb.ProductsFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author isami
 */
@Named(value = "productController")
@SessionScoped
public class ProductController implements Serializable {

    @EJB
    private ProductsFacade productsFacade;

    private List<Products> products;
    
    public void fetchAllProducts(){
        products = productsFacade.findAll();
    }
    
    public void fetchProductsByName(String name){
        products = productsFacade.findProductsByName(name);
        System.out.println("test");
    }
    /**
     * Creates a new instance of ProductController
     */
    public ProductController() {
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }
    
    
    
    
}
