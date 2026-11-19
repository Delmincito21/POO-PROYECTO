package modelo;
public class PlatoComida extends Pedido{
    private boolean tieneBebida;

    
    public PlatoComida(String idPedido, int numMesa, String descripcion, double precioBase, boolean tieneBebida){
        super(idPedido,numMesa,descripcion,precioBase);
        this.tieneBebida = tieneBebida;
    }
    
    public Boolean getTieneBebida() {return tieneBebida;}
    public void setTieneBebida(Boolean tieneBebida) {this.tieneBebida = tieneBebida;}
    
    @Override  
    public double calcularTotal() {

       double itbis = getPrecioBase()* 0.18; //Cambiar itbis cualquier vaina
       double tip = getPrecioBase() * 0.10; // lo mimo de arriba
       return getPrecioBase() + itbis + tip;
    }
}
