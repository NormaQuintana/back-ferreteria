package mx.uv.back_ferreteria.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.uv.back_ferreteria.Modelo.Rol;

public interface RolRepository extends JpaRepository<Rol, UUID> {
    Optional<Rol> findById(UUID idRol);
    Optional<Rol> findByNombre(String nombre);
}
