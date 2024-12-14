package mx.uv.back_ferreteria.Servicio;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uv.back_ferreteria.Modelo.Direccion;
import mx.uv.back_ferreteria.Modelo.Persona;
import mx.uv.back_ferreteria.Modelo.Rol;
import mx.uv.back_ferreteria.Repository.DireccionRepository;
import mx.uv.back_ferreteria.Repository.PersonaRepository;

@Service
public class PersonaService {
     @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private DireccionRepository direccionRepository; 



    public boolean agregarPersona(Persona persona) {

        if (personaRepository.existsByNombreAndCorreoAndRfc(persona.getNombre(), persona.getCorreo(), persona.getRfc())) {
            return false; 
        }

        Rol rol = persona.getRol();
        Direccion direccion = persona.getDireccion(); 
        direccionRepository.save(direccion); 

        persona.setDireccion(direccion);
        persona.setRol(rol);
        persona.setEstado("Disponible");
        personaRepository.save(persona);
        return true;
    }

    public boolean editarPersona(Persona persona) {
        if (!personaRepository.existsById(persona.getId())) {
            return false;
        }
    
        Persona personaExistente = personaRepository.findById(persona.getId()).orElse(null);
    
        if (personaExistente == null) {
            return false;
        }

        personaExistente.setNombre(persona.getNombre());
        personaExistente.setTelefono(persona.getTelefono());
        personaExistente.setCorreo(persona.getCorreo());
        personaExistente.setRfc(persona.getRfc());
        personaExistente.setDireccion(persona.getDireccion());
        personaExistente.setEstado(persona.getEstado() != null ? persona.getEstado() : "Disponible"); 
    
        personaRepository.save(personaExistente);
        return true; 
    }

    public Persona obtenerPersonaById(String id) {
        return personaRepository.findById(id).orElse(null);
    }

    public List<Persona> obtenerTodasLasPersonas() {
        return personaRepository.findAll();
    }

    public boolean eliminarPersona(String id) {
        if (!personaRepository.existsById(id)) {
            return false;
        }

        Persona persona = personaRepository.findById(id).get();
        persona.setEstado("Inactivo");
        personaRepository.save(persona);
        return true;
    }

    public boolean agregarProveedor(Persona persona) {
        if (personaRepository.existsByNombreAndCorreoAndRfc(persona.getNombre(), persona.getCorreo(), persona.getRfc())) {
            return false; 
        }

        Rol rol = persona.getRol();

        Direccion direccion = persona.getDireccion(); 
        direccionRepository.save(direccion); 

        persona.setDireccion(direccion);
        persona.setRol(rol);
        persona.setEstado("Disponible");

        personaRepository.save(persona);
        return true; 
    }

    public List<Persona> obtenerPersonasPorIdRol(Rol rol) {
        return personaRepository.findByRol(rol);
    }
}