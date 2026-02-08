package ui;

import javax.swing.*;
import java.awt.*;

import data.AppData;
import model.*;


public class BasketFrame extends JFrame {
    BasketFrame(){
        setTitle("Basket");
        setSize(800,500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel header = new JLabel("Your basket", JLabel.CENTER);
        header.setOpaque(true); header.setBackground(Color.GREEN);
        add(header, BorderLayout.NORTH);

        JPanel items = new JPanel();
        items.setLayout(new BoxLayout(items, BoxLayout.Y_AXIS));
        refreshItems(items);
        add(new JScrollPane(items), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new GridLayout(3,1));
        bottom.add(new JLabel("Total: "+AppData.currentUser.getCartTotal(), JLabel.CENTER));
        bottom.add(new JLabel("Balance: "+AppData.currentUser.balance, JLabel.CENTER));

        JPanel buttons = new JPanel();
        JButton proceed = new JButton("Proceed");
        buttons.add(proceed);
        JButton goShop = new JButton("Go to shop");
        buttons.add(goShop);

        goShop.addActionListener(e -> {
        new ShopFrame().setVisible(true);
        this.dispose();                 
        });

        bottom.add(buttons);
        add(bottom, BorderLayout.SOUTH);

        proceed.addActionListener(e -> {
            double total = AppData.currentUser.getCartTotal();
            if(AppData.currentUser.balance>=total){
                AppData.currentUser.balance-=total;
                for(CartItem ci : AppData.currentUser.cart){
                    if(!ci.product.decreaseStock(ci.quantity)){
                        JOptionPane.showMessageDialog(this,"Not enough stock!");
                        return;
                    }
                }
                AppData.currentUser.clearBasket();
                AppData.saveAllData(); // ذخیره پس از خرید
                JOptionPane.showMessageDialog(this,"Payment successful! Total: "+total);
                refreshItems(items);
            } else {
                JOptionPane.showMessageDialog(this,"Insufficient balance!");
            }
        });
    }

    void refreshItems(JPanel items){
        items.removeAll();
        for(final CartItem ci : AppData.currentUser.cart){
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            JPanel info = new JPanel(new GridLayout(3,1));
            JLabel lbl = new JLabel(ci.product.getName()+" | Price: "+ci.product.getPrice()+" | Qty: "+ci.quantity
        +" | Description: "+ci.product.getDescription());
            info.add(lbl);
            itemPanel.add(info, BorderLayout.CENTER);

            JPanel btns = new JPanel();
            JButton plus = new JButton("+");
            JButton minus = new JButton("-");
            btns.add(plus); btns.add(minus);
            itemPanel.add(btns, BorderLayout.SOUTH);

        plus.addActionListener(e -> {
            if (ci.quantity < ci.product.getStock()) {
            ci.quantity++;
            AppData.saveAllData();

            lbl.setText(ci.product.getName()+" | Price: "+ci.product.getPrice()+" | Qty: "+ci.quantity
            +" | Description: "+ci.product.getDescription());


    } else {
        JOptionPane.showMessageDialog(
            BasketFrame.this,
            "Not enough stock available!",
            "Stock limit",
            JOptionPane.WARNING_MESSAGE
        );
    }
});
            minus.addActionListener(e -> {
                if(ci.quantity>1){
                    ci.quantity--;
                    AppData.saveAllData(); // ذخیره پس از تغییر تعداد
                    lbl.setText(ci.product.getName()+" | Price: "+ci.product.getPrice()+" | Qty: "+ci.quantity
                +" | Description: "+ci.product.getDescription());
                } else {
                    AppData.currentUser.cart.remove(ci);
                    items.remove(itemPanel);
                    AppData.saveAllData(); // ذخیره پس از حذف آیتم
                    items.revalidate();
                    items.repaint();
                }
            });


            items.add(itemPanel);
        }
        items.revalidate();
        items.repaint();
    }
}
