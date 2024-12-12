package mx.uv.back_ferreteria.Controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.uv.back_ferreteria.Modelo.Producto;
import mx.uv.back_ferreteria.Servicio.ProductoService;

@RestController
@RequestMapping("/productos")
public class ControladorProducto {
    @Autowired
    private ProductoService productoService;

    @PostMapping("/agregar")
    public ResponseEntity<Producto> agregarProducto(@RequestBody Producto producto) {
        try {
        Producto nuevoProducto = productoService.agregarProducto(producto);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    } catch (RuntimeException e) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
    }
    }

    @GetMapping("/obtener-todas")
    public ResponseEntity<List<Producto>> obtenerTodasLosProductosDisponibles() {
        List<Producto> productos = productoService.obtenerProductosDisponibles();
        
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        } else {
            return ResponseEntity.ok(productos); 
        }
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable String id) {
        Producto producto = productoService.obtenerProductoById(id);
        if (producto != null) {
            return ResponseEntity.ok(producto);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable String id) {
        boolean result = productoService.eliminarProducto(id);
        if (result) {
            return ResponseEntity.ok("Persona eliminada con éxito.");
        } else {
            return ResponseEntity.status(404).body("Error: Persona no encontrada.");
        }
    }
}
