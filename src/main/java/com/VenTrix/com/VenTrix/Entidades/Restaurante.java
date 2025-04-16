package com.VenTrix.com.VenTrix.Entidades;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data // Incluye los getters, setters y toString
@NoArgsConstructor
@AllArgsConstructor
public class Restaurante {

    // Atributos de la clase Restaurante

    @Id
    @Column(unique = true, nullable = false, length = 100)
    private String id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = true, length = 200)
    private String descripcion;

    @Column(nullable = false, length = 10)
    private String telefono;

    @Column(nullable = false, length = 200)
    private String direccion;

    @Column(nullable = false, length = 100)
    private String correo;

    @Column(nullable = false)
    private String imagen;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate fecha_creacion;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate fecha_finalizacion;

    @Enumerated(EnumType.STRING)
    private Estado_Restaurante estado;

    @OneToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "documento", nullable = false) // Relación uno a uno
    private Usuario usuario;

    @OneToMany(targetEntity = Sucursal.class, fetch = FetchType.LAZY, mappedBy = "restaurante")
    @JsonManagedReference
    private List<Sucursal> sucursales;

}
