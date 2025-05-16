package org.distribuida.principal;


import org.distribuida.entities.Autor;
import org.distribuida.entities.Categoria;
import org.distribuida.entities.Libro;

import java.util.Date;

public class LibroPrincipal {

    public static void main(String[] args){

        Libro libro = new Libro();

        Categoria categoria = new Categoria(1,"Romance","Amores Prohibidos");
        Autor autor = new Autor(1,"Luis","Diaz","Ecuador","Tababela","911","pepe@chupin");

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
        libro.setCategoria(categoria);
        libro.setAutor(autor);


        System.out.println(libro.toString());



    }

}
