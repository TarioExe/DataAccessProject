package org.dap;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class DialogWindow extends JDialog {

    private JTextField nameField;
    private String name;
    private JTextArea descField;
    private String desc;
    private JTextField priceField;
    private double price;
    private JTextField stockField;
    private int stock;
    private JButton accept;
    private boolean confirm = false;

    public DialogWindow(JFrame parent) {

        super(parent, "Nuevo producto", true);

        SpringLayout layout = new SpringLayout();
        JPanel panel = new JPanel(layout);
        setContentPane(panel);

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

        accept = new JButton("Aceptar");
        panel.add(accept);
        accept.setEnabled(false);

        JButton cancel = new JButton("Cancelar");
        panel.add(cancel);

        // LISTENER
        DocumentListener updater = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                update();
            }
        };

        nameField.getDocument().addDocumentListener(updater);
        priceField.getDocument().addDocumentListener(updater);
        stockField.getDocument().addDocumentListener(updater);

        accept.addActionListener(e -> {
            getData();
            dispose();
        });

        cancel.addActionListener(e -> dispose());

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

        // BUTTONS
        layout.putConstraint(SpringLayout.WEST, accept, 0, SpringLayout.WEST, nameLabel);
        layout.putConstraint(SpringLayout.NORTH, accept, 30, SpringLayout.SOUTH, stockField);

        layout.putConstraint(SpringLayout.WEST, cancel, 20, SpringLayout.EAST, accept);
        layout.putConstraint(SpringLayout.NORTH, cancel, 0, SpringLayout.NORTH, accept);

        // BOTTOM MARGIN
        layout.putConstraint(SpringLayout.EAST, panel, 20, SpringLayout.EAST, descScroll);
        layout.putConstraint(SpringLayout.SOUTH, panel, 20, SpringLayout.SOUTH, accept);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(parent);
    }

    public DialogWindow(JFrame parent, String name, String desc, String price, String stock) {

        super(parent, "Modificar producto", true);

        SpringLayout layout = new SpringLayout();
        JPanel panel = new JPanel(layout);
        setContentPane(panel);

        JLabel nameLabel = new JLabel("Nombre: ");
        panel.add(nameLabel);

        nameField = new JTextField(20);
        nameField.setText(name);
        panel.add(nameField);

        JLabel descLabel = new JLabel("Descripción: ");
        panel.add(descLabel);

        descField = new JTextArea(5,20);
        JScrollPane descScroll = new JScrollPane(descField);
        descField.setText(desc);
        panel.add(descScroll);

        JLabel priceLabel = new JLabel("Precio €: ");
        panel.add(priceLabel);

        priceField = new JTextField(10);
        priceField.setText(price);
        panel.add(priceField);

        JLabel stockLabel = new JLabel("Stock: ");
        panel.add(stockLabel);

        stockField = new JTextField(10);
        stockField.setText(stock);
        panel.add(stockField);

        accept = new JButton("Aceptar");
        panel.add(accept);
        accept.setEnabled(false);

        JButton cancel = new JButton("Cancelar");
        panel.add(cancel);

        // LISTENER
        DocumentListener updater = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                update();
            }
        };

        nameField.getDocument().addDocumentListener(updater);
        priceField.getDocument().addDocumentListener(updater);
        stockField.getDocument().addDocumentListener(updater);

        accept.addActionListener(e -> {
            getData();
            dispose();
        });

        cancel.addActionListener(e -> dispose());

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

        // BUTTONS
        layout.putConstraint(SpringLayout.WEST, accept, 0, SpringLayout.WEST, nameLabel);
        layout.putConstraint(SpringLayout.NORTH, accept, 30, SpringLayout.SOUTH, stockField);

        layout.putConstraint(SpringLayout.WEST, cancel, 20, SpringLayout.EAST, accept);
        layout.putConstraint(SpringLayout.NORTH, cancel, 0, SpringLayout.NORTH, accept);

        // BOTTOM MARGIN
        layout.putConstraint(SpringLayout.EAST, panel, 20, SpringLayout.EAST, descScroll);
        layout.putConstraint(SpringLayout.SOUTH, panel, 20, SpringLayout.SOUTH, accept);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(parent);
    }

    private void update() {

        boolean nombreOk = !nameField.getText().trim().isEmpty();
        boolean precioOk = priceField.getText().matches("^\\d+(\\.\\d+)?$");
        boolean stockOk = stockField.getText().matches("^\\d+$");

        priceField.setBorder(precioOk ? UIManager.getBorder("TextField.border")
                : BorderFactory.createLineBorder(Color.RED));

        stockField.setBorder(stockOk ? UIManager.getBorder("TextField.border")
                : BorderFactory.createLineBorder(Color.RED));

        accept.setEnabled(nombreOk && precioOk && stockOk);
    }

    private void getData() {
        name = nameField.getText();
        desc = descField.getText();
        price = Double.parseDouble(priceField.getText());
        stock = Integer.parseInt(stockField.getText());
        confirm = true;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }
}
