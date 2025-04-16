package com.VenTrix.com.VenTrix.Repositorios;

import com.VenTrix.com.VenTrix.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Usuario_Repositorio extends JpaRepository<Usuario, String> {
    Usuario findByCorreo(String correo);
    List<Usuario> findBySucursal(String sucursal);
}
