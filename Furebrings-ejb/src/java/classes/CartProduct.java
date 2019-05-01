
package classes;

public class CartProduct {
    private String name;
    private float cost;
    private int quantity;
    private float totaltPrice;

    public CartProduct(String name, float cost, int quantity) {
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
        this.totaltPrice = cost*quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.totaltPrice = cost*quantity;
    }

    public float getTotaltPrice() {
        return totaltPrice;
    }

    public void setTotaltPrice(float totaltPrice) {
        this.totaltPrice = totaltPrice;
    }
    
    
}
