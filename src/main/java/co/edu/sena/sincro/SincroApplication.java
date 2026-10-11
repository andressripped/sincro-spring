package co.edu.sena.sincro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicacion.
 * La anotacion SpringBootApplication le indica al framework que debe
 * buscar los controladores, servicios y repositorios de este paquete
 * y levantar el servidor web embebido.
 */
@SpringBootApplication
public class SincroApplication {

    public static void main(String[] args) {
        SpringApplication.run(SincroApplication.class, args);
    }
}
