package mx.uv.back_ferreteria.Modelo;

import java.sql.Date;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Proyecto {
@Id
    private String idProyecto = UUID.randomUUID().toString();
    
@OneToOne(cascade = CascadeType.ALL) // Esto propagará las operaciones de persistencia a Dirección
@JoinColumn(name = "idPersona") // Enlace con Persona
    private Persona persona;

@OneToOne(cascade = CascadeType.ALL) // Esto propagará las operaciones de persistencia a Dirección
@JoinColumn(name = "idDireccion")
    private Direccion direccion;

    private Date fecha;
    private String descripcion;
    private String estado;
    
        public Proyecto() {
        }
    
        public String getIdProyecto() {
            return idProyecto;
        }
    
        public void setIdProyecto(String idProyecto) {
            this.idProyecto = idProyecto;
        }
    
        public Persona getPersona() {
            return persona;
        }
    
        public void setPersona(Persona persona) {
            this.persona = persona;
        }
    
        public Direccion getDireccion() {
            return direccion;
        }
    
        public void setDireccion(Direccion direccion) {
            this.direccion = direccion;
        }
    
        public Date getFecha() {
            return fecha;
        }
    
        public void setFecha(Date fecha) {
            this.fecha = fecha;
        }
    
        public String getDescripcion() {
            return descripcion;
        }
    
        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }
    
        
        public String getEstado() {
            return estado;
        }
    
        public void setEstado(String estado) {
            this.estado = estado;
        }
}
