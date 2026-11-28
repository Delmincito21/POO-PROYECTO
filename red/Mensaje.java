package red;

import modelo.Pedido;
import java.io.Serializable;

public class Mensaje implements Serializable{
    public enum tipoMensaje {NUEVO_PEDIDO,PEDIDO_LISTO,PEDIR_CUENTA, CUENTA, PAGAR,ERROR, CIERRE_CAJA, INFO};
    private tipoMensaje tipo;
    private Pedido pedido;
    private int numMesa;
    private String descripcion;
    private double monto;
   
    

    public Mensaje(tipoMensaje tipo, Pedido pedido, int numMesa, String descripcion) {
        this.tipo = tipo;
        this.pedido = pedido;
        this.numMesa = numMesa;
        this.descripcion = descripcion;
        this.monto = 0;
    }

    public tipoMensaje getTipo() {return tipo;}
    public Pedido getPedido() {return pedido;}
    public int getNumMesa() {return numMesa;}
    public String getDescripcion() {return descripcion;}
    public double getMonto(){return monto;}
    
}
