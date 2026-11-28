
package pantallas;

import javax.swing.*;
import java.awt.*;
import modelo.*;
import red.*;

public class PantallaCocina extends PantallaBase {

    private DefaultListModel<Pedido> modeloPedidos;
    private JList<Pedido> listaPedidos;
    private JButton btnMarcarListo;

    private JLabel lblEstado;

    public PantallaCocina() {
        super("Pantalla Cocina");
        modeloPedidos = new DefaultListModel<>();
        listaPedidos = new JList<>(modeloPedidos);

        btnMarcarListo = new JButton("Marcar como listo");
        lblEstado = new JLabel("Esperando pedidos...");

        setLayout(new BorderLayout());

        add(new JLabel("PEDIDOS PENDIENTES", SwingConstants.CENTER), BorderLayout.NORTH);
        add(new JScrollPane(listaPedidos), BorderLayout.CENTER);

        JPanel panelAbajo = new JPanel(new BorderLayout());

        panelAbajo.add(lblEstado, BorderLayout.CENTER);
        panelAbajo.add(btnMarcarListo, BorderLayout.EAST);

        add(panelAbajo, BorderLayout.SOUTH);

        btnMarcarListo.addActionListener(e -> marcarListo());

        setSize(500, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void marcarListo() {
        Pedido pedido = listaPedidos.getSelectedValue();
        if (pedido == null) {
            lblEstado.setText("Seleccione un pedido primero");
            return;
        }
        Mensaje mensaje = new Mensaje(Mensaje.tipoMensaje.PEDIDO_LISTO, pedido,pedido.getNumMesa(),pedido.getDescripcion());
        cliente.enviarMsgServidor(mensaje);
        modeloPedidos.removeElement(pedido);
        lblEstado.setText("Pedido marcado como listo");
    }

    @Override
    protected void procesarMensaje(Mensaje m) {

        if (m.getTipo() == Mensaje.tipoMensaje.NUEVO_PEDIDO) {
            Pedido pedido = (Pedido) m.getPedido();
            if(pedido instanceof PlatoComida){
                modeloPedidos.addElement(pedido);
                lblEstado.setText("Nuevo pedido recibido, Mesa: "+ pedido.getNumMesa());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PantallaCocina());
    }
}
