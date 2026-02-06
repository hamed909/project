package service;

import model.Product;
import java.util.ArrayList;

public class ProductCatalog {
    private ArrayList<Product> products = new ArrayList<>();
    private int nextId = 1;

public Product addProduct(String name, String category, double price, int stock, String description, String imagePath) {
    Product p = new Product(nextId++, name, category, price, stock, description, imagePath);
    products.add(p);
    return p;
}


    public ArrayList<Product> getAllProducts(){
        return products;
    }

    public Product findProduct(int id){
        for(Product p: products){
            if(p.getId() == id) return p;
        }
        return null;
    }

    public void setNextId(int next){
        nextId = next;
    }
}
