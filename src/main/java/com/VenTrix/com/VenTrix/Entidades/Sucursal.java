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
public class Sucursal {

    // Atributos de la clase Sucursal

    @Id
    @Column(unique = true, nullable = false, length = 100)
    private String id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 200)
    private String direccion;

    @Column(nullable = false, length = 100)
    private String ciudad;

    @Column(nullable = false, length = 10)
    private String telefono;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate fecha_apertura;

    @Enumerated(EnumType.STRING)
    private Estado_Sucursal estado;

    @Column(nullable = true, length = 10)
    private String administrador;

    @ManyToOne(targetEntity = Restaurante.class)
    @JoinColumn(name = "id_restaurante")
    @JsonBackReference
    private Restaurante restaurante;
}
