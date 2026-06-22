package Service;

import DAO.UsuarioDAO;
import Entity.Usuario;
import java.sql.SQLException;
import java.util.List;

public class UsuarioService implements EntidadService<Usuario> {
    private final UsuarioDAO usuarioDAO;
    
    public UsuarioService(UsuarioDAO usuarioDAO) {
        if (usuarioDAO != null) {
            this.usuarioDAO = usuarioDAO;
        } else throw new IllegalArgumentException("El usuarioDAO pasado a UsuarioService no puede ser null");
    }
    
    //METODOS
    
    public Long crear(String nombre, String apellido, String mail, String celular, boolean eliminado) throws SQLException {
        validarNombre(nombre);
        validarApellido(apellido);
        validarMail(mail);
        validarCelular(celular);
               
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        usuario.setCelular(celular);
        usuario.setEliminado(eliminado);
        
        return usuarioDAO.insert(usuario);
    }
    
    public void editarNombre(long id, String nombre) throws SQLException {
        validarId(id);
        validarNombre(nombre);
        
        usuarioDAO.updateNombre(id, nombre);        
    }
    
    public void editarApellido(long id, String apellido) throws SQLException {
        validarId(id);
        validarApellido(apellido);
        
        usuarioDAO.updateApellido(id, apellido);        
    }
    
    public void editarMail(long id, String mail) throws SQLException {
        validarId(id);
        validarMail(mail);
        
        usuarioDAO.updateMail(id, mail);        
    }
    
    public void editarCelular(long id, String celular) throws SQLException {
        validarId(id);
        validarCelular(celular);
        
        usuarioDAO.updateCelular(id, celular);        
    }

    @Override
    public List<Usuario> listarAll() throws SQLException{
        return usuarioDAO.selectAll();
    }

    @Override
    public Usuario listarById(Long id) throws SQLException {
        validarId(id);
        
        Usuario usuario = usuarioDAO.selectById(id);
        
        if(usuario == null) throw new IllegalArgumentException("No existe ningun usuario con ese Id");
        
        return usuario;
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        validarId(id);
        
        usuarioDAO.eliminar(id);        
    }
    
    
    private void validarId(Long id) {
        if (id == null) throw new IllegalArgumentException("El id no puede ser nulo");
        if (id <= 0) throw new IllegalArgumentException("El id no puede ser negativo");        
    }
    
    private boolean validarString(String st) {
        return(st == null || st.isBlank());
    }      
    
    private void validarNombre(String nombre) throws SQLException {
        if(validarString(nombre)) { throw new IllegalArgumentException("El nombre no puede estar vacio");}
    }
    
    private void validarApellido(String apellido) throws SQLException {
        if(validarString(apellido)) { throw new IllegalArgumentException("El apellido no puede estar vacio");}
    }
    
    private void validarMail(String mail) throws SQLException {
        if(validarString(mail)) { throw new IllegalArgumentException("El mail no puede estar vacio");}
    }
    
    private void validarCelular(String celular) throws SQLException {
        if(validarString(celular)) { throw new IllegalArgumentException("El celular no puede estar vacio");}
    }
    
    
}
