package mx.uv.back_ferreteria.Repository;



import org.springframework.data.jpa.repository.JpaRepository;

import mx.uv.back_ferreteria.Modelo.Persona;


public interface PersonaRepository extends JpaRepository<Persona, String> {
    boolean existsByNombreAndCorreoAndRfc(String nombre, String correo, String rfc);   
}