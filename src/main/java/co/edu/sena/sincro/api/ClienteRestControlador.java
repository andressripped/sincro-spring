package co.edu.sena.sincro.api;

import co.edu.sena.sincro.modelo.Cliente;
import co.edu.sena.sincro.servicio.ClienteServicio;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Servicio REST del modulo de clientes.
 * A diferencia del controlador que devuelve paginas HTML, este devuelve
 * los datos en formato JSON para que el front-end hecho en React pueda
 * consumirlos desde el navegador.
 */
@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "http://localhost:5173")
public class ClienteRestControlador {

    private final ClienteServicio clienteServicio;

    public ClienteRestControlador(ClienteServicio clienteServicio) {
        this.clienteServicio = clienteServicio;
    }

    /**
     * Devuelve todos los clientes registrados.
     */
    @GetMapping
    public List<Cliente> listar() {
        return clienteServicio.listar();
    }

    /**
     * Devuelve un cliente por su identificador.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable("id") Integer id) {
        Optional<Cliente> cliente = clienteServicio.buscarPorId(id);

        return cliente.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Registra un cliente nuevo.
     */
    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Cliente cliente) {

        if (clienteServicio.correoRepetido(cliente.getEmailUsuario(), null)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("emailUsuario", "Ese correo ya esta registrado"));
        }

        Cliente guardado = clienteServicio.guardar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    /**
     * Actualiza los datos de un cliente existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable("id") Integer id,
                                        @Valid @RequestBody Cliente cliente) {

        if (clienteServicio.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (clienteServicio.correoRepetido(cliente.getEmailUsuario(), id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("emailUsuario", "Ese correo ya esta registrado"));
        }

        cliente.setIdCliente(id);
        return ResponseEntity.ok(clienteServicio.guardar(cliente));
    }

    /**
     * Elimina el cliente indicado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) {

        if (clienteServicio.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        clienteServicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Traduce los errores de validacion a un objeto JSON con el nombre
     * del campo y su mensaje, para que React los muestre bajo cada campo.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarErroresDeValidacion(
            MethodArgumentNotValidException excepcion) {

        Map<String, String> errores = new HashMap<>();

        for (FieldError error : excepcion.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(errores);
    }
}
