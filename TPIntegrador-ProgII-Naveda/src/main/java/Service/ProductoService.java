package Service;

import DAO.ProductoDAO;
import Entity.Producto;
import java.sql.SQLException;
import java.util.List;

public class ProductoService implements EntidadService<Producto> {
    private final ProductoDAO productoDAO;
    private final CategoriaService categoriaService;
    
    public ProductoService(ProductoDAO productoDAO, CategoriaService categoriaService) {
        if (productoDAO != null) {
            this.productoDAO = productoDAO;
        } else throw new IllegalArgumentException("El productoDAO pasado a ProductoService no puede ser null");
    
        if (categoriaService != null) {
            this.categoriaService = categoriaService;
        } else throw new IllegalArgumentException("El categoriaService pasado a ProductoService no puede ser null");
    }
    
    //METODOS
    
    public List<Producto> listarByCategoria(Long id_categoria) throws SQLException {
        validarId(id_categoria);
        
        return productoDAO.selectByCategoria(id_categoria);        
    }
    
    public Long crear(String nombre, String descripcion, double precio, int stock, String imagen, boolean disponible, long id_categoria) throws SQLException {
        validarNombre(nombre);
        validarPrecio(precio);
        validarStock(stock);
        validarId(id_categoria);
               
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setImagen(imagen);
        producto.setDisponible(disponible);
        producto.setCategoria(categoriaService.listarById(id_categoria));
                
        return productoDAO.insert(producto); 
    }
    
    public void editarPrecio(long id, double precio) throws SQLException {
        validarId(id);
        validarPrecio(precio);
        
        productoDAO.updatePrecio(id, precio);        
    }
    
    public void editarStock(long id, int stock) throws SQLException {
        validarId(id);
        validarStock(stock);
        
        productoDAO.updateStock(id, stock);        
    }
    
    public void editarCategoria(long id, long id_categoria) throws SQLException {
        validarId(id);
        validarId(id_categoria);
              
        productoDAO.updateIdCategoria(id, id_categoria);        
    }

    @Override
    public List<Producto> listarAll() throws SQLException{
        return productoDAO.selectAll();
    }

    @Override
    public Producto listarById(Long id) throws SQLException {
        validarId(id);
        
        Producto producto = productoDAO.selectById(id);
        
        if(producto == null) throw new IllegalArgumentException("No existe ningun producto con ese Id");
        
        return producto;
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        validarId(id);
        
        productoDAO.eliminar(id);        
    }
    
    
    private void validarId(Long id) {
        if (id == null) throw new IllegalArgumentException("El id no puede ser nulo");
        if (id <= 0) throw new IllegalArgumentException("El id no puede ser negativo");        
    }
    
    private void validarNombre(String nombre) throws SQLException {
        if(nombre == null || nombre.isBlank()) { throw new IllegalArgumentException("El nombre del producto no puede estar vacio");}
    }
    
    private void validarPrecio(double precio) {
        if (precio < 0) { throw new IllegalArgumentException("El precio no puede ser negativo");}
    }
    
    private void validarStock(int stock) {
        if (stock < 0) { throw new IllegalArgumentException("El stock no puede ser negativo");}    
    }
    
}
