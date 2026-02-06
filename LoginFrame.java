package ui;

import javax.swing.*;
import java.awt.*;

import data.AppData;
import model.*;

public class LoginFrame extends JFrame {
    JTextField usernameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JCheckBox sellerCheck = new JCheckBox("Login as Seller");

    public LoginFrame() {
        setTitle("Shopping Mall - Login");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel main = new JPanel(new BorderLayout(10,10));

        JLabel header = new JLabel("Enter username and password", JLabel.CENTER);
        header.setBackground(Color.GREEN);
        header.setOpaque(true);
        main.add(header, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(3,2,10,10));
        center.setBorder(BorderFactory.createEmptyBorder(30,40,30,40));

        center.add(new JLabel("Username")); center.add(usernameField);
        center.add(new JLabel("Password")); center.add(passwordField);
        center.add(new JLabel("")); center.add(sellerCheck);

        main.add(center, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton signUp = new JButton("Sign up");
        JButton signIn = new JButton("Sign in");
        buttons.add(signUp); buttons.add(signIn);

        main.add(buttons, BorderLayout.SOUTH);
        add(main);

        // ================= Action Listeners =================
        signIn.addActionListener(e -> {
            String u = usernameField.getText();
            String p = new String(passwordField.getPassword());
            boolean wantSeller = sellerCheck.isSelected();

            for(User user : AppData.users){
                if(user.username.equals(u) && user.password.equals(p)){
                    if(wantSeller && !user.role.equals("seller")){
                        JOptionPane.showMessageDialog(this,"You are not a seller!");
                        return;
                    }
                    AppData.currentUser = user;
                    if(user.role.equals("seller"))
                        new AddProductFrame().setVisible(true);
                    else
                        new ShopFrame().setVisible(true);
                        this.dispose();
                        return;

                }
            }
            JOptionPane.showMessageDialog(this,"Invalid username/password");
        });

        signUp.addActionListener(e -> {
            String u=usernameField.getText();
            String p=new String(passwordField.getPassword());
            String role = sellerCheck.isSelected() ? "seller":"client";
            User newUser = new User(u,p,role,500);
            AppData.users.add(newUser);
            AppData.saveAllData(); // ذخیره پس از ثبت نام
            JOptionPane.showMessageDialog(this,"Sign up successful! Role: "+role);
        });
    }
}
