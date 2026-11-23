package pantallas;
import red.*;
import modelo.*;
import java.awt.*;
import javax.swing.*;

public class PantallaCocina extends JFrame{
    public PantallaCocina(){
        iniciarPantalla();
    }

    public void iniciarPantalla(){
        setTitle("COCINA");
        setSize(950, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(100,166,227));


        
        
        setVisible(true);
    }


}
