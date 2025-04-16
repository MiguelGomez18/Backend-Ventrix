package com.VenTrix.com.VenTrix.Entidades;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id_producto;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private float precio;

    @Column(nullable = true, length = 1000)
    private String imagen;

    @Column(nullable = false)
    private boolean disponibilidad;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'ACTIVO'")
    private Activo activo;

    @OneToMany(targetEntity = Detalle_Pedido.class, fetch = FetchType.LAZY, mappedBy = "producto")
    @JsonBackReference
    private List<Detalle_Pedido> detalle_pedido;

    @ManyToOne(targetEntity = Sucursal.class)
    @JoinColumn(name = "id_sucursal")
    @JsonIncludeProperties("id_sucursal")
    private Sucursal sucursal;

    @ManyToOne(targetEntity = Categoria.class)
    @JoinColumn(name = "id_categoria")
    @JsonIncludeProperties("id_categoria")
    private Categoria categoria;
}
