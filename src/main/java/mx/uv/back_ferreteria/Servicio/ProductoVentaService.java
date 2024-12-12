package mx.uv.back_ferreteria.Servicio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uv.back_ferreteria.Modelo.Producto;
import mx.uv.back_ferreteria.Modelo.ProductoVenta;
import mx.uv.back_ferreteria.Modelo.Venta;
import mx.uv.back_ferreteria.Repository.ProductoRepository;
import mx.uv.back_ferreteria.Repository.ProductoVentaRepository;
import mx.uv.back_ferreteria.Repository.VentaRepository;

@Service
public class ProductoVentaService {
    @Autowired
    private ProductoVentaRepository productoVentaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private VentaRepository ventaRepository;

    public ProductoVenta agregarProductoVenta(ProductoVenta productoVenta){
        String idProducto = productoVenta.getProducto().getIdProducto();
        Optional<Producto> productoOpt = productoRepository.findById(idProducto);

        String idVenta = productoVenta.getVenta().getIdVenta();
        Optional<Venta> ventaOpt = ventaRepository.findById(idVenta);

        if (productoOpt.isPresent()) {
            productoVenta.setProducto(productoOpt.get());
        } else {
            throw new RuntimeException("Producto con ID " + idProducto + " no encontrado");
        }

        if (ventaOpt.isPresent()) {
            productoVenta.setVenta(ventaOpt.get());
        } else {
            throw new RuntimeException("Venta con ID " + idProducto + " no encontrado");
        }

        return productoVentaRepository.save(productoVenta);
    }
}
