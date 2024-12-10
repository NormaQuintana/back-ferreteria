package mx.uv.back_ferreteria.Controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.uv.back_ferreteria.Modelo.Direccion;
import mx.uv.back_ferreteria.Modelo.Persona;
import mx.uv.back_ferreteria.Servicio.PersonaService;

@RestController
@RequestMapping("/personas")
public class ControladorPersona {
    @Autowired
    private PersonaService personaService;

    // Método para agregar una nueva persona
    @PostMapping("/agregar")
    public ResponseEntity<String> agregarPersona(@RequestBody Persona persona) {
        boolean result = personaService.agregarPersona(persona);
        if (result) {
            return ResponseEntity.ok("Persona agregada con éxito.");
        } else {
            return ResponseEntity.status(400).body("Error: Persona ya existe.");
        }
    }

    @PutMapping("/editar/{id}")
public ResponseEntity<String> editarPersona(@RequestBody Persona persona) {
    System.out.println("Persona recibida: " + persona);
    System.out.println("Direccion recibida: " + persona.getDireccion());
    
    boolean result = personaService.editarPersona(persona);
    if (result) {
        return ResponseEntity.ok("Persona editada con éxito.");
    } else {
        return ResponseEntity.status(404).body("Error: Persona no encontrada.");
    }
}

    // Método para obtener una persona por su ID
    @GetMapping("/obtener/{id}")
    public ResponseEntity<Persona> obtenerPersona(@PathVariable String id) {
        Persona persona = personaService.obtenerPersonaById(id);
        if (persona != null) {
            return ResponseEntity.ok(persona);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/obtener-todas")
    public ResponseEntity<List<Persona>> obtenerTodasLasPersonas() {
        List<Persona> personas = personaService.obtenerTodasLasPersonas();
        
        if (personas.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        } else {
            return ResponseEntity.ok(personas); 
        }
    }

    // Método para eliminar (inactivar) una persona por su ID
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarPersona(@PathVariable String id) {
        boolean result = personaService.eliminarPersona(id);
        if (result) {
            return ResponseEntity.ok("Persona eliminada con éxito.");
        } else {
            return ResponseEntity.status(404).body("Error: Persona no encontrada.");
        }
    }
}
