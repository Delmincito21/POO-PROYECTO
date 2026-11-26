
package pantallas;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import modelo.*;
import red.Mensaje;

public class PantallaCamarero extends PantallaBase {

    private JComboBox<Integer> comboMesa;
    private JComboBox<String> comboTipo;
    private JTextField txtDescripcion;
    private JTextField txtPrecio;
    private JTextField txtCantidad;
    private JTextArea areaPedidos;
    private ArrayList<Pedido> listaPedidos;
    private int contador = 1;
    private JCheckBox checkTieneBebida;
    private JCheckBox checkTieneAlcohol;
    private JLabel lblOpciones;
    private JLabel lblAlcohol;

    public PantallaCamarero() {

        super("PANTALLA CAMARERO");
        listaPedidos = new ArrayList<>();
        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel(new GridLayout(2, 2));
        comboMesa = new JComboBox<>();
        for ( int i = 1; i <= 10; i++) { //cambiar la cantidad de mesa por restaurante
            comboMesa.addItem(i);
        }
        comboTipo = new JComboBox<>(new String[]{"Comida", "Bebida"});

        panelSuperior.add(new JLabel("Seleccionar mesa:"));
        panelSuperior.add(comboMesa);
        panelSuperior.add(new JLabel("Tipo de producto:"));
        panelSuperior.add(comboTipo);

        JPanel panelProducto = new JPanel(new GridLayout(0, 2));

        txtDescripcion = new JTextField();
        txtPrecio = new JTextField();
        txtCantidad = new JTextField();
        
        checkTieneBebida = new JCheckBox("Incluye bebida");
        checkTieneAlcohol = new JCheckBox("Tiene alcohol");

        lblOpciones = new JLabel("Opciones:");
        checkTieneBebida = new JCheckBox("Incluye bebida");
        lblAlcohol = new JLabel();
        checkTieneAlcohol = new JCheckBox("Tiene alcohol");

        panelProducto.add(lblOpciones);
        panelProducto.add(checkTieneBebida);
        panelProducto.add(lblAlcohol);
        panelProducto.add(checkTieneAlcohol);
        panelProducto.add(new JLabel("Producto:"));
        panelProducto.add(txtDescripcion);
        panelProducto.add(new JLabel("Precio:"));
        panelProducto.add(txtPrecio);
        panelProducto.add(new JLabel("Cantidad:"));
        panelProducto.add(txtCantidad);

        JButton btnAgregar = new JButton("Agregar producto");
        panelProducto.add(btnAgregar);

        areaPedidos = new JTextArea();
        areaPedidos.setEditable(false);

        JButton btnEnviar = new JButton("ENVIAR PEDIDO");

        JPanel panelCentral = new JPanel(new BorderLayout());

        panelCentral.add(panelProducto, BorderLayout.NORTH);
        panelCentral.add(new JScrollPane(areaPedidos), BorderLayout.CENTER);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(btnEnviar, BorderLayout.SOUTH);

        // AGREGAR PRODUCTO A LA LISTA
        btnAgregar.addActionListener(e -> agregarProducto());

        // ENVIAR TODOS LOS PRODUCTOS
        btnEnviar.addActionListener(e -> enviarPedidos());
        comboTipo.addActionListener(e -> actualizarCheckboxes());
        checkTieneBebida.addActionListener(e -> actualizarCheckboxes());
        actualizarCheckboxes();
        setVisible(true);
    }

    private void agregarProducto() {

        try {

            String descripcion = txtDescripcion.getText();
            double precio = Double.parseDouble(txtPrecio.getText());
            int cantidad = Integer.parseInt(txtCantidad.getText());

            if (descripcion.isEmpty() || precio <= 0 || cantidad <= 0) {

                JOptionPane.showMessageDialog(this,
                    "Introduce datos válidos.");
                return;
            }
            int mesa = (int) comboMesa.getSelectedItem();
            String tipo = (String) comboTipo.getSelectedItem();
            for (int i = 0; i < cantidad; i++) {
                Pedido pedido;
                String id = "PEDIDO-" + contador++;
                boolean tieneBebida = checkTieneBebida.isSelected();
                boolean tieneAlcohol = checkTieneAlcohol.isSelected();
                if (tipo.equals("Comida")) {
                    pedido = new PlatoComida(id,mesa,descripcion,precio,tieneBebida);
                } else {
                    pedido = new Bebida(id,mesa,descripcion,precio,tieneAlcohol);
                }
                listaPedidos.add(pedido);
            }
            areaPedidos.append(cantidad + " x " + descripcion + " | Mesa: " + mesa + " | Tipo: " + tipo + "\n");
            txtDescripcion.setText("");
            txtPrecio.setText("");
            txtCantidad.setText("1");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,"El precio y la cantidad deben ser números.");
        }
    }
    private void enviarPedidos() {

        if (listaPedidos.isEmpty()) {

            JOptionPane.showMessageDialog(this,"No hay productos para enviar.");
            return;
        }

        for (Pedido pedido : listaPedidos) {

            Mensaje mensaje = new Mensaje(Mensaje.tipoMensaje.NUEVO_PEDIDO,pedido,pedido.getNumMesa(),"Nuevo pedido: " + pedido.getDescripcion());
            cliente.enviarMsgServidor(mensaje);
        }

        JOptionPane.showMessageDialog(this,"Pedidos enviados correctamente.");
        listaPedidos.clear();
        areaPedidos.setText("");
    }
    @Override
    protected void procesarMensaje(Mensaje m) {

        if (m.getTipo() == Mensaje.tipoMensaje.INFO) {
            areaPedidos.append("SERVIDOR: " + m.getDescripcion() + "\n");
        }
    }
    private void actualizarCheckboxes() {
        String tipo = (String) comboTipo.getSelectedItem();
        boolean esComida = "Comida".equals(tipo);

        lblOpciones.setVisible(esComida);
        checkTieneBebida.setVisible(esComida);
        if (!esComida) checkTieneBebida.setSelected(false);

        boolean esAlcohol = !esComida || checkTieneBebida.isSelected();
        lblAlcohol.setVisible(esAlcohol);
        checkTieneAlcohol.setVisible(esAlcohol);
        if (!esAlcohol) checkTieneAlcohol.setSelected(false);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
            new PantallaCamarero()
        );
    }
}
