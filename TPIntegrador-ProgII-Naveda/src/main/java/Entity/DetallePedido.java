package Entity;
public class DetallePedido {
    private Producto producto;
    private int cantidad;
    private double total;

    //CONSTRUCTORES
    
    public DetallePedido() {}
    
    public DetallePedido(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }
    
    //GETTERS SETTERS

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
       
    
}
