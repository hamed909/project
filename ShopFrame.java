package ui;

import javax.swing.*;
import java.awt.*;

import data.AppData;
import model.*;

public class ShopFrame extends JFrame {
    ShopFrame() {
        setTitle("Shopping Mall");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel header = new JLabel("Shopping Mall", JLabel.CENTER);
        header.setOpaque(true); header.setBackground(Color.ORANGE);
        add(header, BorderLayout.NORTH);

        JPanel products = new JPanel();
        products.setLayout(new BoxLayout(products, BoxLayout.Y_AXIS));
        refreshProducts(products);
        add(new JScrollPane(products), BorderLayout.CENTER);

        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        right.add(new JLabel("User type: "+AppData.currentUser.role));
        right.add(new JLabel("Balance: "+AppData.currentUser.balance));
        right.add(Box.createVerticalStrut(20));
        JButton basketBtn = new JButton("Basket"); right.add(basketBtn);
        JButton profileBtn = new JButton("Profile");
        right.add(profileBtn);
        
        profileBtn.addActionListener(e -> {
        new ProfileFrame().setVisible(true);
        this.dispose();
        });

        JButton logoutBtn = new JButton("Log out");
        right.add(logoutBtn);
        logoutBtn.addActionListener(e -> {
        int choice = JOptionPane.showConfirmDialog(this,"Are you sure you want to log out?","Logout",JOptionPane.YES_NO_OPTION);

    if(choice == JOptionPane.YES_OPTION){
        AppData.currentUser = null;   
        new LoginFrame().setVisible(true);  
        this.dispose();                     
    }
});


        add(right, BorderLayout.EAST);

        basketBtn.addActionListener(e -> {
            new BasketFrame().setVisible(true);
            this.dispose();///////////
            }
        );
    }

    void refreshProducts(final JPanel panel){
        panel.removeAll();
        for(final Product p : AppData.catalog.getAllProducts()){
            JPanel prodPanel = new JPanel(new BorderLayout());
            prodPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            JPanel info = new JPanel(new GridLayout(4,1));
            info.add(new JLabel("Title: "+p.getName()));
            info.add(new JLabel("Price: "+p.getPrice()));
            info.add(new JLabel("Stock: "+p.getStock()));
            info.add(new JLabel("ID: "+p.getId()));
            JLabel imageLabel = new JLabel();

            imageLabel.setPreferredSize(new Dimension(120,120));
            if (p.getImagePath() != null && !p.getImagePath().isEmpty()) {
            ImageIcon icon = new ImageIcon(p.getImagePath());
            Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));
            } else {
                imageLabel.setText("No Image");
                imageLabel.setHorizontalAlignment(JLabel.CENTER);
            }   

prodPanel.add(imageLabel, BorderLayout.WEST);


            JPanel bottom = new JPanel();
            JButton addBtn = new JButton("Add to basket");
            addBtn.addActionListener(e -> {
                if(AppData.currentUser.role.equals("client")){
                    if(p.getStock()>0){
                        AppData.currentUser.addToBasket(p,1);
                        AppData.saveAllData(); // ذخیره پس از اضافه شدن به سبد
                        JOptionPane.showMessageDialog(null,"Added to basket!");
                    } else {
                        JOptionPane.showMessageDialog(null,"Out of stock!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null,"Seller cannot buy products!");
                }
            });
            bottom.add(addBtn);
            prodPanel.add(info, BorderLayout.CENTER);
            prodPanel.add(bottom, BorderLayout.SOUTH);
            prodPanel.add(imageLabel, BorderLayout.WEST);/////////////
            panel.add(prodPanel);
        }
        panel.revalidate();
        panel.repaint();
    }
}
