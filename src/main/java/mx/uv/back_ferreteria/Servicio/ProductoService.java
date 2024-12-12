package mx.uv.back_ferreteria.Servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uv.back_ferreteria.Modelo.Persona;
import mx.uv.back_ferreteria.Modelo.Producto;

import mx.uv.back_ferreteria.Repository.PersonaRepository;
import mx.uv.back_ferreteria.Repository.ProductoRepository;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PersonaRepository personaRepository;

    public ProductoService(ProductoRepository productoRepository, PersonaRepository personaRepository) {
        this.productoRepository = productoRepository;
        this.personaRepository = personaRepository;
    }

    public Producto agregarProducto(Producto producto) {
        String idPersona = producto.getPersona().getId();
        System.out.println("ID Persona recibido: " + idPersona); 
    
        Optional<Persona> personaOpt = personaRepository.findById(idPersona);
    
        if (personaOpt.isPresent()) {
            producto.setPersona(personaOpt.get());
        } else {
            throw new RuntimeException("Proveedor con ID " + idPersona + " no encontrado");
        }
    
        producto.setEstado("Disponible");
        producto.setCodigo(producto.getIdProducto().replace("-", ""));
        return productoRepository.save(producto);
    }

    public List<Producto> obtenerProductosDisponibles() {
        return productoRepository.findProductosDisponibles();
    }

    public Producto obtenerProductoById(String id) {
        return productoRepository.findById(id).orElse(null);
    }

    public boolean eliminarProducto(String id) {
        if (!productoRepository.existsById(id)) {
            return false;
        }

        Producto producto = productoRepository.findById(id).get();
        producto.setEstado("Inactivo");
        productoRepository.save(producto);
        return true;
    }
}
