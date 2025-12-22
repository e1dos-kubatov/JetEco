package comsep_23.JetEco.entity;

public class CartItem {
    private String name;
    private int price;

    public CartItem(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public int getPrice() { return price; }
}

