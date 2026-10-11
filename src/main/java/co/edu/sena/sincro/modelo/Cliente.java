package co.edu.sena.sincro.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Entidad que representa la tabla t_cliente de la base de datos Sincro.
 * Las anotaciones de persistencia le dicen al framework a que tabla y a
 * que columna corresponde cada atributo, y las de validacion definen las
 * reglas que deben cumplir los datos antes de guardarse.
 */
@Entity
@Table(name = "t_cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer idCliente;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Pattern(regexp = "^[\\p{L} ]+$",
             message = "El nombre solo puede contener letras y espacios")
    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String nombreCompleto;

    @NotBlank(message = "El telefono es obligatorio")
    @Pattern(regexp = "^[0-9]{7,20}$",
             message = "El telefono debe tener entre 7 y 20 digitos, sin letras ni espacios")
    @Column(name = "telefono_contacto", nullable = false, length = 20)
    private String telefonoContacto;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no tiene un formato valido")
    @Size(max = 100, message = "El correo no puede superar los 100 caracteres")
    @Column(name = "email_usuario", nullable = false, length = 100, unique = true)
    private String emailUsuario;

    /**
     * Constructor vacio, lo necesita el framework para crear la entidad.
     */
    public Cliente() {
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }
}
