package com.distribuida.dao;


import com.distribuida.model.Categoria;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class CategoriaRepositorioTestIntegracion {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    public void findAll(){
        List<Categoria> categoria = categoriaRepository.findAll();
        assertNotNull(categoria);
        assertTrue(categoria.size()>0);

        for(Categoria item: categoria){
            System.out.println(item.toString());
        }
    }

    @Test
    public void findOne(){
        Optional<Categoria> categoria = categoriaRepository.findById(1);
        assertTrue(categoria.isPresent(),"El Categoria con id= 1, deberia existir");
        System.out.println(categoria.toString());
    }

    @Test
    public void save(){
        Categoria categoria = new Categoria(1,"Adultos","Libros de Adultos");
        categoriaRepository.save(categoria);
        assertNotNull(categoria.getIdCategoria(),"El categoria guardado debe tener ID");
        assertEquals("Libros de Adultos", categoria.getDescripcion());
        assertEquals("Adultos", categoria.getCategoria());
    }

    @Test
    public void update(){
        Optional<Categoria> categoria = categoriaRepository.findById(1);
        assertTrue(categoria.isPresent(),"El Categoria con ID=1 debe existir para actualizarse");
        categoria.orElse(null).setCategoria("Historia");
        categoria.orElse(null).setDescripcion("Libros de Historia");
        Categoria categoriaactualizado = categoriaRepository.save(categoria.orElse(null));
        assertEquals("Historia",categoriaactualizado.getCategoria());
        assertEquals("Libros de Historia", categoriaactualizado.getDescripcion());
    }

    @Test
    public void delete(){
        if(categoriaRepository.existsById(2)){
            categoriaRepository.deleteById(2);
        }
        assertFalse(categoriaRepository.existsById(2));
    }


}
