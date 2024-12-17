package mx.uv.back_ferreteria.Controlador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import mx.uv.back_ferreteria.Modelo.Direccion;
import mx.uv.back_ferreteria.Modelo.Persona;
import mx.uv.back_ferreteria.Modelo.Proyecto;
import mx.uv.back_ferreteria.Servicio.PersonaService;
import mx.uv.back_ferreteria.Servicio.ProyectoService;

@RestController
@CrossOrigin(origins = "http://localhost:5174")
public class ControladorProyecto {

    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private PersonaService personaService;

    @PostMapping("/proyecto/agregar")
    public ResponseEntity<?> agregarProyecto(@RequestBody Proyecto proyecto) {
        boolean result = proyectoService.agregarProyecto(proyecto);
        if (result) {
            return ResponseEntity.ok().body(Map.of("status", 200,"mensaje", "Proyecto agregado con éxito"));
        } else {
            return ResponseEntity.status(400).body("El proyecto ya existe.");
        }
    }

    @GetMapping("/proyecto/obtener-todas")
    public ResponseEntity<?> obtenerProyectos() {
        try {
            List<Proyecto> proyectos = proyectoService.obtenerProyectos();
    
            if (proyectos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay proyectos registrados");
            }
    
            List<Proyecto> proyectosDisponibles = proyectos.stream()
                .filter(proyecto -> "Disponible".equals(proyecto.getEstado()))
                .collect(Collectors.toList());
    
            if (proyectosDisponibles.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay proyectos disponibles");
            }
    
            List<Map<String, Object>> proyectosMapeados = proyectosDisponibles.stream().map(proyecto -> {
                Map<String, Object> proyectoMap = new HashMap<>();
                proyectoMap.put("idProyecto", proyecto.getIdProyecto());
    
                Persona persona = proyecto.getPersona();
                if (persona != null) {
                    Map<String, Object> personaMap = new HashMap<>();
                    personaMap.put("idPersona", persona.getIdPersona());
                    personaMap.put("nombre", persona.getNombre());
                    personaMap.put("telefono", persona.getTelefono());
                    personaMap.put("correo", persona.getCorreo());
    
                    if (persona.getDireccion() != null) {
                        Map<String, Object> direccionPersonaMap = new HashMap<>();
                        direccionPersonaMap.put("calle", persona.getDireccion().getCalle());
                        direccionPersonaMap.put("numero", persona.getDireccion().getNumero());
                        direccionPersonaMap.put("colonia", persona.getDireccion().getColonia());
                        direccionPersonaMap.put("ciudad", persona.getDireccion().getCiudad());
                        personaMap.put("direccionPersona", direccionPersonaMap);
                    } else {
                        personaMap.put("direccionPersona", null);
                    }
    
                    proyectoMap.put("persona", personaMap);
                } else {
                    proyectoMap.put("persona", null);
                }
    
                Direccion direccion = proyecto.getDireccion();
                if (direccion != null) {
                    Map<String, Object> direccionProyectoMap = new HashMap<>();
                    direccionProyectoMap.put("calle", direccion.getCalle());
                    direccionProyectoMap.put("numero", direccion.getNumero());
                    direccionProyectoMap.put("colonia", direccion.getColonia());
                    direccionProyectoMap.put("ciudad", direccion.getCiudad());
                    proyectoMap.put("direccionProyecto", direccionProyectoMap);
                } else {
                    proyectoMap.put("direccionProyecto", null);
                }
    
                return proyectoMap;
            }).collect(Collectors.toList());
    
            return ResponseEntity.ok(proyectosMapeados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener los proyectos: " + e.getMessage());
        }
    }

    @GetMapping("/proyecto/obtener/{id}")
    public ResponseEntity<Proyecto> obtenerProyectoById(@PathVariable String id) {
        Optional<Proyecto> proyecto = proyectoService.obtenerProyectoById(id);
        if (proyecto.isPresent()) {
            return ResponseEntity.ok(proyecto.get());
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/proyecto/eliminar/{id}")
    public ResponseEntity<String> eliminarProyecto(@PathVariable String id) {
        try {
            Optional<Proyecto> optionalProyecto = proyectoService.obtenerProyectoById(id);
            
            if (optionalProyecto.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proyecto no encontrado.");
            }
            
            Proyecto proyecto = optionalProyecto.get();
            
            proyecto.setEstado("Inactivo");
            
            Persona persona = proyecto.getPersona();
            if (persona != null) {
                persona.setEstado("Inactivo");
                personaService.actualizarPersona(persona);
            }

            proyectoService.actualizarProyecto(proyecto); 

            return ResponseEntity.ok("Proyecto y persona actualizados a Inactivo con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el proyecto: " + e.getMessage());
        }
    }

}
