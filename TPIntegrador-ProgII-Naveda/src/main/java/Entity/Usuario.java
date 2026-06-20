package Entity;

import java.time.LocalDateTime;

public class Usuario extends Entidad {
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    
    //CONSTRUCTORES

    public Usuario(long id, boolean eliminado, LocalDateTime createdAt, String nombre, String apellido, String mail, String celular) {
        super(id, eliminado, createdAt);
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.celular = celular;
    }

    public Usuario(long id, String nombre, String apellido, String mail, String celular) {
        super(id);
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.celular = celular;
    }
    
    //GETTERS SETTERS

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
}
