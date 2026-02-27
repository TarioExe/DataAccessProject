package org.dap;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class DataBaseGUI {

    private static final int WIDTH_RES = 898;
    private static final int HEIGHT_RES = 509;

    private static Object selectItemId;
    private static Object selectItemName;
    private static Object selectItemDesc;
    private static Object selectItemPrice;
    private static Object selectItemStock;

    private static DefaultTableModel model;

    private static final String[] columnas = {"ID","Nombre","Descripción","Precio €","Stock"};

    private static ArrayList<Producto> productos = new ArrayList<>();

    static {
        for (int i = 0; i < 5; i++) {
            Producto p = new Producto("Producto " + i, "Descr. " + i, 10+i, 150+i);
            productos.add(p);
        }
    }


    public static void main(String[] args) {
        new DataBaseGUI();
    }

    public DataBaseGUI() {

        JFrame frame = new JFrame("Almacén");
        frame.setSize(WIDTH_RES,HEIGHT_RES);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        JPanel panel = new JPanel(new BorderLayout());

        // TOP PANEL
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel busquedaLabel = new JLabel("🔎");
        topPanel.add(busquedaLabel);

        JTextField searchbar = getSearchbar();

        topPanel.add(searchbar);

        panel.add(topPanel,BorderLayout.NORTH);
        // ------------------------------------

        // MIDDLE PANEL (TABLE)
        JTable table = getJTable();

        // EXAMPLE DATA !!!!!!!!!!!!!!!!!!
        for (Producto p: productos) {
            model.addRow(new Object[]{p.getId(),p.getNombre(),p.getDescripcion(),p.getPrecio(),p.getStock()});
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        scrollPane.setOpaque(false);

        panel.add(scrollPane,BorderLayout.CENTER);
        // ------------------------------------

        // BOTTOM PANEL
        JPanel bottomPanel = new JPanel();

        JButton addButton = createButton("Añadir",new Color(143, 170, 71));
        addButton.addActionListener(e -> {

            boolean error = false;
            JPanel addPanel = AcceptPanel.createPanel();
            int result = JOptionPane.showConfirmDialog(
            null,
            addPanel,
            "Nuevo producto",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                String nombre = AcceptPanel.getName();
                String descri = AcceptPanel.getDesc();
                double precio = 0;
                try {
                   precio = Double.parseDouble(AcceptPanel.getPrice());
                } catch (NumberFormatException ex) {
                    error = true;
                }

                int stk = 0;
                try {
                    stk = Integer.parseInt(AcceptPanel.getStock());
                } catch (NumberFormatException ex) {
                    error = true;
                }

                if (!error) {
                    Producto p = new Producto(nombre,descri,precio,stk);
                    productos.add(p);
                    updateTable(productos);
                } else {
                    JOptionPane.showMessageDialog(null, "Los datos no coinciden\ncon los valores de la tabla","Error al insertar",JOptionPane.ERROR_MESSAGE);

                }
            }

        });

        JButton modButton = createButton("Modificar",new Color(211, 166, 54));
        modButton.addActionListener(e -> {

            try {
                JPanel modPanel = ModifyPanel.createPanel(selectItemName.toString(),
                        selectItemDesc.toString(),
                        selectItemPrice.toString(),
                        selectItemStock.toString());

                int result = JOptionPane.showConfirmDialog(
                        null,
                        modPanel,
                        "Modificar producto",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

                if (result == JOptionPane.OK_OPTION) {
                    String nombre = ModifyPanel.getName();
                    String descri = ModifyPanel.getDesc();
                    String precio = ModifyPanel.getPrice();
                    String stk = ModifyPanel.getStock();

                    System.out.println("Nombre: " + nombre);
                    System.out.println("Desc.: " + descri);
                    System.out.println("Precio: " + precio);
                    System.out.println("Stock: " + stk);
                }

            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(null, "Seleccione un elemento de la tabla, por favor.","Error al seleccionar",JOptionPane.ERROR_MESSAGE);
            }

        });

        JButton delButton = createButton("Eliminar",new Color(195, 60, 56));
        delButton.addActionListener(e -> {

            try {
                JPanel delPanel = DeletePanel.createPanel(selectItemName.toString(),
                        selectItemDesc.toString(),
                        selectItemPrice.toString(),
                        selectItemStock.toString());


                Object[] message = {"¿Está seguro de querer eliminar el siguiente producto?:",delPanel};
                int result = JOptionPane.showConfirmDialog(
                        null,
                        message,
                        "Eliminar producto",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (result == JOptionPane.OK_OPTION) {
                    String id = selectItemName.toString();
                    System.out.println("Elemento a eliminar: ID " + id);
                    JOptionPane.showMessageDialog(null, "El producto se ha eliminado.","Borrado exitoso",JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(null, "Seleccione un elemento de la tabla, por favor.","Error al seleccionar",JOptionPane.ERROR_MESSAGE);
            }
        });

        bottomPanel.add(addButton);
        bottomPanel.add(modButton);
        bottomPanel.add(delButton);

        panel.add(bottomPanel,BorderLayout.SOUTH);
        // ------------------------------------

        frame.add(panel);
        frame.setVisible(true);
    }

    private static JTextField getSearchbar() {
        JTextField searchbar = new JTextField();
        searchbar.setPreferredSize(new Dimension(400,20));


        searchbar.getDocument().addDocumentListener(new DocumentListener() {

            private void update() {
                if (!searchbar.getText().isEmpty()) {
                    ArrayList<Producto> filter = (ArrayList<Producto>) productos.stream()
                            .filter(p -> p.getNombre().toLowerCase().contains(searchbar.getText().toLowerCase()))
                            .collect(Collectors.toList());

                    updateTable(filter);
                } else {
                    updateTable(productos);
                }
            }

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
        });
        return searchbar;
    }

    private static JTable getJTable() {
        model = new DefaultTableModel(columnas,0);
        JTable table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(800,200));
        table.setFillsViewportHeight(true);
        table.setBackground(new Color(249,246,241));
        table.setFont(new Font("Arial",Font.PLAIN,12));

        // Header options
        table.getTableHeader().setBackground(new Color(85, 84, 81));
        table.getTableHeader().setForeground(new Color(249,246,241));
        table.getTableHeader().setFont(new Font("Arial",Font.BOLD,16));
        table.getTableHeader().setReorderingAllowed(false);

        // Selection listener
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    selectItemId = table.getValueAt(selectedRow,0);
                    selectItemName = table.getValueAt(selectedRow,1);
                    selectItemDesc = table.getValueAt(selectedRow,2);
                    selectItemPrice = table.getValueAt(selectedRow,3);
                    selectItemStock = table.getValueAt(selectedRow,4);
                }
            }
        });

        return table;
    }

    private static JButton createButton(String buttonName, Color color) {
        JButton button = new JButton(buttonName);
        button.setBackground(color);
        button.setFont(new Font("Arial",Font.PLAIN,18));

        return button;
    }

    private static void updateTable(ArrayList<Producto> producto) {
        model.setRowCount(0);
        for (Producto p : producto) {
            model.addRow(new Object[]{p.getId(),p.getNombre(),p.getDescripcion(),p.getPrecio(),p.getStock()});

        }
    }
}
