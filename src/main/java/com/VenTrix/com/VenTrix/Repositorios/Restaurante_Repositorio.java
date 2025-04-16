package com.VenTrix.com.VenTrix.Repositorios;

import com.VenTrix.com.VenTrix.Entidades.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Restaurante_Repositorio extends JpaRepository<Restaurante, String> {

    Restaurante findByUsuarioDocumento(String documento);
}
