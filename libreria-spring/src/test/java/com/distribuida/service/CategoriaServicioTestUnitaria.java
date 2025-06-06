package com.distribuida.service;

import com.distribuida.dao.CategoriaRepository;
import com.distribuida.model.Categoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoriaServicioTestUnitaria {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaServiceImpl categoriaService;
    private Categoria categoria;

    @BeforeEach
    public void setUp(){
        categoria = new Categoria();
        categoria.setIdCategoria(1);
        categoria.setCategoria("Drama");
        categoria.setDescripcion("Amores");
    }

    @Test
    public void testFindAll(){
        when(categoriaRepository.findAll()).thenReturn(List.of(categoria));
        List<Categoria>categorias=categoriaService.findAll();
        assertNotNull(categorias);
        assertEquals(1,categorias.size());
        verify(categoriaRepository, times(1)).findAll();
    }

    @Test
    public void testFindOneExistente(){
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));
        Categoria categoria1 = categoriaService.findOne(1);
        assertNotNull(categoria1);
        assertEquals("Drama",categoria1.getCategoria());

    }

    @Test
    public void testFindOneNoExistente(){
        when(categoriaRepository.findById(2)).thenReturn(Optional.of(categoria));
        Categoria categoria2 = categoriaService.findOne(2);
        assertNotNull(categoria2);
    }

    @Test
    public void testSave(){
        when(categoriaRepository.save(categoria)).thenReturn(categoria);
        Categoria categoria1 = categoriaService.save(categoria);
        assertNotNull(categoria1);
        assertEquals("Drama", categoria1.getCategoria());
    }

    @Test
    public void testUpdateExistente(){
        Categoria categoriaActualizado = new Categoria();
        categoriaActualizado.setCategoria("Comedia");
        categoriaActualizado.setDescripcion("Chistes");

        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));
        when(categoriaRepository.save(any())).thenReturn(categoriaActualizado);
        Categoria categoriaResultado = categoriaService.update(1, categoriaActualizado);
        assertNotNull(categoriaResultado);
        assertEquals("Comedia", categoriaResultado.getCategoria());
        verify(categoriaRepository, times(1)).save(categoria);
    }

    @Test
    public  void testUpdateNoExistente(){
        Categoria categoriaNuevo = new Categoria();
        when(categoriaRepository.findById(2)).thenReturn(Optional.empty());
        Categoria categoriaResultado = categoriaService.update(2,categoriaNuevo);
        assertNull(categoriaResultado);
        verify(categoriaRepository,never()).save(any());
    }

    @Test
    public void testDeleteExistente(){
        when(categoriaRepository.existsById(1)).thenReturn(true);
        categoriaService.delete(1);
        verify(categoriaRepository).deleteById(1);
    }

    @Test
    public void testDeleteNoExistente(){
        when(categoriaRepository.existsById(2)).thenReturn(false);
        categoriaService.delete(2);
        verify(categoriaRepository, never()).deleteById(anyInt());
    }





}
