package mx.uv.back_ferreteria.Repository;



import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.uv.back_ferreteria.Modelo.Persona;
import mx.uv.back_ferreteria.Modelo.Rol;


public interface PersonaRepository extends JpaRepository<Persona, String> {
    boolean existsByNombreAndCorreoAndRfc(String nombre, String correo, String rfc);   

    List<Persona> findByRol(Rol rol);

    Optional<Persona> findById(String idPersona);
}