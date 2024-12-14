package mx.uv.back_ferreteria.Controlador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.uv.back_ferreteria.ResponseMessage;

import mx.uv.back_ferreteria.Modelo.Usuario;
import mx.uv.back_ferreteria.Servicio.UsuarioService;

@RestController
@CrossOrigin(origins = "http://localhost:7890")
public class ControladorUsuario {

    @Autowired
    private final UsuarioService usuarioService;

    @Autowired
    public ControladorUsuario(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/validar")
    public ResponseEntity<?> validarCredenciales(@RequestParam String usuario, @RequestParam String contrasena) {
        System.out.println("Usuario recibido: " + usuario);
    System.out.println("Contraseña recibida: " + contrasena);

    Optional<Usuario> usuarioOpt = usuarioService.validarCredenciales(usuario, contrasena);

    if (usuarioOpt.isPresent()) {
        return ResponseEntity.ok("Usuario autenticado");
    } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario o contraseña incorrectos");
    }
    }

    @PutMapping("/cambiarContrasena")
    public ResponseEntity<String> cambiarContrasena(@RequestParam String correo, @RequestParam String nuevaContrasena) {
        return ResponseEntity.ok(usuarioService.cambiarContrasena(correo, nuevaContrasena));
    }

    @DeleteMapping("/usuario/eliminar/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable String id) {
        return usuarioService.eliminarUsuario(id) ? ResponseEntity.ok("Usuario eliminado") : ResponseEntity.badRequest().body("Error al eliminar usuario");
    }

    @PostMapping("/usuario/agregar")
    public ResponseEntity<?> agregarUsuario(@RequestBody Usuario usuario) {
        try {
            System.out.println("Datos recibidos: " + usuario);
            Usuario usuarioGuardado = usuarioService.crearUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage("Usuario agregado exitosamente"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/usuario/obtener-todas")
    public ResponseEntity<?> obtenerTodasLasPersonas() {
        //List<Usuario> usuarios = usuarioService.obtenerUsuariosActivos();
        try {
        // Obtén la lista de usuarios desde la base de datos
        List<Usuario> usuarios = usuarioService.obtenerTodasLosUsuarios();
        List<Usuario> usuariosDisponibles = usuarioService.obtenerUsuariosDisponibles();
        // Mapea los usuarios para devolver solo los datos requeridos
        List<Map<String, Object>> usuariosMapeados = usuarios.stream().map(usuario -> {
            Map<String, Object> personaMap = new HashMap<>();
            personaMap.put("nombre", usuario.getPersona().getNombre());
            personaMap.put("telefono", usuario.getPersona().getTelefono());
            personaMap.put("correo", usuario.getPersona().getCorreo());
            personaMap.put("rfc", usuario.getPersona().getRfc());

            Map<String, Object> rolMap = new HashMap<>();
            rolMap.put("id", usuario.getPersona().getRol().getId());
            rolMap.put("nombre", usuario.getPersona().getRol().getNombre());

            personaMap.put("rol", rolMap);

            Map<String, Object> usuarioMap = new HashMap<>();
            usuarioMap.put("usuario", usuario.getUsuario());
            usuarioMap.put("sueldo", usuario.getSueldo());
            usuarioMap.put("persona", personaMap);

            usuarioMap.put("Disponible", "Disponible".equalsIgnoreCase(usuario.getEstado()));

            return usuarioMap;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(usuariosMapeados);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    }
}
