package mx.uv.back_ferreteria.Controlador;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.uv.back_ferreteria.ResponseMessage;
import mx.uv.back_ferreteria.Modelo.Proyecto;
import mx.uv.back_ferreteria.Servicio.ProyectoService;

@RestController
@CrossOrigin(origins = "http://localhost:7890")
public class ControladorProyecto {

    @Autowired
    private ProyectoService proyectoService;

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
    public ResponseEntity<List<Proyecto>> obtenerProyectos() {
        return ResponseEntity.ok(proyectoService.obtenerProyectos());
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
        boolean result = proyectoService.eliminarProyecto(id);
        if (result) {
            return ResponseEntity.ok("Proyecto eliminado con éxito.");
        } else {
            return ResponseEntity.status(404).body("Proyecto no encontrado.");
        }
    }

}
