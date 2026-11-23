package pantallas;

import java.awt.*;
import javax.swing.*;


public class PantallaBase extends JFrame {
    private  JPanel panelHeader;
    private  JPanel panelContenido;
    private  JPanel panelFooter;
    private  JLabel lblTitulo;
    private  JLabel lblEstado;

    public PantallaBase(String tituloVentana, String tituloHeader){
        setTitle(tituloVentana);
        setSize(950, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(100,166,22)); // un azulito exotico

        //Header
        panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(new Color(184,209,231));
        
    }
}
