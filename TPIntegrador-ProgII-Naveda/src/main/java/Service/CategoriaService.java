package Service;

import DAO.CategoriaDAO;
import Entity.Categoria;
import java.sql.SQLException;
import java.util.List;

public class CategoriaService implements EntidadService<Categoria> {
    private final CategoriaDAO categoriaDAO;
    
    public CategoriaService(CategoriaDAO categoriaDAO) {
        if (categoriaDAO != null) {
            this.categoriaDAO = categoriaDAO;
        } else throw new IllegalArgumentException("El categoriaDAO pasado a CategoriaService no puede ser null");
    }
    
    //METODOS
    
    public Long crear(String nombre, String descripcion) throws SQLException {
        validarNombre(nombre);
               
        Categoria cat = new Categoria();
        cat.setNombre(nombre);
        cat.setDescripcion(descripcion);
        
        return categoriaDAO.insert(cat); 
    }
    
    public void editarNombre(long id, String nombre) throws SQLException {
        validarId(id);
        validarNombre(nombre);
        
        categoriaDAO.updateNombre(id, nombre);        
    }
    
    public void editarDescripcion(long id, String descripcion) throws SQLException {
        validarId(id);
        
        categoriaDAO.updateDescripcion(id, descripcion);        
    }

    @Override
    public List<Categoria> listarAll() throws SQLException{
        return categoriaDAO.selectAll();
    }

    @Override
    public Categoria listarById(Long id) throws SQLException {
        validarId(id);
        
        Categoria cat = categoriaDAO.selectById(id);
        
        if(cat == null) throw new IllegalArgumentException("No existe ninguna categoria con ese Id");
        
        return cat;
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        validarId(id);
        
        categoriaDAO.eliminar(id);        
    }
    
    
    private void validarId(Long id) {
        if (id == null) throw new IllegalArgumentException("El id no puede ser nulo");
        if (id <= 0) throw new IllegalArgumentException("El id no puede ser negativo");        
    }
    
    private void validarNombre(String nombre) throws SQLException {
        if(nombre == null || nombre.isBlank()) { throw new IllegalArgumentException("El nombre de la categoria no puede estar vacio");}
        if(categoriaDAO.selectByNombre(nombre) != null) {
            throw new IllegalArgumentException("Ya existe una categoria con ese nombre");
        }
    }
}
