package com.distribuida.service;

import com.distribuida.dao.AutorRepository;
import com.distribuida.dao.CategoriaRepository;
import com.distribuida.dao.LibroRepository;
import com.distribuida.model.Autor;
import com.distribuida.model.Categoria;
import com.distribuida.model.Libro;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class LibroServiceImpl implements LibroService{

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Override
    public List<Libro> findAll(){
        return libroRepository.findAll();
    }

    @Override
    public Libro findOne(int id){
        Optional<Libro> libro = libroRepository.findById(id);
        return libro.orElse(null);
    }
    @Override
    public Libro save(Libro libro){
        return libroRepository.save(libro);
    }

    @Override
    public Libro update(int id,  int idCategoria, int idAutor, Libro libro){
        Libro libroExistente = findOne(id);

        Optional<Autor> autorExistente = autorRepository.findById(idAutor);
        Optional<Categoria> categoriaExistente = categoriaRepository.findById(idCategoria);

        if (libroExistente == null){
            return null;
        }

        libroExistente.setAutor(libro.getAutor());
        libroExistente.setCategoria(libro.getCategoria());
        libroExistente.setDescripcion(libro.getDescripcion());
        libroExistente.setEdicion(libro.getEdicion());
        libroExistente.setCategoria();

    }

}
