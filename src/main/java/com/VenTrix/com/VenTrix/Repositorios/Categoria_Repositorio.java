package com.VenTrix.com.VenTrix.Repositorios;

import com.VenTrix.com.VenTrix.Entidades.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Categoria_Repositorio extends JpaRepository<Categoria, Integer> {
    List<Categoria> findBySucursal(String id_sucursal);
}
