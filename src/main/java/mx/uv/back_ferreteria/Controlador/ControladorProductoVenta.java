package mx.uv.back_ferreteria.Controlador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import mx.uv.back_ferreteria.Modelo.ProductoVenta;
import mx.uv.back_ferreteria.Servicio.ProductoVentaService;



@CrossOrigin
@RestController
public class ControladorProductoVenta {



    @Autowired
    private ProductoVentaService productoVentaService;

    @PostMapping("/producto-venta/agregar")
    public ResponseEntity<ProductoVenta> agregarProductoVenta(@RequestBody ProductoVenta productoVenta) {
        return ResponseEntity.ok(productoVentaService.agregarProductoVenta(productoVenta));
    }
}
