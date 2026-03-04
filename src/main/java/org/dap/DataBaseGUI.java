package org.dap;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataBaseGUI {

    private static final int WIDTH_RES = 898;
    private static final int HEIGHT_RES = 509;

    private static Object selectItemId;
    private static Object selectItemName;
    private static Object selectItemDesc;
    private static Object selectItemPrice;
    private static Object selectItemStock;

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("DataAccessProject");
    private static EntityManager em = emf.createEntityManager();

    private static DefaultTableModel model;

    private static final String[] columnas = {"ID","Nombre","Descripción","Precio €","Stock"};

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

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        scrollPane.setOpaque(false);

        panel.add(scrollPane,BorderLayout.CENTER);
        // ------------------------------------

        // BOTTOM PANEL
        JPanel bottomPanel = new JPanel();

        JButton addButton = createButton("Añadir",new Color(143, 170, 71));
        addButton.addActionListener(e -> addButtonAction());

        JButton modButton = createButton("Modificar",new Color(211, 166, 54));
        modButton.addActionListener(e -> modifyButtonAction(table));

        JButton delButton = createButton("Eliminar",new Color(195, 60, 56));
        delButton.addActionListener(e -> deleteButtonAction(table));

        bottomPanel.add(addButton);
        bottomPanel.add(modButton);
        bottomPanel.add(delButton);

        panel.add(bottomPanel,BorderLayout.SOUTH);
        // ------------------------------------
        updateTable();

        frame.add(panel);
        frame.setVisible(true);
    }

    private static JTextField getSearchbar() {
        JTextField searchbar = new JTextField();
        searchbar.setPreferredSize(new Dimension(400,20));


        searchbar.getDocument().addDocumentListener(new DocumentListener() {

            private void update() {
//                if (!searchbar.getText().isEmpty()) {
//                    List<Producto> filter =  productos.stream()
//                            .filter(p -> p.getName().toLowerCase().contains(searchbar.getText().toLowerCase())).toList();
//
//                    updateTable(filter);
//                } else {
//                    updateTable(productos);
//                }
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

    private static void updateTable() {
        List<Producto> productos = em.createQuery("FROM Producto", Producto.class).getResultList();
        model.setRowCount(0);
        for (Producto p : productos) {
            model.addRow(new Object[]{p.getId(),p.getName(),p.getDescription(),p.getPrice(),p.getStock()});
        }
    }

    private static void addButtonAction() {
        DialogWindow addDialog = new DialogWindow(new JFrame());
        addDialog.setVisible(true);

        if (addDialog.isConfirm()) {
            Producto p = new Producto(addDialog.getName(),
                    addDialog.getDesc(),
                    addDialog.getPrice(),
                    addDialog.getStock());
            //productos.add(p);
            updateTable();
        }
    }

    private static void modifyButtonAction(JTable table) {
        try {
            DialogWindow addDialog = new DialogWindow(new JFrame(),
                    selectItemName.toString(),
                    selectItemDesc.toString(),
                    selectItemPrice.toString(),
                    selectItemStock.toString());
            addDialog.setVisible(true);

            if (addDialog.isConfirm()) {

                em.getTransaction().begin();
                Producto p = em.find(Producto.class, selectItemId);
                if (p != null) {
                   p.setName(addDialog.getName());
                   p.setDescription(addDialog.getDesc());
                   p.setPrice(addDialog.getPrice());
                   p.setStock(addDialog.getStock());
                }
                em.getTransaction().commit();
                updateTable();
                JOptionPane.showMessageDialog(null, "El producto se ha modificado.","Modificación exitosa",JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "Seleccione un elemento de la tabla, por favor.","Error al seleccionar",JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void deleteButtonAction(JTable table) {
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
                //productos.remove(table.getSelectedRow());
                updateTable();
                JOptionPane.showMessageDialog(null, "El producto se ha eliminado.","Borrado exitoso",JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "Seleccione un elemento de la tabla, por favor.","Error al seleccionar",JOptionPane.ERROR_MESSAGE);
        }
    }
}
