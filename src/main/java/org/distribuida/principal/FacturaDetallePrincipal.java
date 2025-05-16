package org.distribuida.principal;

import org.distribuida.entities.Factura;
import org.distribuida.entities.FacturaDetalle;
import org.distribuida.entities.Libro;

import java.util.Date;

public class FacturaDetallePrincipal {

        public static void main(String[] args){

                FacturaDetalle facturaDetalle = new FacturaDetalle();

               Libro libro = new Libro();
                libro.setIdLibro(1);
                libro.setTitulo("Amores");
                libro.setEditorial("Castellana");
                libro.setNumPaginas(12);
                libro.setEdicion("Edicion");
                libro.setIdioma("Ingles");
                libro.setFechaPublicacion(new Date(12));
                libro.setDescripcion("Mano");
                libro.setTipoPasta("Amarilla");
                libro.setIsbn("ISBN");
                libro.setNumEjemplares(25);
                libro.setPortada("Roja");
                libro.setPresentacion("Mala");
                libro.setPrecio(1.1);


                facturaDetalle.setIdFacturaDetalle(2);
                facturaDetalle.setCantidad(2);
                facturaDetalle.setSubtotal(23.23);
                facturaDetalle.setLibro(libro);

                System.out.println(facturaDetalle.toString());

        }
}
