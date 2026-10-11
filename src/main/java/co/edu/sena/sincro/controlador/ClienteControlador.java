package co.edu.sena.sincro.controlador;

import co.edu.sena.sincro.modelo.Cliente;
import co.edu.sena.sincro.servicio.ClienteServicio;

import jakarta.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

/**
 * Controlador del modulo de clientes.
 * Cada metodo atiende una direccion del navegador: las anotaciones
 * GetMapping y PostMapping reemplazan lo que antes se hacia con un
 * servlet y sus metodos doGet y doPost.
 */
@Controller
@RequestMapping("/clientes")
public class ClienteControlador {

    private final ClienteServicio clienteServicio;

    public ClienteControlador(ClienteServicio clienteServicio) {
        this.clienteServicio = clienteServicio;
    }

    /**
     * Muestra la lista de clientes registrados.
     */
    @GetMapping
    public String listar(Model modelo) {
        modelo.addAttribute("clientes", clienteServicio.listar());
        modelo.addAttribute("total", clienteServicio.contar());
        return "lista";
    }

    /**
     * Muestra el formulario vacio para registrar un cliente nuevo.
     */
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model modelo) {
        modelo.addAttribute("cliente", new Cliente());
        modelo.addAttribute("titulo", "Registrar cliente");
        return "formulario";
    }

    /**
     * Muestra el formulario con los datos del cliente que se va a editar.
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Integer id,
                                          Model modelo,
                                          RedirectAttributes redireccion) {

        Optional<Cliente> cliente = clienteServicio.buscarPorId(id);

        if (cliente.isEmpty()) {
            redireccion.addFlashAttribute("mensaje", "No se encontro el cliente solicitado");
            return "redirect:/clientes";
        }

        modelo.addAttribute("cliente", cliente.get());
        modelo.addAttribute("titulo", "Editar cliente");
        return "formulario";
    }

    /**
     * Guarda un cliente nuevo o los cambios de uno existente.
     * La anotacion Valid hace que el framework revise las reglas
     * declaradas en la entidad antes de entrar al metodo.
     */
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("cliente") Cliente cliente,
                          BindingResult resultado,
                          Model modelo,
                          RedirectAttributes redireccion) {

        boolean edicion = cliente.getIdCliente() != null;

        // Si alguna validacion fallo se vuelve al formulario mostrando los errores
        if (resultado.hasErrors()) {
            modelo.addAttribute("titulo", edicion ? "Editar cliente" : "Registrar cliente");
            return "formulario";
        }

        if (clienteServicio.correoRepetido(cliente.getEmailUsuario(), cliente.getIdCliente())) {
            resultado.rejectValue("emailUsuario", "repetido", "Ese correo ya esta registrado");
            modelo.addAttribute("titulo", edicion ? "Editar cliente" : "Registrar cliente");
            return "formulario";
        }

        try {
            clienteServicio.guardar(cliente);
            redireccion.addFlashAttribute("mensaje",
                    edicion ? "Cliente actualizado correctamente" : "Cliente registrado correctamente");

        } catch (DataIntegrityViolationException e) {
            // Protege el caso en que dos personas registren el mismo correo a la vez
            redireccion.addFlashAttribute("mensaje", "No se pudo guardar, el correo ya esta registrado");
        }

        return "redirect:/clientes";
    }

    /**
     * Elimina el cliente indicado.
     */
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Integer id, RedirectAttributes redireccion) {

        try {
            clienteServicio.eliminar(id);
            redireccion.addFlashAttribute("mensaje", "Cliente eliminado correctamente");

        } catch (DataIntegrityViolationException e) {
            // Pasa cuando el cliente tiene citas registradas que dependen de el
            redireccion.addFlashAttribute("mensaje",
                    "No se puede eliminar: el cliente tiene citas registradas");
        }

        return "redirect:/clientes";
    }
}
