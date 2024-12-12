package mx.uv.back_ferreteria.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import mx.uv.back_ferreteria.Modelo.Proyecto;

public interface ProyectoRepository extends JpaRepository<Proyecto, String> {
    Proyecto findByIdProyecto(String idProyecto);  
}
