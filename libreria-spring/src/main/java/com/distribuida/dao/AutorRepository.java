package com.distribuida.dao;

import com.distribuida.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Integer> {

    // Ejemplo válido si la clase Autor tiene la propiedad 'nombre'
    Autor findByNombre(String nombre);
}
