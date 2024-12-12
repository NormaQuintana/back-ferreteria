package mx.uv.back_ferreteria.Controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.uv.back_ferreteria.Modelo.Venta;
import mx.uv.back_ferreteria.Servicio.VentaService;

@RestController
@RequestMapping("/ventas")
public class ControladorVenta {
    @Autowired
    private VentaService ventaService;

    @PostMapping("/agregar")
    public ResponseEntity<Venta> agregarVenta(@RequestBody Venta venta) {
        return ResponseEntity.ok(ventaService.agregarVenta(venta));
    }

    @GetMapping("/obtener-diarias")
    public ResponseEntity<List<Venta>> obtenerTodasLasVentasDiarias() {
        List<Venta> ventas = ventaService.obtenerTodasLasVentasDiarias();
        
        if (ventas.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(ventas); 
        }
    }

}
