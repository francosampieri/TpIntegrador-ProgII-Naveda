package Entity;

import java.time.LocalDateTime;

public abstract class Entidad {
    private long id;
    private boolean eliminado = false;
    private LocalDateTime createdAt;

    //CONSTRUCTORES
    
    public Entidad(long id, boolean eliminado, LocalDateTime createdAt) {
        this.id = id;
        this.eliminado = eliminado;
        this.createdAt = createdAt;
    }
    
    public Entidad(long id) {
        this.id = id;
        this.eliminado = false;
        this.createdAt = LocalDateTime.now();
    }
    
    //GETTERS SETTERS

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    //METODOS
    
    public void eliminar() {
        this.eliminado = true;
    }
    
}
