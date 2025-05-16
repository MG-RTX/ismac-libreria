package org.distribuida.principal;

import org.distribuida.entities.Autor;

public class AutorPrincipal {

    public  static void main(String[] args){

        Autor autor = new Autor(1,"Luis","Diaz","Ecuador","Tababela","911","pepe@chupin");
        System.out.println(autor.toString());

    }



}
