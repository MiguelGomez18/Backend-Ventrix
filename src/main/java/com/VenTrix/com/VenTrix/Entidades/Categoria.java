package com.VenTrix.com.VenTrix.Entidades;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Data // Incluye los getters, setters y toString
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private String sucursal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'ACTIVO'")
    private Activo activo;

    @OneToMany(targetEntity = Producto.class, fetch = FetchType.LAZY, mappedBy = "categoria")
    @JsonIncludeProperties("id_producto")
    private List<Producto> producto;

}
