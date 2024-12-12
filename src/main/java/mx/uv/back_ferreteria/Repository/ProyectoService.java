package mx.uv.back_ferreteria.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uv.back_ferreteria.Modelo.Direccion;
import mx.uv.back_ferreteria.Modelo.Persona;
import mx.uv.back_ferreteria.Modelo.Proyecto;

@Service
public class ProyectoService {
    @Autowired
    private ProyectoRepository proyectoRepository;
    
    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private DireccionRepository direccionRepository;

    public boolean agregarProyecto(Proyecto proyecto){

        Persona persona = proyecto.getPersona(); // Obtener la dirección de proyecto
        if (personaRepository.existsByNombreAndCorreoAndRfc(persona.getNombre(), persona.getCorreo(), persona.getRfc())) {
            personaRepository.save(persona); // Guardar la dirección en la tabla PERSONA
        }

        Direccion direccion = proyecto.getDireccion(); // Obtener la dirección de proyecto
        direccionRepository.save(direccion); // Guardar la dirección en la tabla DIRECCION
        
        proyecto.setPersona(persona);
        proyecto.setDireccion(direccion);
        proyecto.setEstado("Disponible");
        proyectoRepository.save(proyecto);
        return true;
    }

    public List<Proyecto> obtenerProyectos() {
        return proyectoRepository.findAll();
    }

    // Obtener proyecto por id
    public Optional<Proyecto> obtenerProyectoById(String idProyecto) {
        return proyectoRepository.findById(idProyecto);
    }

    // Eliminar un proyecto
    public boolean eliminarProyecto(String idProyecto) {
        Optional<Proyecto> proyecto = proyectoRepository.findById(idProyecto);
        if (proyecto.isPresent()) {
            Proyecto proyectoExistente = proyecto.get();
            proyectoExistente.setEstado("Inactivo");
            proyectoRepository.save(proyectoExistente);
            return true;
        }
        return false;
    }

}
