package com.distribuida.service;

import com.distribuida.dao.AutorRepository;
import com.distribuida.dao.CategoriaRepository;
import com.distribuida.dao.LibroRepository;
import com.distribuida.model.Autor;
import com.distribuida.model.Categoria;
import com.distribuida.model.Libro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LibroServicioTestUnitaria {

    @Mock
    private LibroRepository libroRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private LibroServiceImpl libroServices;
    private CategoriaServiceImpl categoriaServices;
    private AutorServiceImpl autorServices;

    private Categoria categoria;
    private Libro libro;
    private Autor autor;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        libro = new Libro(1,"Romeo","Santillan",12,"Segunda","Ingles",new Date(),"Largo","Amarrilla","ISBN",12,"Mala","Buena",12.22,categoria,autor);
        autor = new Autor(1,"Marco","Chupin","Colombia","Tumbaco","1800","test@gmail.com");
        categoria = new Categoria(1,"Drama","Amores");
    }

    @Test
    public void testFindAll(){
       when(libroRepository.findAll()).thenReturn(Arrays.asList(libro));
       List<Libro> libros = libroServices.findAll();
       assertNotNull(libros);
       assertEquals(1,libros.size());
       verify(libroRepository,times(1)).findAll();
    }

    @Test
    public void testFindOne(){
        when(libroRepository.findById(1)).thenReturn(java.util.Optional.of(libro));
        Libro libro = libroServices.findOne(1);
        assertNotNull(libro);
        assertEquals("Romeo",libro.getTitulo());
        verify(libroRepository,times(1)).findById(1);
    }

    @Test
    public void Save(){
        when(libroRepository.save(libro)).thenReturn(libro);
        Libro libro1 = libroServices.save(libro);
        assertNotNull(libro1);
        assertEquals("Romeo",libro1.getTitulo());
        verify(libroRepository,times(1)).save(libro);
    }

    @Test
    public void update(){
        Libro libroactualizado = new Libro(1,"Julieta","Castle",12,"Segunda","Ingles",new Date(),"Largo","Amarrilla","ISBN",12,"Mala","Buena",12.22,categoria,autor);
        when(libroRepository.findById(1)).thenReturn(Optional.of(libro));
        when(libroRepository.save(any(Libro.class))).thenReturn(libroactualizado);
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));
        when(autorRepository.findById(1)).thenReturn(Optional.of(autor));
        Libro libro1 = libroServices.update(1,1,1,libroactualizado);
        assertNotNull(libro1);
        assertEquals("Julieta", libro1.getTitulo());
        assertEquals("Castle", libro1.getEditorial());
        assertEquals(12, libro1.getNumPaginas());
        assertEquals("Segunda", libro1.getEdicion());
        assertEquals("Ingles",libro1.getIdioma());
        assertEquals("Largo", libro1.getDescripcion());
        assertEquals("Amarrilla", libro1.getTipoPasta());
        assertEquals("ISBN", libro1.getIsbn());
        assertEquals(12.22, libro1.getPrecio());
        assertEquals(1, libro1.getCategoria().getIdCategoria());
        assertEquals(1, libro1.getAutor().getIdAutor());
        verify(libroRepository).save(any(Libro.class));
        verify(categoriaRepository).findById(1);
        verify(autorRepository).findById(1);
    }

    @Test
    public void testDelete(){
        when(libroRepository.existsById(1)).thenReturn(false);
        libroServices.delete(1);
        verify(libroRepository,times(0)).deleteById(1);
    }

    @Test
    public void testFindOneNoExiste(){
        when(libroRepository.findById(2)).thenReturn(java.util.Optional.empty());
        Libro libro = libroServices.findOne(2);
    }


}
