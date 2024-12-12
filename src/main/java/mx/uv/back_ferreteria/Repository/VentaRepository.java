package mx.uv.back_ferreteria.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.uv.back_ferreteria.Modelo.Venta;

public interface VentaRepository extends JpaRepository <Venta, String> {
    List<Venta> findByFecha(LocalDate fecha);
}
