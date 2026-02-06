package ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import data.AppData;

public class AddProductFrame extends JFrame {

    JTextField titleField = new JTextField();
    JTextField priceField = new JTextField();
    JCheckBox availableBox = new JCheckBox();
    JTextField stockField = new JTextField();
    JTextField descriptionField = new JTextField();

    String selectedImagePath = "";

    JLabel imagePreview = new JLabel("No image", JLabel.CENTER);

    public AddProductFrame() {
        setTitle("Add Product");
        setSize(650, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel main = new JPanel(new BorderLayout(10,10));

        JLabel header = new JLabel("Product information", JLabel.CENTER);
        header.setOpaque(true);
        header.setBackground(Color.GREEN);
        header.setPreferredSize(new Dimension(100,40));
        main.add(header, BorderLayout.NORTH);

        // ================= Form =================
        JPanel form = new JPanel(new GridLayout(6,2,10,10));
        form.setBorder(BorderFactory.createEmptyBorder(20,30,20,30));

        form.add(new JLabel("Title"));
        form.add(titleField);

        form.add(new JLabel("Price"));
        form.add(priceField);

        form.add(new JLabel("Available"));
        form.add(availableBox);

        form.add(new JLabel("Stock"));
        form.add(stockField);

        form.add(new JLabel("Description"));
        form.add(descriptionField);

        // ================= Image chooser =================
        JPanel imagePanel = new JPanel(new BorderLayout(5,5));
        JButton imageBtn = new JButton("Choose Image");
        imagePreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        imagePreview.setPreferredSize(new Dimension(120,120));
        imagePanel.add(imagePreview, BorderLayout.CENTER);
        imagePanel.add(imageBtn, BorderLayout.SOUTH);
        form.add(new JLabel("Image"));
        form.add(imagePanel);


        main.add(form, BorderLayout.CENTER);

        // ================= Bottom buttons =================
        JPanel bottom = new JPanel();
        JButton backBtn = new JButton("Back");
        JButton addBtn = new JButton("Add");
        
        bottom.add(backBtn);
        bottom.add(addBtn);

        main.add(bottom, BorderLayout.SOUTH);
        add(main);

        // ================= Image chooser logic =================
        imageBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(
                    new javax.swing.filechooser.FileNameExtensionFilter(
                            "Images", "jpg", "jpeg", "png"
                    )
            );

            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File src = chooser.getSelectedFile();

                try {
                    File imgDir = new File("images");
                    if (!imgDir.exists())
                        imgDir.mkdir();

                    File dest = new File(imgDir, src.getName());
                    Files.copy(src.toPath(), dest.toPath(),
                            StandardCopyOption.REPLACE_EXISTING);

                    selectedImagePath = dest.getPath();

                    ImageIcon icon = new ImageIcon(selectedImagePath);
                    Image img = icon.getImage().getScaledInstance(
                            100, 100, Image.SCALE_SMOOTH
                    );
                    imagePreview.setText("");
                    imagePreview.setIcon(new ImageIcon(img));

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                            "Error while copying image");
                }
            }
        });

        // ================= Add product =================
        addBtn.addActionListener(e -> {
            if (AppData.currentUser == null ||
                !AppData.currentUser.role.equals("seller")) {

                JOptionPane.showMessageDialog(this,
                        "Only sellers can add products!");
                return;
            }

            try {
                String name = titleField.getText();
                double price = Double.parseDouble(priceField.getText());
                int stock = Integer.parseInt(stockField.getText());
                String desc = descriptionField.getText();

                AppData.catalog.addProduct(
                        name,
                        "General",
                        price,
                        stock,
                        desc,
                        selectedImagePath
                );

                AppData.saveAllData();
                JOptionPane.showMessageDialog(this,
                        "Product added successfully!");

                // reset form
                titleField.setText("");
                priceField.setText("");
                stockField.setText("");
                descriptionField.setText("");
                availableBox.setSelected(false);
                selectedImagePath = "";
                imagePreview.setIcon(null);
                imagePreview.setText("No image");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Invalid input data!");
            }
        });

        backBtn.addActionListener(e -> {
             new LoginFrame().setVisible(true);
             this.dispose();
        });
    }
}
