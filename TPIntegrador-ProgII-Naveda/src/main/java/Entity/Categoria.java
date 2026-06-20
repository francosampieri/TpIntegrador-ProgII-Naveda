package Entity;

import java.time.LocalDateTime;

public class Categoria extends Entidad {
    private String nombre;
    private String descripcion;
    
    //CONSTRUCTORES

    public Categoria(long id, boolean eliminado, String nombre, String descripcion, LocalDateTime createdAt) {
        super(id, eliminado, createdAt);
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Categoria(long id, String nombre, String descripcion) {
        super(id);
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    
    //GETTERS SETTERS

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
