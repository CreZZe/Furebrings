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
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
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

    private List<Products> allProducts;
    private List<Products> filteredProducts;
    private String searchName;
    private Products chosenProduct;
    
    @PostConstruct
    public void fetchAllProducts(){
        allProducts = productsFacade.findAll();
        //filteredProducts = allProducts;
    }
    
    public void fetchProductsByName(String name){
        filteredProducts = productsFacade.findProductsByName(name);
    }
    
    public String showAllProducts(){
        filteredProducts = allProducts;
        return "productpage";
    }
    
    public String showProductDetail(Products product){
        chosenProduct = product;
        return "item";
    }
    
    public String filterProductByCategoryId(int id){
        filteredProducts = allProducts.stream().filter(p -> p.getCategory().getId() == id).collect(Collectors.toList());
        return "productpage";
    }
    
    public String filterProductByName(String name){
        filteredProducts = allProducts.stream().filter(p -> (p.getName().toLowerCase().indexOf(name.toLowerCase()))>=0).collect(Collectors.toList());
        return "productpage";
    }
    /**
     * Creates a new instance of ProductController
     */
    public ProductController() {
    }

    public List<Products> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(List<Products> allProducts) {
        this.allProducts = allProducts;
    }

    public Products getChosenProduct() {
        return chosenProduct;
    }

    public void setChosenProduct(Products chosenProduct) {
        this.chosenProduct = chosenProduct;
    }

    public List<Products> getFilteredProducts() {
        return filteredProducts;
    }

    public void setFilteredProducts(List<Products> filteredProducts) {
        this.filteredProducts = filteredProducts;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }
    
    
    
    
       
}
