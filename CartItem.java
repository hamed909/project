package model;

public class CartItem {
    public Product product;
    public int quantity;

    public CartItem(Product p, int q){
        product = p;
        quantity = q;
    }
}
