package com.VenTrix.com.VenTrix.Entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_pedido;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate fecha_pedido;

    @Column(nullable = false, columnDefinition = "TIME")
    private LocalTime hora_pedido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'ORDENADO'")
    private Estado_Pedido estado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'ACTIVO'")
    private Activo activo;

    @Column(nullable = true, columnDefinition = "float default 0.0")
    private float total_pedido;

    @Column(nullable = true)
    private String nombre;

    @Column(nullable = false)
    private String sucursal;

    @ManyToOne(targetEntity = Mesa.class)
    @JoinColumn(name = "id_mesa")
    @JsonIncludeProperties("id")
    private Mesa mesa;

    @ManyToOne
    @JoinColumn(name = "id_tipo_pago")
    @JsonIncludeProperties("id")
    private Tipo_pago tipo_pago;

    @OneToMany(targetEntity = Detalle_Pedido.class, fetch = FetchType.LAZY, mappedBy = "pedido")
    @JsonBackReference
    private List<Detalle_Pedido> detalle_pedido;

}
