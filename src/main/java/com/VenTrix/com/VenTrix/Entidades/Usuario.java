package com.VenTrix.com.VenTrix.Entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data // Incluye los getters, setters y toString
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "password")
public class Usuario {

    // Atributos de la clase Usuario

    @Id
    @Column(unique = true, length = 10, nullable = false)
    private String documento;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(unique = true, nullable = false, length = 100)
    private String correo;

    @Column(nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    private Rol_Usuario rol;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate fecha_creacion;

    @Column(nullable = true, length = 100)
    private String sucursal;

    @Enumerated(EnumType.STRING)
    private Estado_Usuario estado;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL) // Relación inversa uno a uno
    @JsonBackReference
    private Restaurante restaurante;

}
