package servicios;

import modelo.*;
import modelo.Pedido.EstadoPedido;

public class ProcesarPedido implements  Runnable{
    private Pedido pedido;
    
    public ProcesarPedido(Pedido pedido) {
        this.pedido = pedido;
    }
    @Override 
    public void run(){
        System.out.println("Procesando pedido: " + pedido.getIdPedido());
        pedido.setEstadoPedido(EstadoPedido.PROCESO);
    }
}
