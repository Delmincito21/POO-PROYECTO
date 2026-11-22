package red;

import java.io.*;
import java.net.*;

public class Cliente {
    private Socket socket;
    private ObjectInputStream entrada;
    private ObjectOutputStream salida;
    private String host;
    private int puerto;
    private boolean isConectado;

    public Cliente(String host, int puerto){
        this.host = host;
        this.puerto = puerto;
        this.isConectado = false;
}
    public static void main(String[] args) {   
        final  String HOST ="127.0.0.1";
        final int puerto = 5000;
        
    }
}
