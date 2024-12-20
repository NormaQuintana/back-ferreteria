package mx.uv.back_ferreteria.Modelo;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Direccion {
    @Id
    private String id = UUID.randomUUID().toString();
    private String calle;
    private String numero;
    private String colonia;
    private String ciudad;

    public Direccion(){

    }

    public Direccion(String id, String ciudad, String colonia, String calle, String numero) {
        this.id=id;
        this.ciudad=ciudad;
        this.colonia=colonia;
        this.calle=calle;
        this.numero=numero;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
