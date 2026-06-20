package Entity;
public class DetallePedido implements Calculable {
    private Producto producto;
    private int cantidad;
    private double total;

    //CONSTRUCTORES
    
    public DetallePedido() {}
    
    public DetallePedido(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        actualizarTotal();
    }
    
    //GETTERS SETTERS

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        actualizarTotal();
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        actualizarTotal();
    }

    @Override
    public Double calcularTotal() {
        return this.producto.getPrecio() * this.cantidad;
    }  
    
    public void actualizarTotal() {
        this.total = calcularTotal();
    }
}
