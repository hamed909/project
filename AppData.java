package data;

import model.*;
import service.ProductCatalog;
import java.io.*;
import java.util.*;

public class AppData {
    public static ProductCatalog catalog = new ProductCatalog();
    public static ArrayList<User> users = new ArrayList<>();
    public static User currentUser;

    private static final String PRODUCTS_FILE = "products.csv";
    private static final String USERS_FILE = "users.csv";

    public static void loadAllData() {
         // بارگذاری محصولات
        File prodFile = new File(PRODUCTS_FILE);
        if(prodFile.exists()){
            try(Scanner sc = new Scanner(prodFile)){
                int maxId = 0;
                while(sc.hasNextLine()){
                    String[] parts = sc.nextLine().split(";");
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String category = parts[2];
                    double price = Double.parseDouble(parts[3]);
                    int stock = Integer.parseInt(parts[4]);
                    String desc = parts[5];
                    String imagePath = parts[6];
                    Product p = new Product(id,name,category,price,stock,desc,imagePath);
                    catalog.getAllProducts().add(p);
                    if(id>maxId) maxId=id;
                }
                catalog.setNextId(maxId+1);
            } catch(Exception e){ e.printStackTrace(); }
        }

        // بارگذاری کاربران و سبد خرید
        File userFile = new File(USERS_FILE);
        if(userFile.exists()){
            try(Scanner sc = new Scanner(userFile)){
                User current = null;
                while(sc.hasNextLine()){
                    String line = sc.nextLine();
                    String[] parts = line.split(";");
                    if(parts[0].equals("CART") && current!=null){
                        int pid = Integer.parseInt(parts[1]);
                        int qty = Integer.parseInt(parts[2]);
                        Product p = catalog.findProduct(pid);
                        if(p!=null) current.addToBasket(p, qty);
                    } else {
                        current = new User(parts[0],parts[1],parts[2],Double.parseDouble(parts[3]));
                        users.add(current);
                    }
                }
            } catch(Exception e){ e.printStackTrace(); }
        }

        // تنظیم currentUser پیشفرض اگر خالی است
        if(users.size()>0 && currentUser==null){
            currentUser = users.get(0);
        }
    }

    // ================= Save Data =================
    public static void saveAllData(){
        // ذخیره محصولات
        try(PrintWriter out = new PrintWriter(new FileWriter(PRODUCTS_FILE))){
            for(Product p : catalog.getAllProducts()){
                out.println(p.getId()+";"+p.getName()+";"+p.getCategory()+";"+p.getPrice()+";"+p.getStock()+";"+p.getDescription()+";"+p.getImagePath());
            }
        } catch(Exception e){ e.printStackTrace(); }

        // ذخیره کاربران و سبد خرید
        try(PrintWriter out = new PrintWriter(new FileWriter(USERS_FILE))){
            for(User u : users){
                out.println(u.username+";"+u.password+";"+u.role+";"+u.balance);
                for(CartItem ci : u.cart){
                    out.println("CART;"+ci.product.getId()+";"+ci.quantity);
                }
            }
        } catch(Exception e){ e.printStackTrace(); }
    }
}

    

