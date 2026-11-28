package modelo;
import java.io.Serializable;

public abstract class Pedido implements Serializable{
    public enum EstadoPedido {PENDIENTE, PROCESO, LISTO, ENTREGADO}
    private String idPedido;
    private int numMesa;
    private String descripcion;
    private double precioBase;
    private EstadoPedido estadoPedido;
    
    

    public Pedido(String idPedido, int numMesa, String descripcion, double precioBase) {
        this.idPedido = idPedido;
        this.numMesa = numMesa;
        this.descripcion = descripcion;
        this.precioBase = precioBase;
        this.estadoPedido = EstadoPedido.PENDIENTE;
    }
   

    public String getIdPedido() {return idPedido;}
    public void setIdPedido(String idPedido) {this.idPedido = idPedido;}
    public int getNumMesa() {return numMesa;}
    public void setNumMesa(int numMesa) {this.numMesa = numMesa;}
    public String getDescripcion() {return descripcion;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
    public double getPrecioBase() {return precioBase;}
    public void setPrecioBase(double precioBase) {this.precioBase = precioBase;}
    public EstadoPedido getEstadoPedido() {return estadoPedido;}
    public void setEstadoPedido(EstadoPedido estadoPedido) {this.estadoPedido = estadoPedido;}

    public abstract double calcularTotal();
    @Override
    public String toString() {
    String tipo = (this instanceof PlatoComida) ? "Comida" : "Bebida";
    return "Mesa " + numMesa + " | " + tipo + " | " + descripcion + " | " + precioBase + " pesos.";
}
}
