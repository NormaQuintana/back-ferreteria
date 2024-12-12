package mx.uv.back_ferreteria.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.uv.back_ferreteria.Modelo.ProductoVenta;

public interface ProductoVentaRepository extends JpaRepository<ProductoVenta, String> {
    
}
