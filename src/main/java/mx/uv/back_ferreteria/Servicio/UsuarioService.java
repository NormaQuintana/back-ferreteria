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

    public Optional<Usuario> validarCredenciales(String usuario, String contrasena) {
        return usuarioRepository.validarCredenciales(usuario, contrasena);
    }

    public List<Usuario> obtenerUsuariosDisponibles() {
        return usuarioRepository.findUsuariosDisponibles();
    }

    public String cambiarContrasena(String correo, String nuevaContrasena) {
        int updatedRows = usuarioRepository.actualizarContrasena(correo, nuevaContrasena);
        return updatedRows > 0 ? "Se ha actualizado la contraseña" : "No se ha actualizado la contraseña";
    }

    public boolean eliminarUsuario(String idUsuario) {
        return usuarioRepository.eliminarUsuario(idUsuario) > 0;
    }

    public Usuario obtenerUsuarioById(String id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public List<Usuario> obtenerTodasLosUsuarios() {
        return usuarioRepository.findAllWithPersonaAndRol();
    }

    @Transactional
    public Usuario crearUsuario(Usuario usuario) throws Exception {
        if (personaRepository.existsByNombreAndCorreoAndRfc(
                usuario.getPersona().getNombre(), 
                usuario.getPersona().getCorreo(), 
                usuario.getPersona().getRfc())) {
            throw new Exception("La persona ya existe con estos datos.");
        }

        Persona personaGuardada = personaRepository.save(usuario.getPersona());

        usuario.setPersona(personaGuardada);

        return usuarioRepository.save(usuario);
    }
}

