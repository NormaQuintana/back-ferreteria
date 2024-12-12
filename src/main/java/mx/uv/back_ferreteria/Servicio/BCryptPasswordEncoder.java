package mx.uv.back_ferreteria.Servicio;


import org.springframework.context.annotation.Bean;


public class BCryptPasswordEncoder {

@Bean
public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
}
