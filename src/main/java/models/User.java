package models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.DefaultValue;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "username" }),
        @UniqueConstraint(columnNames = { "email" }),
        @UniqueConstraint(columnNames = { "identity" })
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "La 'identificacion' no puede ser nulo")
    @NotBlank(message = "La identificacion no puede ser vacio")
    @Pattern(regexp = "[0-9]{10}", message = "La identificacion debe tener 10 digitos")
    @Column(name = "identity", unique = true)
    private String identity;

    @NotNull(message = "El 'nombre de usuario' no puede ser nulo")
    @NotBlank(message = "El nombre de usuario no puede ser vacio")
    @Column(name = "username", unique = true)
    private String username;

    @NotNull(message = "El 'email' no puede ser nulo")
    @Email(message = "El email no es valido")
    @NotBlank(message = "El email no puede ser vacio")
    @Column(name = "email", unique = true)
    private String email;

    @NotNull(message = "La 'contraseña' no puede ser nulo")
    @NotBlank(message = "La contraseña no puede ser vacio")
    @Column(name = "password")
    private String password;

    @NotNull(message = "El 'nombre' no puede ser nulo")
    @NotBlank(message = "El nombre no puede ser vacio")
    @Column(name = "firstname")
    private String firstname;

    @NotNull(message = "El 'apellido' no puede ser nulo")
    @NotBlank(message = "El apellido no puede ser vacio")
    @Column(name = "lastname")
    private String lastname;

    @Pattern(regexp = "[0-9]{10}", message = "El telefono no es válido")
    @Column(name = "phone")
    private String phone;

    @Column(name = "status")
    @DefaultValue("A")
    private char status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at")
    private java.sql.Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private java.sql.Timestamp updatedAt;

}