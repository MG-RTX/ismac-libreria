package com.distribuida.service;

import com.distribuida.dao.FacturaDetalleRepository;
import com.distribuida.dao.FacturaRepository;
import com.distribuida.dao.LibroRepository;
import com.distribuida.model.Factura;
import com.distribuida.model.FacturaDetalle;
import com.distribuida.model.Libro;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class FacturaDetalleServiceImpl implements FacturaDetalleService {

    @Autowired
    private FacturaDetalleRepository facturaDetalleRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private LibroRepository libroRepository;


    @Override
    public List<FacturaDetalle> findAll() {
        return facturaDetalleRepository.findAll();
    }

    @Override
    public FacturaDetalle findOne(int id) {
        Optional<FacturaDetalle> facturaDetalle = facturaDetalleRepository.findById(id);
        return facturaDetalle.orElse(null);
    }

    @Override
    public FacturaDetalle save(FacturaDetalle facturaDetalle) {
        return facturaDetalleRepository.save(facturaDetalle);
    }

    @Override
    public FacturaDetalle update(int id, int idFactura, int idLibro, FacturaDetalle facturaDetalle) {
        FacturaDetalle facturaDetalleExistente = findOne(id);

        Optional<Factura> facturaExistente = facturaRepository.findById(idFactura);
        Optional<Libro>libroExistente= libroRepository.findById(idLibro);

        if(facturaDetalleExistente == null){
            return null;
        }

        facturaDetalleExistente.setCantidad(facturaDetalle.getCantidad());
        facturaDetalleExistente.setSubtotal(facturaDetalle.getSubtotal());
        facturaDetalleExistente.setFactura(facturaExistente.orElse(null));
        facturaDetalleExistente.setLibro(libroExistente.orElse(null));

        return facturaDetalleRepository.save(facturaDetalleExistente);


    }

    @Override
    public void delete(int id) {
        if (facturaDetalleRepository.existsById(id)){
            facturaDetalleRepository.deleteById(id);
        }

    }
}
