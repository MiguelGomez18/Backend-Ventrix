package com.VenTrix.com.VenTrix.Entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Data // Incluye los getters, setters y toString
@NoArgsConstructor
@AllArgsConstructor
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado_Mesa estado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'ACTIVO'")
    private Activo activo;

    @ManyToOne(targetEntity = Sucursal.class)
    @JoinColumn(name = "id_sucursal")
    @JsonBackReference
    private Sucursal sucursal;

    @OneToMany(targetEntity = Pedido.class, fetch = FetchType.LAZY, mappedBy = "mesa")
    @JsonIncludeProperties({"id_pedido","total_pedido","tipo_pago","estado"})
    private List<Pedido> pedido;
}
