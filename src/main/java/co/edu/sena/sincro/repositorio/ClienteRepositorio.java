package co.edu.sena.sincro.repositorio;

import co.edu.sena.sincro.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio de clientes.
 * Al extender de JpaRepository el framework genera por si solo las
 * operaciones de insertar, consultar, actualizar y eliminar, por eso
 * esta interfaz no necesita implementacion ni sentencias SQL escritas.
 */
@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, Integer> {

    /**
     * Busca un cliente por su correo. Spring Data construye la consulta
     * a partir del nombre del metodo.
     */
    Optional<Cliente> findByEmailUsuario(String emailUsuario);

    /**
     * Devuelve los clientes ordenados alfabeticamente por nombre.
     */
    java.util.List<Cliente> findAllByOrderByNombreCompletoAsc();
}
