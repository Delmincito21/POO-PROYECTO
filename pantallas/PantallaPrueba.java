
package pantallas;

import javax.swing.*;
import java.awt.*;

import modelo.*;
import red.Mensaje;

public class PantallaPrueba extends PantallaBase {
    private JList<PlatoComida> listavisual;
    private DefaultListModel<PlatoComida> modeloLista;
    private JButton btnlisto;

    public PantallaPrueba(){
        super("Pantalla Cocina");
        setLayout(new BorderLayout());
        modeloLista = new DefaultListModel<>();
        listavisual = new JList<>(modeloLista);





        
        setVisible(true);
    }
       
    
    



    @Override
    protected void procesarMensaje(Mensaje m) {
        
        
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PantallaPrueba());
    }
}
