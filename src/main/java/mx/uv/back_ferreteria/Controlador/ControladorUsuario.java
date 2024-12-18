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

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;

import jakarta.servlet.http.HttpServletResponse;
import mx.uv.back_ferreteria.ResponseMessage;

import mx.uv.back_ferreteria.Modelo.Usuario;
import mx.uv.back_ferreteria.Servicio.UsuarioService;

@CrossOrigin
@RestController
public class ControladorUsuario {


    @Autowired
    private final UsuarioService usuarioService;

    @Autowired
    public ControladorUsuario(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/validar")
    public ResponseEntity<?> validarCredenciales(@RequestParam String usuario, @RequestParam String contrasena, HttpServletResponse response) {
        Optional<Usuario> usuarioOpt = usuarioService.validarCredenciales(usuario, contrasena);

        if (usuarioOpt.isPresent()) {
            Usuario usuarioAutenticado = usuarioOpt.get();
            String rol = usuarioAutenticado.getPersona().getRol().getNombre();
            
            Cookie rolCookie = new Cookie("rol", rol);
            rolCookie.setPath("/");
            //rolCookie.setHttpOnly(true); 
            rolCookie.setMaxAge(60 * 60 * 24);
            
            response.addCookie(rolCookie);

            return ResponseEntity.ok("Usuario autenticado");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario o contraseña incorrectos");
        }
    }


    @PutMapping("/cambiarContrasena")
    public ResponseEntity<String> cambiarContrasena(@RequestParam String correo, @RequestParam String nuevaContrasena) {
        return ResponseEntity.ok(usuarioService.cambiarContrasena(correo, nuevaContrasena));
    }

    @PutMapping("/usuario/eliminar/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable String id) {
        boolean eliminado = usuarioService.eliminarUsuario(id);
        if (eliminado) {
            return ResponseEntity.ok("Usuario eliminado");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
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
        try {
        List<Usuario> usuarios = usuarioService.obtenerTodasLosUsuarios();
        List<Usuario> usuariosDisponibles = usuarioService.obtenerUsuariosDisponibles();
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
            usuarioMap.put("idUsuario", usuario.getIdUsuario());
            usuarioMap.put("usuario", usuario.getUsuario());
            usuarioMap.put("estado", usuario.getEstado());
            usuarioMap.put("sueldo", usuario.getSueldo());
            usuarioMap.put("persona", personaMap);

            return usuarioMap;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(usuariosMapeados);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    }
    
}
