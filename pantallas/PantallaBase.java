package pantallas;

import javax.swing.*;
import red.*;


public abstract class PantallaBase extends JFrame {
    protected Cliente cliente;

    public PantallaBase(String titulo) {
        setTitle(titulo);
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        cliente = new Cliente("127.0.0.1", 8080);
        cliente.conectar();

      Thread hilo =  new Thread(() -> {
            while (true) {
                try {
                    Mensaje msg = cliente.recibirMsgServidor();
                    if (msg != null) {
                        SwingUtilities.invokeLater(() -> procesarMensaje(msg)); //pa que se actualize la pantalla
                    }
                } catch (Exception e) {
                    System.out.println("Error recibiendo mensajes: "+ e.getMessage());
                    return;
                }
            }
        });
        hilo.setDaemon(true);
        hilo.start();
    }

    protected abstract void procesarMensaje(Mensaje m);
}