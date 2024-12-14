package mx.uv.back_ferreteria.Controlador;

import java.util.List;
import java.util.Optional;

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
            
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage("Usuario agregado exitosamente"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/usuario/obtener-todas")
    public ResponseEntity<List<Usuario>> obtenerTodasLasPersonas() {
        List<Usuario> usuarios = usuarioService.obtenerUsuariosActivos();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        } else {
            return ResponseEntity.ok(usuarios); 
        }
    }
}
