package model;

import java.util.ArrayList;

public class User {
    public String username;
    public String password;
    public String role;
    public double balance;
    public ArrayList<CartItem> cart = new ArrayList<>();

    public User(String username, String password, String role, double balance) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.balance = balance;
    }

    public void addToBasket(Product p, int q) {
        for(CartItem ci: cart){
            if(ci.product.getId() == p.getId()){
                ci.quantity += q;
                return;
            }
        }
        cart.add(new CartItem(p, q));
    }

    public void clearBasket() {
        cart.clear();
    }

    public double getCartTotal(){
        double total = 0;
        for(CartItem ci: cart){
            total += ci.product.getPrice() * ci.quantity;
        }
        return total;
    }
}
