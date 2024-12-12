package mx.uv.back_ferreteria.Servicio;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.uv.back_ferreteria.Modelo.Direccion;
import mx.uv.back_ferreteria.Repository.DireccionRepository;

@Service
public class DireccionService {
    @Autowired
    private DireccionRepository direccionRepository;

    public Direccion guardarDireccion(Direccion direccion) {
        return direccionRepository.save(direccion); 
    }

    public Optional<Direccion> obtenerDireccionPorId(String id) {
        return direccionRepository.findById(id); 
    }

    public void eliminarDireccion(String id) {
        direccionRepository.deleteById(id);
    }

    public List<Direccion> obtenerTodasLasDirecciones() {
        return direccionRepository.findAll();
    }
}
