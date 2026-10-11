package co.edu.sena.sincro.servicio;

import co.edu.sena.sincro.modelo.Cliente;
import co.edu.sena.sincro.repositorio.ClienteRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Capa de servicio del modulo de clientes.
 * Aqui van las reglas del negocio, separadas del controlador, que solo
 * se encarga de recibir las peticiones, y del repositorio, que solo
 * habla con la base de datos.
 */
@Service
public class ClienteServicio {

    private final ClienteRepositorio clienteRepositorio;

    /**
     * El framework entrega automaticamente el repositorio por el constructor.
     */
    public ClienteServicio(ClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }

    /**
     * Devuelve todos los clientes ordenados por nombre.
     */
    public List<Cliente> listar() {
        return clienteRepositorio.findAllByOrderByNombreCompletoAsc();
    }

    /**
     * Busca un cliente por su identificador.
     */
    public Optional<Cliente> buscarPorId(Integer idCliente) {
        return clienteRepositorio.findById(idCliente);
    }

    /**
     * Guarda un cliente nuevo o actualiza uno existente.
     */
    public Cliente guardar(Cliente cliente) {
        return clienteRepositorio.save(cliente);
    }

    /**
     * Elimina el cliente que tenga el identificador indicado.
     */
    public void eliminar(Integer idCliente) {
        clienteRepositorio.deleteById(idCliente);
    }

    /**
     * Indica si el correo ya esta registrado en otro cliente distinto.
     * Se usa antes de guardar para no depender del error de la base de datos.
     */
    public boolean correoRepetido(String emailUsuario, Integer idCliente) {
        Optional<Cliente> encontrado = clienteRepositorio.findByEmailUsuario(emailUsuario);

        if (encontrado.isEmpty()) {
            return false;
        }

        // Si el correo pertenece al mismo cliente que se esta editando no es un repetido
        return !encontrado.get().getIdCliente().equals(idCliente);
    }

    /**
     * Cuenta cuantos clientes hay registrados.
     */
    public long contar() {
        return clienteRepositorio.count();
    }
}
