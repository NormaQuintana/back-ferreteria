package mx.uv.back_ferreteria.Controlador;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RestController;

import mx.uv.back_ferreteria.ResponseMessage;
import mx.uv.back_ferreteria.Modelo.Persona;
import mx.uv.back_ferreteria.Modelo.Rol;
import mx.uv.back_ferreteria.Repository.RolRepository;
import mx.uv.back_ferreteria.Servicio.PersonaService;

@RestController
@CrossOrigin(origins = "http://localhost:5174")
public class ControladorProveedor {


    @Autowired
    private PersonaService personaService;

    @Autowired
    private RolRepository rolRepository;

    @PostMapping("/proveedor/agregar")
    public ResponseEntity<?> agregarProveedor(@RequestBody Persona persona) {
        try {
            boolean result = personaService.agregarProveedor(persona);
            if (result) {
                return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage("Proveedor registrado correctamente"));
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("El proveedor ya se encuentra en existencia");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al crear el proveedor");
        }
    }

    @PutMapping("/proveedor/editar/{id}")
    public ResponseEntity<String> editarPersona(@RequestBody Persona persona) {
    System.out.println("Proveedor recibida: " + persona);
    System.out.println("Direccion recibida: " + persona.getDireccion());
    
        boolean result = personaService.editarPersona(persona);
        if (result) {
            return ResponseEntity.ok("Proveedor editada con éxito.");
        } else {
            return ResponseEntity.status(404).body("Error: Persona no encontrada.");
        }
    }

    @GetMapping("/proveedor/obtener/{id}")
    public ResponseEntity<Persona> obtenerPersona(@PathVariable String id) {
        Persona persona = personaService.obtenerPersonaById(id);
        if (persona != null) {
            return ResponseEntity.ok(persona);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/proveedor/obtener-todas")
    public ResponseEntity<?> obtenerProveedores() {
        try {
            Rol rol = new Rol("9f7b755f-e3bb-485a-a31b-14987f91d9fe", "Proveedor");
            
            List<Persona> proveedores = personaService.obtenerPersonasPorIdRol(rol);

            if (proveedores.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay proveedores registrados");
            }

            List<Map<String, Object>> proveedoresMapeados = proveedores.stream().map(proveedor -> {
                Map<String, Object> proveedorMap = new HashMap<>();
                proveedorMap.put("idPersona", proveedor.getIdPersona());
                proveedorMap.put("nombre", proveedor.getNombre());
                proveedorMap.put("telefono", proveedor.getTelefono());
                proveedorMap.put("correo", proveedor.getCorreo());

                if (proveedor.getDireccion() != null) {
                    Map<String, Object> direccionMap = new HashMap<>();
                    direccionMap.put("calle", proveedor.getDireccion().getCalle());
                    direccionMap.put("numero", proveedor.getDireccion().getNumero());
                    direccionMap.put("colonia", proveedor.getDireccion().getColonia());
                    direccionMap.put("ciudad", proveedor.getDireccion().getCiudad());
                    proveedorMap.put("direccion", direccionMap);
                } else {
                    proveedorMap.put("direccion", null);
                }

                return proveedorMap;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(proveedoresMapeados);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener los proveedores: " + e.getMessage());
        }
    }

    @DeleteMapping("/proveedor/eliminar/{id}")
    public ResponseEntity<String> eliminarPersona(@PathVariable String id) {
        boolean result = personaService.eliminarPersona(id);
        if (result) {
            return ResponseEntity.ok("Proveedor eliminada con éxito.");
        } else {
            return ResponseEntity.status(404).body("Error: Persona no encontrada.");
        }
    }
}   
