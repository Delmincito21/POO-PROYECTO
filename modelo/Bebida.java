package modelo;
public class Bebida extends Pedido{
     private boolean tieneAlcohol;

    public Bebida(String idPedido, int numMesa, String descripcion, double precioBase, boolean tieneAlcohol){
        super(idPedido,numMesa,descripcion,precioBase);
        this.tieneAlcohol = tieneAlcohol;
    }

    public boolean isTieneAlcohol() { return tieneAlcohol;}
    public void setTieneAlcohol(boolean tieneAlcohol) {this.tieneAlcohol = tieneAlcohol;}
    @Override
    
    public double calcularTotal() {
        
        double itbis = getPrecioBase()* 0.18; //Cambiar itbis cualquier vaina
        double tip = getPrecioBase() * 0.10; // lo mimo de arriba
        double itbisAlcohol; 
        if(tieneAlcohol)
        {
            itbisAlcohol= getPrecioBase() * 0.10;
        }
        else 
        {
            itbisAlcohol = 0;
        }
       return getPrecioBase() + itbis + tip + itbisAlcohol;
    }
}