package mx.uv.back_ferreteria.Servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uv.back_ferreteria.Modelo.Direccion;
import mx.uv.back_ferreteria.Modelo.Persona;
import mx.uv.back_ferreteria.Modelo.Proyecto;
import mx.uv.back_ferreteria.Repository.DireccionRepository;
import mx.uv.back_ferreteria.Repository.PersonaRepository;
import mx.uv.back_ferreteria.Repository.ProyectoRepository;

@Service
public class ProyectoService {
    @Autowired
    private ProyectoRepository proyectoRepository;
    
    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private DireccionRepository direccionRepository;

    public boolean agregarProyecto(Proyecto proyecto){

        Persona persona = proyecto.getPersona(); 
        if (personaRepository.existsByNombreAndCorreoAndRfc(persona.getNombre(), persona.getCorreo(), persona.getRfc())) {
            personaRepository.save(persona); 
        }

        Direccion direccion = proyecto.getDireccion(); 
        direccionRepository.save(direccion); 
        
        proyecto.setPersona(persona);
        proyecto.setDireccion(direccion);
        proyecto.setEstado("Disponible");
        proyectoRepository.save(proyecto);
        return true;
    }

    public List<Proyecto> obtenerProyectos() {
        return proyectoRepository.findAll();
    }

    public Optional<Proyecto> obtenerProyectoById(String idProyecto) {
        return proyectoRepository.findById(idProyecto);
    }

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
