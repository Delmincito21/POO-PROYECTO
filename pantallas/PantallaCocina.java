package pantallas;
import red.*;
import modelo.*;
import java.awt.*;
import javax.swing.*;

public class PantallaCocina extends JFrame{
    private JTextArea areaPedidos;
    private Cliente cliente;

    public PantallaCocina(){
        setTitle("Pantalla de la cocina");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        areaPedidos = new JTextArea();
        areaPedidos.setEditable(false);
        add(new JScrollPane(areaPedidos), BorderLayout.CENTER);
        JButton btnlisto = new JButton("Marcar pedido como listo");
        add(btnlisto,BorderLayout.SOUTH);

        //Conexion a la red
        cliente = new Cliente("127.0.0.1", 8080);
        //terminar cliente preguntar al profe.

        
    }

}
