package com.distribuida.service;

import com.distribuida.dao.AutorRepository;
import com.distribuida.dao.FacturaDetalleRepository;
import com.distribuida.dao.FacturaRepository;
import com.distribuida.dao.LibroRepository;
import com.distribuida.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FacturaDetalleServicioTestUnitaria {

    @Mock
    private FacturaDetalleRepository facturaDetalleRepository;

    @Mock
    private FacturaRepository facturaRepository;

    @Mock
    private LibroRepository libroRepository;

    @InjectMocks
    private FacturaDetalleServiceImpl facturaDetalleService;

    // Variables para los objetos de prueba
    private FacturaDetalle facturaDetalle;
    private Factura factura;
    private Libro libro;
    private Cliente cliente;
    private Categoria categoria;
    private Autor autor;

    @InjectMocks
    private LibroServiceImpl libroService;
    private FacturaServiceImpl facturaService;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        facturaDetalle = new FacturaDetalle(1,2,23.23,libro,factura);
        factura = new Factura(1,"FAC-001",new Date(),155.00,12.00,177.00,cliente);
        libro = new Libro(1,"Romeo","Santillan",12,"Limitada","Español",new Date(),"Larga","Roja","ISBN",12,"Buena","Mala",12.00,categoria,autor);
    }

    @Test
    public void testFindAll(){
        when(facturaDetalleRepository.findAll()).thenReturn(Arrays.asList(facturaDetalle));
        List<FacturaDetalle>facturaDetalles = facturaDetalleService.findAll();
        assertNotNull(facturaDetalles);
        assertEquals(1, facturaDetalles.size());
        verify(facturaDetalleRepository,times(1)).findAll();
    }

    @Test
    public void testFindOne(){
        when(facturaDetalleRepository.findById(1)).thenReturn(Optional.of(facturaDetalle));
        FacturaDetalle facturaDetalle = facturaDetalleService.findOne(1);
        assertNotNull(facturaDetalle);
        assertEquals(2, facturaDetalle.getCantidad());
        verify(facturaDetalleRepository,times(1)).findById(1);
    }

    @Test
    public void save(){
        when(facturaDetalleRepository.save(facturaDetalle)).thenReturn(facturaDetalle);
        FacturaDetalle facturaDetalle1 = facturaDetalleService.save(facturaDetalle);
        assertNotNull(facturaDetalle1);
        assertEquals(2, facturaDetalle1.getCantidad());
        verify(facturaDetalleRepository,times(1)).save(facturaDetalle);
    }

    @Test
    public void update(){
        FacturaDetalle facturaDetalleActualizada = new FacturaDetalle(1,1,12.22,libro,factura);
        when(facturaDetalleRepository.findById(1)).thenReturn(Optional.of(facturaDetalle));
        when(facturaDetalleRepository.save(any(FacturaDetalle.class))).thenReturn(facturaDetalleActualizada);
        when(facturaRepository.findById(1)).thenReturn(Optional.of(factura));
        when(libroRepository.findById(1)).thenReturn(Optional.of(libro));
        FacturaDetalle facturaDetalle1 = facturaDetalleService.update(1,1,1, facturaDetalleActualizada);
        assertNotNull(facturaDetalle1);
        assertEquals(1, facturaDetalle1.getCantidad());
        assertEquals(12.22, facturaDetalle1.getSubtotal());
        verify(facturaDetalleRepository).save(any(FacturaDetalle.class));
        verify(facturaRepository).findById(1);
        verify(libroRepository).findById(1);
    }
    @Test
    public void testDelete(){
        when(facturaDetalleRepository.existsById(1)).thenReturn(false);
        facturaDetalleService.delete(1);
        verify(facturaDetalleRepository,times(0)).deleteById(1);
    }





}