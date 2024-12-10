package mx.uv.back_ferreteria.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.uv.back_ferreteria.Modelo.Direccion;

public interface DireccionRepository extends JpaRepository<Direccion, String>{
    
}
