package mx.uv.back_ferreteria.Servicio;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uv.back_ferreteria.Modelo.Usuario;
import mx.uv.back_ferreteria.Modelo.Venta;
import mx.uv.back_ferreteria.Repository.UsuarioRepository;
import mx.uv.back_ferreteria.Repository.VentaRepository;

@Service
public class VentaService {
    @Autowired
    private VentaRepository ventaRepository;

    private final UsuarioRepository usuarioRepository;

    public VentaService(VentaRepository ventaRepository, UsuarioRepository usuarioRepository) {
        this.ventaRepository = ventaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Venta agregarVenta(Venta venta) {
        String idUsuario = venta.getUsuario().getIdUsuario();

        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);

        if (usuarioOpt.isPresent()) {
            venta.setUsuario(usuarioOpt.get());
        } else {
            throw new RuntimeException("Usuario con ID " + idUsuario + " no encontrado");
        }
        venta.setFecha(LocalDate.now());
        return ventaRepository.save(venta);
    }

    public List<Venta> obtenerTodasLasVentasDiarias() {
        LocalDate hoy = LocalDate.now();
        return ventaRepository.findByFecha(hoy);
    }
}
