package model;

public class Product {
    private int id;
    private String name;
    private String category;
    private double price;
    private int stock;
    private String description;
    private String imagePath;

    public Product(int id, String name, String category,double price, int stock, String description , String imagePath){
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.imagePath = imagePath;
    }

    public int getId(){ return id; }
    public String getName(){ return name; }
    public String getCategory(){ return category; }
    public double getPrice(){ return price; }
    public int getStock(){ return stock; }
    public String getDescription(){ return description; }
    public String getImagePath() { return imagePath;  }


    public boolean decreaseStock(int q){
        if(stock - q >= 0){
            stock -= q;
            return true;
        }
        return false;
    }
}
