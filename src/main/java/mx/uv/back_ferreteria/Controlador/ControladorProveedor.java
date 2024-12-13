package mx.uv.back_ferreteria.Controlador;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.uv.back_ferreteria.Modelo.Persona;
import mx.uv.back_ferreteria.Servicio.PersonaService;

@RestController
@CrossOrigin(origins = "http://localhost:7890")
public class ControladorProveedor {


    @Autowired
    private PersonaService personaService;

    @PostMapping("/proveedor/agregar")
    public ResponseEntity<String> agregarProveedor(@RequestBody Persona persona) {
        boolean result = personaService.agregarProveedor(persona);
        if (result) {
            return ResponseEntity.ok("Proveedor agregado con éxito.");
        } else {
            return ResponseEntity.status(400).body("Error: Proveedor ya existe.");
        }
    }

    @PutMapping("/proveedor/editar/{id}")
    public ResponseEntity<String> editarPersona(@RequestBody Persona persona) {
    System.out.println("Proveedor recibida: " + persona);
    System.out.println("Direccion recibida: " + persona.getDireccion());
    
        boolean result = personaService.editarPersona(persona);
        if (result) {
            return ResponseEntity.ok("Proveedor editada con éxito.");
        } else {
            return ResponseEntity.status(404).body("Error: Persona no encontrada.");
        }
    }

    @GetMapping("/proveedor/obtener/{id}")
    public ResponseEntity<Persona> obtenerPersona(@PathVariable String id) {
        Persona persona = personaService.obtenerPersonaById(id);
        if (persona != null) {
            return ResponseEntity.ok(persona);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/proveedor/obtener-todas")
    public List<Persona> obtenerPersonasPorRol() {
    UUID idRolProveedor = UUID.fromString("9f7b755f-e3bb-485a-a31b-14987f91d9fe");
    return personaService.obtenerPersonasPorIdRol(idRolProveedor);
    }

    @DeleteMapping("/proveedor/eliminar/{id}")
    public ResponseEntity<String> eliminarPersona(@PathVariable String id) {
        boolean result = personaService.eliminarPersona(id);
        if (result) {
            return ResponseEntity.ok("Persona eliminada con éxito.");
        } else {
            return ResponseEntity.status(404).body("Error: Persona no encontrada.");
        }
    }
}   
