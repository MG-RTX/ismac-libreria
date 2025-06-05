package com.distribuida.service;

import com.distribuida.dao.AutorRepository;
import com.distribuida.model.Autor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.ListResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

}
