package ui;

import javax.swing.*;
import java.awt.*;

import data.AppData;

class ProfileFrame extends JFrame {

    JTextField usernameField;
    JPasswordField passwordField;
    JTextField balanceField;

    ProfileFrame() {
        setTitle("User Profile");
        setSize(400,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        JLabel header = new JLabel("Edit Profile", JLabel.CENTER);
        header.setOpaque(true);
        header.setBackground(Color.CYAN);
        add(header, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(3,2,10,10));
        form.setBorder(BorderFactory.createEmptyBorder(20,30,20,30));

        usernameField = new JTextField(AppData.currentUser.username);
        passwordField = new JPasswordField(AppData.currentUser.password);
        balanceField  = new JTextField(
                String.valueOf(AppData.currentUser.balance)
        );

        form.add(new JLabel("Username"));
        form.add(usernameField);
        form.add(new JLabel("Password"));
        form.add(passwordField);
        form.add(new JLabel("Balance"));
        form.add(balanceField);

        add(form, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JButton saveBtn = new JButton("Save");
        JButton backBtn = new JButton("Back");
        bottom.add(saveBtn);
        bottom.add(backBtn);
        add(bottom, BorderLayout.SOUTH);

        saveBtn.addActionListener(e -> {
            try {
                AppData.currentUser.username =
                        usernameField.getText();
                AppData.currentUser.password =
                        new String(passwordField.getPassword());
                AppData.currentUser.balance =
                        Double.parseDouble(balanceField.getText());

                AppData.saveAllData();
                JOptionPane.showMessageDialog(
                        this,"Profile updated successfully!"
                );
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,"Invalid balance value!"
                );
            }
        });

        backBtn.addActionListener(e -> {
            new ShopFrame().setVisible(true);
            this.dispose();
        });
    }
}

