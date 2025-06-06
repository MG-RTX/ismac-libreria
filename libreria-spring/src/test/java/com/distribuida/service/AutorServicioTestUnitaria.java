package com.distribuida.service;

import com.distribuida.dao.AutorRepository;
import com.distribuida.model.Autor;

import com.distribuida.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.ListResourceBundle;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AutorServicioTestUnitaria {

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private AutorServiceImpl autorService;

    private Autor autor;

    @BeforeEach
    public void setUp(){
        autor = new Autor();
        autor.setIdAutor(1);
        autor.setNombre("Xavi");
        autor.setApellido("Causa");
        autor.setPais("Peru");
        autor.setDireccion("Casa de Mama Charo");
        autor.setTelefono("Claro");
        autor.setCorreo("test@gmail.com");
    }

    @Test
    public void  testFindAll(){
        when(autorRepository.findAll()).thenReturn(List.of(autor));
        List<Autor> autors=autorService.findAll();
        assertNotNull(autors);
        assertEquals(1, autors.size());
        verify(autorRepository, times(1)).findAll();
    }

    @Test
    public void testFindOneExistente(){
        when(autorRepository.findById(1)).thenReturn(Optional.of(autor));
        Autor autor = autorService.findOne(1);
        assertNotNull(autor);
        assertEquals("Xavi", autor.getNombre());
    }

    @Test
    public void testFindOneNoExistente(){
        when(autorRepository.findById(2)).thenReturn(Optional.empty());
        Autor autor1 = autorService.findOne(2);
        assertNull(autor1);
    }

    @Test
    public void testSave(){
        when(autorRepository.save(autor)).thenReturn(autor);
        Autor autor1 = autorService.save(autor);
        assertNotNull(autor1);
        assertEquals("Xavi",autor1.getNombre());
    }

    @Test
    public void testUpdateExistente(){
        Autor autorActualizado = new Autor();

        autorActualizado.setNombre("Marco");
        autorActualizado.setApellido("Chupin");
        autorActualizado.setPais("Brasil");
        autorActualizado.setDireccion("Pifo");
        autorActualizado.setTelefono("9110");
        autorActualizado.setCorreo("test@gmai.com");

        when(autorRepository.findById(1)).thenReturn(Optional.of(autor));
        when(autorRepository.save(any())).thenReturn(autorActualizado);
        Autor autorResultado = autorService.update(1, autorActualizado);
        assertNotNull(autorResultado);
        assertEquals("Marco", autorResultado.getNombre());
        verify(autorRepository, times(1)).save(autor);
    }

    @Test
    public void testUpdateNoExistente(){
       Autor autor1Nuevo = new Autor();
        when(autorRepository.findById(2)).thenReturn(Optional.empty());
        Autor autorResultado = autorService.update(2, autor1Nuevo);
        assertNull(autorResultado);
        verify(autorRepository, never()).save(any());
    }

    @Test
    public void testDeleteExistente(){
        when(autorRepository.existsById(1)).thenReturn(true);
        autorService.delete(1);
        verify(autorRepository).deleteById(1);
    }

    @Test
    public void testDeleteNoExistente(){
        when(autorRepository.existsById(2)).thenReturn(false);
        autorService.delete(2);
        verify(autorRepository, never()).deleteById(anyInt());
    }









}
