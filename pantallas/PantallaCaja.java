
package pantallas;

import javax.swing.*;
import java.awt.*;

import red.Mensaje;

public class PantallaCaja extends PantallaBase {
    private JComboBox<Integer> comboMesa;
    private JTextArea areaCuenta;
    private JLabel lblEstado;
    private JLabel lblTotal;

    private JButton btnSolicitar;
    private JButton btnPagar;
    private JButton btnCierre;

    private int mesaSeleccionada = -1;
    private boolean cuentaSolicitada = false;

    public PantallaCaja() {
        super("PANTALLA CAJA");
        setLayout(new BorderLayout(10, 10));
        // PANEL SUPERIOR
        JPanel panelSuperior = new JPanel(new FlowLayout());
        comboMesa = new JComboBox<>();
        for (int i = 1; i <= 10; i++) {
            comboMesa.addItem(i);
        }

        btnSolicitar = new JButton("Solicitar cuenta");
        panelSuperior.add(new JLabel("Seleccionar mesa:"));
        panelSuperior.add(comboMesa);
        panelSuperior.add(btnSolicitar);

        // AREA DE CUENTA
        areaCuenta = new JTextArea();
        areaCuenta.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaCuenta);
        // PANEL INFERIOR
        JPanel panelInferior = new JPanel(new BorderLayout());
        lblEstado = new JLabel("Esperando operación.");
        lblTotal = new JLabel("Total:");

        JPanel panelInfo = new JPanel(new GridLayout(2, 1));

        panelInfo.add(lblEstado);
        panelInfo.add(lblTotal);
        JPanel panelBotones = new JPanel(new FlowLayout());

        btnPagar = new JButton("Procesar pago");
        btnCierre = new JButton("Generar cierre de caja");

        btnPagar.setEnabled(false);

        panelBotones.add(btnPagar);
        panelBotones.add(btnCierre);
        panelInferior.add(panelInfo, BorderLayout.NORTH);
        panelInferior.add(panelBotones, BorderLayout.SOUTH);

        // AGREGAR COMPONENTES
        add(panelSuperior, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        btnSolicitar.addActionListener(e -> solicitarCuenta());
        btnPagar.addActionListener(e -> procesarPago());
        btnCierre.addActionListener(e -> generarCierre());
        setSize(600, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // SOLICITAR CUENTA AL SERVIDOR
    private void solicitarCuenta() {

        int mesa = (int) comboMesa.getSelectedItem();
        mesaSeleccionada = mesa;
        cuentaSolicitada = false;
        btnPagar.setEnabled(false);
        areaCuenta.setText("Solicitando cuenta de la mesa: " + mesa + ".\n");

        Mensaje mensaje = new Mensaje(Mensaje.tipoMensaje.PEDIR_CUENTA,null,mesa,"Solicitar cuenta");
        cliente.enviarMsgServidor(mensaje);
    }
    private void procesarPago() {
        if (!cuentaSolicitada || mesaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,"Primero debes solicitar una cuenta.");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,"¿Desea procesar el pago de la mesa: " + mesaSeleccionada + "?","Confirmar pago",JOptionPane.YES_NO_OPTION);

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        Mensaje mensaje = new Mensaje(Mensaje.tipoMensaje.PAGAR,null,mesaSeleccionada,"Pago de mesa: " + mesaSeleccionada);
        cliente.enviarMsgServidor(mensaje);
        btnPagar.setEnabled(false);
        lblEstado.setText("Procesando pago.");
    }

    // GENERAR CIERRE DE CAJA
    private void generarCierre() {
        int confirmacion = JOptionPane.showConfirmDialog(this,"¿Desea generar el cierre de caja?","Cierre de caja",JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        Mensaje mensaje = new Mensaje(Mensaje.tipoMensaje.CIERRE_CAJA,null,0,"Generar cierre de caja");
        cliente.enviarMsgServidor(mensaje);
        lblEstado.setText("Generando cierre de caja.");
    }

    // RECIBIR MENSAJES DEL SERVIDOR
    @Override
    protected void procesarMensaje(Mensaje m) {

        switch (m.getTipo()) {
            case PEDIR_CUENTA:
                if (m.getNumMesa() == mesaSeleccionada) {
                    areaCuenta.setText("=== CUENTA ===\n\n" + "Mesa: " + m.getNumMesa() + " " +m.getDescripcion());
                    lblTotal.setText(m.getDescripcion());

                    cuentaSolicitada = true;
                    btnPagar.setEnabled(true);
                    lblEstado.setText("Cuenta recibida correctamente.");
                }
                break;
            case PAGAR:
                if (m.getNumMesa() == mesaSeleccionada) {

                    areaCuenta.append("\nPAGO CONFIRMADO, Mesa: " + m.getNumMesa() +" liberada.");
                    lblEstado.setText("Pago realizado correctamente.");
                    cuentaSolicitada = false;
                    mesaSeleccionada = -1;
                    btnPagar.setEnabled(false);
                    JOptionPane.showMessageDialog(this,"Pago realizado correctamente.");
                }
                break;
            case INFO:

                if (m.getDescripcion() != null &&
                    m.getDescripcion().contains("Cierre de caja")) {
                    lblEstado.setText(m.getDescripcion());
                    JOptionPane.showMessageDialog(this,m.getDescripcion());
                }
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new PantallaCaja());

    }
}
