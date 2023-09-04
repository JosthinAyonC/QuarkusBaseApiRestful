package models;

import java.util.HashSet;
import java.util.Set;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "El 'nombre' no puede ser nulo")
    @NotBlank(message = "El nombre no puede ser vacio")
    @Column(name = "name")
    private String name;

    @Length(max = 255, message = "La descripcion debe tener maximo 255 caracteres")
    @Column(name = "description")
    private String description;

    @Column(name = "status")
    @DefaultValue("A")
    private char status;

    @ManyToMany(mappedBy = "roles")
    @JsonbTransient
    private Set<User> users = new HashSet<>();
}