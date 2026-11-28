package pantallas;

import javax.swing.*;
import java.awt.*;
import modelo.*;
import red.*;

public class PantallaBar extends PantallaBase {
    private DefaultListModel<Pedido> modeloBebidas;
    private JList<Pedido> listaBebidas;
    private JButton btnMarcarListo;
    private JLabel lblEstado;

    public PantallaBar() {
        super("PANTALLA BAR");
        modeloBebidas = new DefaultListModel<>();
        listaBebidas = new JList<>(modeloBebidas);
        listaBebidas.setFixedCellHeight(60);

        btnMarcarListo = new JButton("Marcar bebida como lista");
        lblEstado = new JLabel("Esperando bebidas...");
        
        setLayout(new BorderLayout());
        add(new JLabel("BEBIDAS PENDIENTES", SwingConstants.CENTER), BorderLayout.NORTH);
        add(new JScrollPane(listaBebidas), BorderLayout.CENTER);

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
        Pedido bebida = listaBebidas.getSelectedValue();
        if (bebida == null) {
            lblEstado.setText("Selecciona una bebida primero");
            return;
        }
        cliente.enviarMsgServidor(new Mensaje(Mensaje.tipoMensaje.PEDIDO_LISTO,bebida,bebida.getNumMesa(),"Bebida lista: " + bebida.getDescripcion()));
        lblEstado.setText("Bebida de mesa: " + bebida.getNumMesa() + " marcada como lista");
    }

    @Override
    protected void procesarMensaje(Mensaje m) {
        if (m.getTipo() == Mensaje.tipoMensaje.NUEVO_PEDIDO) {
            Pedido p = m.getPedido();
            if (p instanceof Bebida) { 
                modeloBebidas.addElement(p);
                lblEstado.setText("Nueva bebida —> Mesa " + p.getNumMesa());
            }
        } else if (m.getTipo() == Mensaje.tipoMensaje.PEDIDO_LISTO) {
            Pedido p = m.getPedido();
            if (p instanceof Bebida) {
                for (int i = 0; i < modeloBebidas.size(); i++) {
                    if (modeloBebidas.get(i).getIdPedido().equals(p.getIdPedido())) {
                        modeloBebidas.remove(i);
                        break;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
       SwingUtilities.invokeLater(() -> new PantallaBar());
    }
}