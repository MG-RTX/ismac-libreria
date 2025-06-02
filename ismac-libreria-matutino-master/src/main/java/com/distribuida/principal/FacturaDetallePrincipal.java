package com.distribuida.principal;

import com.distribuida.entities.Factura;
import com.distribuida.entities.FacturaDetalle;
import com.distribuida.entities.Libro;

public class FacturaDetallePrincipal {

    public static void main(String[] args) {
        FacturaDetalle facturaDetalle = new FacturaDetalle();
        Libro libro = new Libro();
        Factura factura = new Factura();


        facturaDetalle.setIdFacturaDetalle(1);
        facturaDetalle.setCantidad(12);
        facturaDetalle.setSubtotal(38.5);
        facturaDetalle.setLibro(libro);
        facturaDetalle.setFactura(factura);


        System.out.println(facturaDetalle.toString());
    }

}
