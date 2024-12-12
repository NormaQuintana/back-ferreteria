package mx.uv.back_ferreteria.Servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import mx.uv.back_ferreteria.Modelo.Persona;
import mx.uv.back_ferreteria.Modelo.Usuario;
import mx.uv.back_ferreteria.Repository.PersonaRepository;
import mx.uv.back_ferreteria.Repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PersonaRepository personaRepository;


    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Validación de credenciales (sin cambios)
    public Optional<Usuario> validarCredenciales(String usuario, String contrasena) {
        return usuarioRepository.validarCredenciales(usuario, contrasena);
    }

    // Obtener usuarios activos (sin cambios)
    public List<Usuario> obtenerUsuariosActivos() {
        return usuarioRepository.findUsuariosActivos();
    }

    // Cambiar contraseña usando el correo asociado a Persona
    public String cambiarContrasena(String correo, String nuevaContrasena) {
        int updatedRows = usuarioRepository.actualizarContrasena(correo, nuevaContrasena);
        return updatedRows > 0 ? "Se ha actualizado la contraseña" : "No se ha actualizado la contraseña";
    }

    // Eliminar usuario (sin cambios)
    public boolean eliminarUsuario(String idUsuario) {
        return usuarioRepository.eliminarUsuario(idUsuario) > 0;
    }

    // Obtener usuario por ID (sin cambios)
    public Usuario obtenerUsuarioById(String id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public List<Usuario> obtenerTodasLosU() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario crearUsuario(Usuario usuario) throws Exception {
        // Verificar si la persona ya existe con los tres parámetros (nombre, correo, rfc)
        if (personaRepository.existsByNombreAndCorreoAndRfc(
                usuario.getPersona().getNombre(), 
                usuario.getPersona().getCorreo(), 
                usuario.getPersona().getRfc())) {
            throw new Exception("La persona ya existe con estos datos.");
        }

        // Guardar la persona
        Persona personaGuardada = personaRepository.save(usuario.getPersona());

        // Asignar la persona guardada al usuario
        usuario.setPersona(personaGuardada);

        // Guardar el usuario
        return usuarioRepository.save(usuario);
    }
}

