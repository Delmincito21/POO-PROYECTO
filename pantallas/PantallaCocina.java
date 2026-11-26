package pantallas;

import javax.swing.*;
import java.awt.*;
import modelo.*;
import red.Mensaje;

public class PantallaCocina extends PantallaBase {
    private JTextArea areaPedidos;
    private Pedido pedidoActual;

    public PantallaCocina() {
        super("Pantalla Cocina (Chef)");
        setLayout(new BorderLayout());

        areaPedidos = new JTextArea();
        areaPedidos.setEditable(false);
        
        JButton btnListo = new JButton("Marcar Pedido como LISTO");

        add(new JScrollPane(areaPedidos), BorderLayout.CENTER);
        add(btnListo, BorderLayout.SOUTH);

        btnListo.addActionListener(e -> {
            if (pedidoActual != null) {
                pedidoActual.setEstadoPedido(Pedido.EstadoPedido.LISTO);
                cliente.enviarMsgServidor(new Mensaje(Mensaje.tipoMensaje.PEDIDO_LISTO, pedidoActual, pedidoActual.getNumMesa(), "Pedido Listo"));
                areaPedidos.append("--> Pedido enviado a mesa " + pedidoActual.getNumMesa() + " como LISTO.\n");
                pedidoActual = null;
            } else {
                JOptionPane.showMessageDialog(this, "No hay pedidos pendientes.");
            }
        });

        setVisible(true);
    }

    @Override
    protected void procesarMensaje(Mensaje m) {
        if (m.getTipo() == Mensaje.tipoMensaje.NUEVO_PEDIDO) {
            Pedido p = m.getPedido();
            if (p instanceof PlatoComida) {
                pedidoActual = p;
                areaPedidos.append("MESA " + p.getNumMesa() + " | " + p.getDescripcion() + " | Precio: " + p.getPrecioBase() + "\n");
            }
        }
    }
    public static void main(String[] args) {
       SwingUtilities.invokeLater(() -> new PantallaCocina());
    }
}