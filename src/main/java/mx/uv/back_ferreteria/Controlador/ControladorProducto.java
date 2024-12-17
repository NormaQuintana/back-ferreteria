package mx.uv.back_ferreteria.Controlador;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;


import mx.uv.back_ferreteria.Modelo.Persona;
import mx.uv.back_ferreteria.Modelo.Producto;
import mx.uv.back_ferreteria.Repository.PersonaRepository;
import mx.uv.back_ferreteria.Repository.ProductoRepository;
import mx.uv.back_ferreteria.Servicio.ProductoService;

@CrossOrigin
@RestController
public class ControladorProducto {
    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @PostMapping("/producto/agregar")
    public ResponseEntity<?> agregarProducto(@RequestBody Producto producto) {
        if (producto.getPersona() != null && producto.getPersona().getId() != null) {
        Persona persona = personaRepository.findById(producto.getPersona().getId())
            .orElseThrow(() -> new RuntimeException("Persona no encontrada"));

        producto.setPersona(persona);
    }
        productoRepository.save(producto);
        return ResponseEntity.ok(Map.of("message", "Producto registrado correctamente"));
    }

    @GetMapping("/producto/obtener-todas")
    public ResponseEntity<List<Producto>> obtenerTodasLosProductosDisponibles() {
        List<Producto> productos = productoService.obtenerProductosDisponibles();
        
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        } else {
            return ResponseEntity.ok(productos); 
        }
    }

    @GetMapping("/producto/obtener/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable String id) {
        Producto producto = productoService.obtenerProductoById(id);
        if (producto != null) {
            return ResponseEntity.ok(producto);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/producto/eliminar/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable String id) {
        boolean result = productoService.eliminarProducto(id);
        if (result) {
            return ResponseEntity.ok("Persona eliminada con éxito.");
        } else {
            return ResponseEntity.status(404).body("Error: Persona no encontrada.");
        }
    }

    public ResponseEntity<?> reducirStock(@RequestBody Producto productoRequest) {
        Producto producto = productoService.obtenerProductoById(productoRequest.getIdProducto());
        if (producto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
        }
    
        if (producto.getCantidad() < productoRequest.getCantidad()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Stock insuficiente");
        }
        System.out.println("Cantidad antes de la actualización: " + producto.getCantidad());
        producto.setCantidad(producto.getCantidad() - productoRequest.getCantidad());
        System.out.println("Cantidad después de la actualización: " + producto.getCantidad());

        productoService.save(producto);
    
        return ResponseEntity.ok(producto);
    }
}
