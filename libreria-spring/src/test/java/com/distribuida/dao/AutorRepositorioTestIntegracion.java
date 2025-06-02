package com.distribuida.dao;

import com.distribuida.model.Autor;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(value = false)
public class AutorRepositorioTestIntegracion {

    @Autowired
    private AutorRepository autorRepository;

    @Test
    public void findAll(){
        List<Autor> autor = autorRepository.findAll();
        assertNotNull(autor);
        assertTrue(autor.size()>0);

        for(Autor item: autor){
            System.out.println(item.toString());
        }
    }

    @Test
    public void findOne(){
        Optional<Autor> autor = autorRepository.findById(1);
        assertTrue(autor.isPresent(),"El Autor con id= 1, deberia existir");
        System.out.println(autor.toString());
    }

    @Test
    public  void save(){
        Autor autor = new Autor(1,"Lucho","Diaz","Ecuador","Pifo","911","mg@gmail.com");
        autorRepository.save(autor);
        assertNotNull(autor.getIdAutor(),"El autor guardado debe tener ID");
        assertEquals("911", autor.getTelefono());
        assertEquals("Lucho", autor.getNombre());
    }

    @Test
    public void update(){
        Optional<Autor> autor = autorRepository.findById(1);

        assertTrue(autor.isPresent(),"El autor con ID=1 debe existir para actualizarse");
        autor.orElse(null).setNombre("Jona");
        autor.orElse(null).setApellido("Flores");
        autor.orElse(null).setDireccion("Tumbaco");
        autor.orElse(null).setTelefono("999");
        autor.orElse(null).setPais("Yaruqui");
        autor.orElse(null).setNombre("Jona");

        Autor autoractualizado = autorRepository.save(autor.orElse(null));

        assertEquals("Jona",autoractualizado.getNombre());
        assertEquals("Flores", autoractualizado.getApellido());

    }

    @Test
    public void delete(){
        if(autorRepository.existsById(50)){
            autorRepository.deleteById(50);
        }
        assertFalse(autorRepository.existsById(50));
    }

}
