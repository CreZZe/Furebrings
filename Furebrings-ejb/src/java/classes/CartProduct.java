
package classes;

public class CartProduct {
    private String name;
    private float cost;
    private int quantity;
    private float totaltProductPrice;

    public CartProduct(String name, float cost, int quantity) {
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
        this.totaltProductPrice = cost*quantity;
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
        this.totaltProductPrice = cost*quantity;
    }

    public float getTotaltProductPrice() {
        return totaltProductPrice;
    }

    public void setTotaltProductPrice(float totaltProductPrice) {
        this.totaltProductPrice = totaltProductPrice;
    }   
}
