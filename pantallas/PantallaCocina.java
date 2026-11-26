package pantallas;

import javax.swing.*;
import java.awt.*;
import modelo.*;
import red.Mensaje;

public class PantallaCocina extends PantallaBase {
    private DefaultListModel<Pedido> modeloPedidos;
    private JList<Pedido> listaPedidos;
    private JButton btnMarcarListo;
    private JLabel lblEstado;

    public PantallaCocina() {
        super("Pantalla Cocina");
        setLayout(new BorderLayout());



        setVisible(true);
    }

    @Override
    protected void procesarMensaje(Mensaje m) {
        if (m.getTipo() == Mensaje.tipoMensaje.NUEVO_PEDIDO) { //terminar eta vaina pD: voy a jugar un ratillo
        }
    }
    public static void main(String[] args) {
       SwingUtilities.invokeLater(() -> new PantallaCocina());
    }
}