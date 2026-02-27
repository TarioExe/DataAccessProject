package org.dap;

import javax.swing.*;

public class AcceptPanel {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Añadir producto");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        JPanel panel = new JPanel();

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    private static JTextField nameField;
    private static JTextArea descField;
    private static JTextField priceField;
    private static JTextField stockField;

    public static String getName() {
        return nameField.getText();
    }

    public static String getDesc() {
        return descField.getText();
    }

    public static String getPrice() {
        return priceField.getText();
    }

    public static String getStock() {
        return stockField.getText();
    }

    public static JPanel createPanel() {
        SpringLayout layout = new SpringLayout();
        JPanel panel = new JPanel(layout);

        JLabel nameLabel = new JLabel("Nombre: ");
        panel.add(nameLabel);

        nameField = new JTextField(20);
        panel.add(nameField);

        JLabel descLabel = new JLabel("Descripción: ");
        panel.add(descLabel);

        descField = new JTextArea(5,20);
        JScrollPane descScroll = new JScrollPane(descField);
        panel.add(descScroll);

        JLabel priceLabel = new JLabel("Precio €: ");
        panel.add(priceLabel);

        priceField = new JTextField(10);
        panel.add(priceField);

        JLabel stockLabel = new JLabel("Stock: ");
        panel.add(stockLabel);

        stockField = new JTextField(10);
        panel.add(stockField);

        // NAME
        layout.putConstraint(SpringLayout.WEST, nameLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, nameLabel, 10, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, nameField, 35, SpringLayout.EAST, nameLabel);
        layout.putConstraint(SpringLayout.NORTH, nameField, 0, SpringLayout.NORTH, nameLabel);

        // DESC
        layout.putConstraint(SpringLayout.WEST, descLabel, 0, SpringLayout.WEST, nameLabel);
        layout.putConstraint(SpringLayout.NORTH, descLabel, 20, SpringLayout.SOUTH, nameLabel);

        layout.putConstraint(SpringLayout.WEST, descScroll, 0, SpringLayout.WEST, nameField);
        layout.putConstraint(SpringLayout.NORTH, descScroll, 0, SpringLayout.NORTH, descLabel);

        // PRICE
        layout.putConstraint(SpringLayout.WEST, priceLabel, 0, SpringLayout.WEST, nameLabel);
        layout.putConstraint(SpringLayout.NORTH, priceLabel, 20, SpringLayout.SOUTH, descScroll);

        layout.putConstraint(SpringLayout.WEST, priceField, 0, SpringLayout.WEST, nameField);
        layout.putConstraint(SpringLayout.NORTH, priceField, 0, SpringLayout.NORTH, priceLabel);

        // STOCK
        layout.putConstraint(SpringLayout.WEST, stockLabel, 0, SpringLayout.WEST, nameLabel);
        layout.putConstraint(SpringLayout.NORTH, stockLabel, 20, SpringLayout.SOUTH, priceLabel);

        layout.putConstraint(SpringLayout.WEST, stockField, 0, SpringLayout.WEST, nameField);
        layout.putConstraint(SpringLayout.NORTH, stockField, 0, SpringLayout.NORTH, stockLabel);

        // BOTTOM MARGIN
        layout.putConstraint(SpringLayout.EAST, panel, 20, SpringLayout.EAST, descScroll);
        layout.putConstraint(SpringLayout.SOUTH, panel, 20, SpringLayout.SOUTH, stockField);

        return panel;
    }

}
