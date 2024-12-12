package mx.uv.back_ferreteria.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import mx.uv.back_ferreteria.Modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

        @Query("SELECT u FROM Usuario u WHERE u.usuario = :usuario AND u.contrasena = :contrasena")
        Optional<Usuario> validarCredenciales(@Param("usuario") String usuario, @Param("contrasena") String contrasena);
    
        @Query("SELECT u FROM Usuario u WHERE u.estado = 'Disponible'")
        List<Usuario> findUsuariosActivos();
    
        @Modifying
        @Transactional
        @Query("UPDATE Usuario u SET u.contrasena = :nuevaContrasena WHERE u.persona.correo = :correo")
        int actualizarContrasena(@Param("correo") String correo, @Param("nuevaContrasena") String nuevaContrasena);
    
        @Modifying
        @Transactional
        @Query("UPDATE Usuario u SET u.estado = 'Inactivo' WHERE u.idUsuario = :idUsuario")
        int eliminarUsuario(@Param("idUsuario") String idUsuario);
    
}
