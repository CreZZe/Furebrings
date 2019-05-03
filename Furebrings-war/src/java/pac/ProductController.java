/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pac;

import ejb.AccountFacade;
import entities.Products;
import ejb.ProductsFacade;
import entities.Account;
import entities.Categories;
import entities.Customer;
import entities.OrderDetails;
import entities.Orders;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
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
    private AccountFacade accountFacade;

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
        init();
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
    
    
    public void init() {
        if (productsFacade.findAll().isEmpty()) {
            Customer cust = new Customer("Mikael", "Fredriksson", "0737777777", "Toppartorp 123", "19333", "Stockholm", "Sweden");
            Customer cust2 = new Customer("Göran", "Petterson", "0739231233", "Odengatan 23", "19233", "Stockholm", "Sweden", 550000);
            
            Orders order1 = new Orders(cust);
            Orders order2 = new Orders(cust);

            Categories cat1 = new Categories("Leksaker");
            Categories cat2 = new Categories("Cyklar");
            Categories cat3 = new Categories("Mat");

            Products prod1 = new Products("Brandbil", 29, 15, "Röd brandbil för barn mellan 0-5 år.", cat1);
            Products prod2 = new Products("Docka", 12, 3, "Docka för barn mellan 3-10 år.", cat1);
            Products prod3 = new Products("Studsboll", 20, 35, "Studsboll för barn mellan 10-15 år.", cat1);
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

            cust2.setOrders(orders);

            /*Account acc = new Account("224Mikael@gmail.com", "asd123", "regular", cust);
            accountFacade.register(acc);
            
            Account acc2 = new Account("admin@admin.com", "Adminpass123", "admin");
            accountFacade.register(acc2);*/
            
            Account acc3 = new Account("premium@premium.com", "Premiumpass123", "premium", cust2);
            accountFacade.register(acc3);
            
            //KOLLA PÅ DET HÄR
        }
    }
}
