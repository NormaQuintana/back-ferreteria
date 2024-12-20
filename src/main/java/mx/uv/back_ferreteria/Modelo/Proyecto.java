package mx.uv.back_ferreteria.Modelo;

import java.time.LocalDate;
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
    
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "idPersona")
    private Persona persona;

@OneToOne(cascade = CascadeType.ALL) 
@JoinColumn(name = "idDireccion")
    private Direccion direccion;

    private LocalDate fecha;
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
    
        public LocalDate getFecha() {
            return fecha;
        }
    
        public void setFecha(LocalDate fecha) {
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
