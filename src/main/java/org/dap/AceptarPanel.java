package org.dap;

import javax.swing.*;
import java.awt.*;

public class AceptarPanel {

    public static JPanel AceptarPanel() {

//        JFrame frame = new JFrame("Añadir producto");
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.setResizable(false);

        SpringLayout layout = new SpringLayout();
        JPanel panel = new JPanel(layout);

        JLabel nameLabel = new JLabel("Nombre: ");
        panel.add(nameLabel);

        JTextField name = new JTextField(20);
        panel.add(name);

        JLabel descLabel = new JLabel("Descripción: ");
        panel.add(descLabel);

        JTextArea description = new JTextArea(5,20);
        JScrollPane descScroll = new JScrollPane(description);
        panel.add(descScroll);

        JLabel priceLabel = new JLabel("Precio €: ");
        panel.add(priceLabel);

        JTextField price = new JTextField(10);
        panel.add(price);

        JLabel stockLabel = new JLabel("Stock: ");
        panel.add(stockLabel);

        JTextField stock = new JTextField(10);
        panel.add(stock);

        // NAME
        layout.putConstraint(SpringLayout.WEST, nameLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, nameLabel, 10, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, name, 35, SpringLayout.EAST, nameLabel);
        layout.putConstraint(SpringLayout.NORTH, name, 0, SpringLayout.NORTH, nameLabel);

        // DESC
        layout.putConstraint(SpringLayout.WEST, descLabel, 0, SpringLayout.WEST, nameLabel);
        layout.putConstraint(SpringLayout.NORTH, descLabel, 20, SpringLayout.SOUTH, nameLabel);

        layout.putConstraint(SpringLayout.WEST, descScroll, 0, SpringLayout.WEST, name);
        layout.putConstraint(SpringLayout.NORTH, descScroll, 0, SpringLayout.NORTH, descLabel);

        // PRICE
        layout.putConstraint(SpringLayout.WEST, priceLabel, 0, SpringLayout.WEST, nameLabel);
        layout.putConstraint(SpringLayout.NORTH, priceLabel, 20, SpringLayout.SOUTH, descScroll);

        layout.putConstraint(SpringLayout.WEST, price, 0, SpringLayout.WEST, name);
        layout.putConstraint(SpringLayout.NORTH, price, 0, SpringLayout.NORTH, priceLabel);

        // STOCK
        layout.putConstraint(SpringLayout.WEST, stockLabel, 0, SpringLayout.WEST, nameLabel);
        layout.putConstraint(SpringLayout.NORTH, stockLabel, 20, SpringLayout.SOUTH, priceLabel);

        layout.putConstraint(SpringLayout.WEST, stock, 0, SpringLayout.WEST, name);
        layout.putConstraint(SpringLayout.NORTH, stock, 0, SpringLayout.NORTH, stockLabel);

        // BOTTOM MARGIN
        layout.putConstraint(SpringLayout.EAST, panel, 20, SpringLayout.EAST, descScroll);
        layout.putConstraint(SpringLayout.SOUTH, panel, 20, SpringLayout.SOUTH, stock);

//        frame.add(panel);
//        frame.pack();
//        frame.setLocationRelativeTo(null);

        return panel;
    }

}
