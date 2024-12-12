package mx.uv.back_ferreteria.Servicio;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uv.back_ferreteria.Modelo.Direccion;
import mx.uv.back_ferreteria.Modelo.Persona;
import mx.uv.back_ferreteria.Repository.DireccionRepository;
import mx.uv.back_ferreteria.Repository.PersonaRepository;
import mx.uv.back_ferreteria.Repository.RolRepository;

@Service
public class PersonaService {
     @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private DireccionRepository direccionRepository; // Para guardar la dirección

     @Autowired
    private RolRepository rolRepository; // Repositorio para la entidad Rol

    public boolean agregarPersona(Persona persona) {

        // Verificar si la persona ya existe por nombre, correo y RFC
        if (personaRepository.existsByNombreAndCorreoAndRfc(persona.getNombre(), persona.getCorreo(), persona.getRfc())) {
            return false; 
        }

        Direccion direccion = persona.getDireccion(); // Obtener la dirección de la persona
        direccionRepository.save(direccion); // Guardar la dirección en la tabla DIRECCION

        // Asignar la dirección recién guardada a la persona
        persona.setDireccion(direccion);
        persona.setEstado("Disponible");
        // Guardar la persona
        personaRepository.save(persona);
        return true; // Persona guardada con éxito
    }

    public boolean editarPersona(Persona persona) {
        if (!personaRepository.existsById(persona.getId())) {
            return false;
        }
    
        Persona personaExistente = personaRepository.findById(persona.getId()).orElse(null);
    
        if (personaExistente == null) {
            return false;
        }

        // Modificar los datos de la persona con los nuevos valores
        personaExistente.setNombre(persona.getNombre());
        personaExistente.setTelefono(persona.getTelefono());
        personaExistente.setCorreo(persona.getCorreo());
        personaExistente.setRfc(persona.getRfc());
        //personaExistente.setIdRol(persona.getIdRol());
        personaExistente.setDireccion(persona.getDireccion()); // Si deseas modificar la dirección
        personaExistente.setEstado(persona.getEstado() != null ? persona.getEstado() : "Disponible"); // Si el estado no se pasa, dejar "Disponible"
    
        // Guardar la persona con los cambios
        personaRepository.save(personaExistente);
        return true; // Persona editada con éxito
    }

    public Persona obtenerPersonaById(String id) {
        return personaRepository.findById(id).orElse(null);
    }

    public List<Persona> obtenerTodasLasPersonas() {
        return personaRepository.findAll();
    }

    public boolean eliminarPersona(String id) {
        // Verificar si la persona existe
        if (!personaRepository.existsById(id)) {
            return false;
        }

        // Cambiar el estado a 'Inactivo'
        Persona persona = personaRepository.findById(id).get();
        persona.setEstado("Inactivo");
        personaRepository.save(persona);
        return true;
    }

    public boolean agregarProveedor(Persona persona) {
        // Verificar si la persona ya existe por nombre, correo y RFC
        if (personaRepository.existsByNombreAndCorreoAndRfc(persona.getNombre(), persona.getCorreo(), persona.getRfc())) {
            return false; 
        }

        // Asignar el idRol para proveedor como un UUID
        persona.setIdRol(UUID.fromString("9f7b755f-e3bb-485a-a31b-14987f91d9fe")); // ID de proveedor como UUID

        Direccion direccion = persona.getDireccion(); // Obtener la dirección de la persona
        direccionRepository.save(direccion); // Guardar la dirección en la tabla DIRECCION

        // Asignar la dirección recién guardada a la persona
        persona.setDireccion(direccion);
        persona.setEstado("Disponible");

        // Guardar la persona (proveedor)
        personaRepository.save(persona);
        return true; // Proveedor guardado con éxito
    }

    public List<Persona> obtenerPersonasPorIdRol(UUID idRol) {
        return personaRepository.findByIdRol(idRol);
    }
}