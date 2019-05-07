package pac;

import ejb.AccountFacade;
import ejb.CategoriesFacade;
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
    private CategoriesFacade categoriesFacade;

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
        init();
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
        if(name==null || name.trim().equals("")){
            filteredProducts = null;
        } else {
            filteredProducts = allProducts.stream().filter(p -> (p.getName().toLowerCase().indexOf(name.trim().toLowerCase()))>=0).collect(Collectors.toList());
        }
        searchName = "";
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
            List<Categories> cats = new ArrayList<>();
            
            cats.add(new Categories("Hybridcykel"));
            cats.add(new Categories("Damcykel"));
            cats.add(new Categories("Barncykel"));
            cats.add(new Categories("Elcykel"));
            cats.add(new Categories("Fatbykes"));
            
            for (int i = 0; i < cats.size(); i++) {
                categoriesFacade.create(cats.get(i));
            }

            List<Products> prods = new ArrayList<>();
            
            prods.add(new Products("Crossway Striker Gent 18", 4499, 15, "En mycket långsam cykel.", cats.get(0)));
            prods.add(new Products("SC Trainer FF MNS 19", 3499, 3, "Docka för barn mellan 3-10 år.", cats.get(0)));
            prods.add(new Products("Crossway Striker Lady 18", 550000, 35, "Dyrast cykel vi har", cats.get(0)));
            prods.add(new Products("Vilma 28 3s classic 19", 3499, 4, "En väldigt snabb cykel för de som gillar fart!", cats.get(1)));
            prods.add(new Products("Live 7 18", 4299, 5, "Elegant och klassisk stadscykel för damer.", cats.get(1)));
            prods.add(new Products("Vilma 26 3s classic 19", 2999, 2, "Tandemcykel som är perfekt för par.", cats.get(1)));
            prods.add(new Products("XC 260 Pro 19", 4499, 78, "Juniorcykel med 26\"-hjul. Perfekt storlek för ungdomar.", cats.get(2)));
            prods.add(new Products("XC 240 Lite 19", 3499, 28, "Lätt cykel för juniorer, lättare än vanliga 24-tumscyklar.", cats.get(2)));
            prods.add(new Products("HYB TERU B2", 27990, 17, "Elektrisk terrängcykel med kraftig Bosch-motor och låg vikt.", cats.get(3)));
            prods.add(new Products("XC-E Trainer 275 19 EU", 14990, 32, "Mycket välutrustad elcykel, perfekt för turer, fritid, pendling och terräng.", cats.get(3)));
            prods.add(new Products("6 Fat Pro 19", 11990, 16, "Sjätte generationens fatbike från White.", cats.get(4)));
            prods.add(new Products("6 Fat Lite NX", 9999, 12, "Mycket bra bärförmåga på de breda däcken.", cats.get(4)));
            
            for (int i = 0; i < prods.size(); i++) {
                productsFacade.create(prods.get(i));
            }
            
            Customer cust = new Customer("Mikael", "Fredriksson", "0737777777", "Toppartorp 123", "19333", "Stockholm", "Sweden");
            Customer cust2 = new Customer("Göran", "Petterson", "0739231233", "Odengatan 23", "19233", "Stockholm", "Sweden", 550000);
            
            Orders order1 = new Orders(true, cust2);
            Orders order2 = new Orders(true, cust2);
            
            OrderDetails det1 = new OrderDetails(prods.get(0), 4, order1);
            OrderDetails det2 = new OrderDetails(prods.get(1), 2, order1);
            OrderDetails det3 = new OrderDetails(prods.get(2), 5, order1);
            OrderDetails det4 = new OrderDetails(prods.get(3), 4, order1);
            OrderDetails det5 = new OrderDetails(prods.get(4), 2, order1);
            OrderDetails det6 = new OrderDetails(prods.get(5), 5, order1);
            OrderDetails det7 = new OrderDetails(prods.get(6), 4, order2);
            OrderDetails det8 = new OrderDetails(prods.get(7), 2, order2);
            OrderDetails det9 = new OrderDetails(prods.get(8), 5, order2);
            OrderDetails det10 = new OrderDetails(prods.get(9), 4, order2);
            OrderDetails det11 = new OrderDetails(prods.get(10), 2, order2);
            OrderDetails det12 = new OrderDetails(prods.get(11), 5, order2);

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

            Account acc = new Account("224Mikael@gmail.com", "Regularpass123", "regular", cust);
            accountFacade.register(acc);
            
            Account acc2 = new Account("admin@admin.com", "Adminpass123", "admin");
            accountFacade.register(acc2);
            
            Account acc3 = new Account("premium@premium.com", "Premiumpass123", "premium", cust2);
            accountFacade.register(acc3);
            
            //KOLLA PÅ DET HÄR
        }
    }
}
