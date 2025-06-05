package com.distribuida.dao;

import com.distribuida.model.Factura;
import com.distribuida.model.FacturaDetalle;
import com.distribuida.model.Libro;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(value = false)

public class FacturaDetallesRepositoryTestIntegracion {
    @Autowired
    private FacturaDetalleRepository facturaDetalleRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Test
    public void findAll(){
        List<FacturaDetalle> facturaDetalles = facturaDetalleRepository.findAll();
        for(FacturaDetalle item: facturaDetalles){
            System.out.println(item.toString());
        }
    }

    @Test
    public void findOne(){
        Optional<FacturaDetalle>facturaDetalle = facturaDetalleRepository.findById(1);
        System.out.println(facturaDetalle.orElse(null).toString());
    }

    @Test
    public void save(){
        FacturaDetalle facturaDetalle = new FacturaDetalle();
        Optional<Libro>libro = libroRepository.findById(1);
        Optional<Factura>factura = facturaRepository.findById(1);
        facturaDetalle.setCantidad(0);
        facturaDetalle.setCantidad(12);
        facturaDetalle.setSubtotal(12.0);
        facturaDetalle.setFactura(factura.orElse(null));
        facturaDetalle.setLibro(libro.orElse(null));

        facturaDetalleRepository.save(facturaDetalle);
    }

    @Test
    public void update(){
        Optional<FacturaDetalle>facturaDetalleExistente = facturaDetalleRepository.findById(210);
        Optional<Factura>factura = facturaRepository.findById(1);
        Optional<Libro>libro = libroRepository.findById(2);

        facturaDetalleExistente.orElse(null).setCantidad(12);
        facturaDetalleExistente.orElse(null).setSubtotal(12.0);
        facturaDetalleExistente.orElse(null).setFactura(factura.orElse(null));
        facturaDetalleExistente.orElse(null).setLibro(libro.orElse(null));

        facturaDetalleRepository.save(facturaDetalleExistente.orElse(null));
    }

    @Test
    public void delete(){
        if (facturaDetalleRepository.existsById(1)){
            facturaDetalleRepository.deleteById(1);
        }
    }



}
