package servicios;

import modelo.*;
import modelo.Pedido.EstadoPedido;
import red.*;

public class ProcesarPedido implements  Runnable{
    private Pedido pedido;
    private Servidor servidor;
    
    public ProcesarPedido(Pedido pedido, Servidor servidor) {
        this.pedido = pedido;
        this.servidor = servidor;

    }
    @Override 
    public void run(){
        System.out.println("Procesando pedido: " + pedido.getIdPedido());
        pedido.setEstadoPedido(EstadoPedido.PROCESO);

        try{
            Thread.sleep(4000); //cambia esta vaina con el tiempo
        }catch(InterruptedException e){
            pedido.setEstadoPedido(EstadoPedido.PENDIENTE);
            Thread.currentThread().interrupt();
            return;
        }
        pedido.setEstadoPedido(EstadoPedido.LISTO);
        servidor.difusion(new Mensaje(Mensaje.tipoMensaje.PEDIDO_LISTO, pedido, pedido.getNumMesa(), "Pedido Listo"));
    }
}
