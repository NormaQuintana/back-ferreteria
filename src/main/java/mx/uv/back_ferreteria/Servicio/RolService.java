package mx.uv.back_ferreteria.Servicio;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uv.back_ferreteria.Modelo.Rol;
import mx.uv.back_ferreteria.Repository.RolRepository;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public void crearRoles() {
        if (!rolRepository.existsById(UUID.fromString("d4e9f052-e2d2-4a4c-b225-7a69b0c9d0b3"))) {  
            rolRepository.save(new Rol(UUID.fromString("d4e9f052-e2d2-4a4c-b225-7a69b0c9d0b3"), "Administrador"));
        }
        if (!rolRepository.existsById(UUID.fromString("a56f7c83-2b5f-4019-b73c-9f6f96c25123"))) {
            rolRepository.save(new Rol(UUID.fromString("a56f7c83-2b5f-4019-b73c-9f6f96c25123"), "Vendedor"));
        }
        if (!rolRepository.existsById(UUID.fromString("9f7b755f-e3bb-485a-a31b-14987f91d9fe"))) {
            rolRepository.save(new Rol(UUID.fromString("9f7b755f-e3bb-485a-a31b-14987f91d9fe"), "Proveedor"));
        }
        if (!rolRepository.existsById(UUID.fromString("1c5b795a-3d41-4c3d-b10e-92c14a7f52f3"))) {
            rolRepository.save(new Rol(UUID.fromString("1c5b795a-3d41-4c3d-b10e-92c14a7f52f3"), "Gerente"));
        }
        if (!rolRepository.existsById(UUID.fromString("2c5a595e-7b49-4b2f-ae42-b62bb1df5037"))) {
            rolRepository.save(new Rol(UUID.fromString("2c5a595e-7b49-4b2f-ae42-b62bb1df5037"), "Encargado de Proyecto"));
        }
    }
}
