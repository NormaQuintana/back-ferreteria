package mx.uv.back_ferreteria.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mx.uv.back_ferreteria.Modelo.Persona;
import mx.uv.back_ferreteria.Modelo.Producto;

public interface ProductoRepository extends JpaRepository<Producto, String>{
    boolean existsByNombreAndPersona (String nombre, Persona persona);

    @Query("SELECT p FROM Producto p WHERE p.estado = 'Disponible'")
    List<Producto> findProductosDisponibles();
}
