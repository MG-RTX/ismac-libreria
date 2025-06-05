package com.distribuida.dao;

import com.distribuida.model.Autor;
import com.distribuida.model.Categoria;
import com.distribuida.model.Libro;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(value = false)

public class LibroRepositoryTestIntegracion {
    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Test
    public void findAll(){
        List<Libro> libros = libroRepository.findAll();
        for (Libro item: libros){
            System.out.println(item.toString());
        }
    }

    @Test
    public void findOne(){
        Optional<Libro>libro = libroRepository.findById(1);
        System.out.println(libro.orElse(null).toString());
    }

    @Test
    public void save(){
        Libro libro = new Libro();
        Optional<Categoria>categoria = categoriaRepository.findById(1);
        Optional<Autor>autor = autorRepository.findById(1);
        libro.setTitulo("Aventuras de Steff");
        libro.setEditorial("Nose");
        libro.setNumPaginas(1);
        libro.setEdicion("Primera");
        libro.setIdioma("Quechua");
        libro.setFechaPublicacion(new Date());
        libro.setDescripcion("Ara");
        libro.setTipoPasta("Amarrila");
        libro.setIsbn("ISBN");
        libro.setNumEjemplares(12);
        libro.setPortada("Roha");
        libro.setPresentacion("Buena");
        libro.setPrecio(12.0);
        libro.setCategoria(categoria.orElse(null));
        libro.setAutor(autor.orElse(null));

        libroRepository.save(libro);
    }

    @Test
    public void update(){
        Optional<Libro>libroexistente = libroRepository.findById(1);
        Optional<Categoria>categoria = categoriaRepository.findById(1);
        Optional<Autor>autor = autorRepository.findById(1);

        libroexistente.orElse(null).setTitulo("Steff");
        libroexistente.orElse(null).setEditorial("Noses");
        libroexistente.orElse(null).setNumPaginas(2);
        libroexistente.orElse(null).setEdicion("Steff");
        libroexistente.orElse(null).setIdioma("Ingles");
        libroexistente.orElse(null).setFechaPublicacion(new Date());
        libroexistente.orElse(null).setDescripcion("Steff");
        libroexistente.orElse(null).setTipoPasta("Roja");
        libroexistente.orElse(null).setIsbn("ISBN");
        libroexistente.orElse(null).setNumEjemplares(2);
        libroexistente.orElse(null).setPortada("Mala");
        libroexistente.orElse(null).setPresentacion("Regular");
        libroexistente.orElse(null).setPrecio(30.0);
        libroexistente.orElse(null).setCategoria(categoria.orElse(null));
        libroexistente.orElse(null).setAutor(autor.orElse(null));

        libroRepository.save(libroexistente.orElse(null));
    }

    @Test
    public void delete(){
        if(libroRepository.existsById(1)){
            libroRepository.deleteById(1);
        }
    }

}
