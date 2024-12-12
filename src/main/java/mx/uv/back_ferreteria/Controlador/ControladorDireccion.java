package mx.uv.back_ferreteria.Controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.uv.back_ferreteria.Modelo.Direccion;
import mx.uv.back_ferreteria.Servicio.DireccionService;

@RestController
@RequestMapping("/direcciones")
public class ControladorDireccion {
     @Autowired
    private DireccionService direccionService;

    @PostMapping("/agregar-direccion")
    public ResponseEntity<Direccion> crearDireccion(@RequestBody Direccion direccion) {
        return ResponseEntity.ok(direccionService.guardarDireccion(direccion));
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<Optional<Direccion>> obtenerDireccion(@PathVariable String id) {
        return ResponseEntity.ok(direccionService.obtenerDireccionPorId(id));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarDireccion(@PathVariable String id) {
        direccionService.eliminarDireccion(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/obtener-todas")
    public ResponseEntity<List<Direccion>> obtenerTodasLasDirecciones() {
        List<Direccion> direcciones = direccionService.obtenerTodasLasDirecciones();
        
        if (direcciones.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        } else {
            return ResponseEntity.ok(direcciones); 
        }
    }
}
