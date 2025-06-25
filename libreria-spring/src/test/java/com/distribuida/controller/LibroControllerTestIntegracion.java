package com.distribuida.controller;

import com.distribuida.model.Autor;
import com.distribuida.model.Categoria;
import com.distribuida.model.Libro;
import com.distribuida.service.LibroService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;


//import static java.lang.reflect.Array.get;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("removal")
@WebMvcTest(LibroController.class)
public class LibroControllerTestIntegracion {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibroService libroService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFinAll() throws Exception {
        Libro libro = new Libro(1,"Fenomeno","PEPE",12,"Mala","Español",new Date(),"Corta","Roja","ISBN",5,"Mala","Buena",12.23,new Categoria(), new Autor());

        Mockito.when(libroService.findAll()).thenReturn(List.of(libro));

        mockMvc.perform(get("/api/libro"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Fenomeno"));
    }

    @Test
    public void testSave() throws Exception {
        Libro libro = new Libro(1,"Fenomeno","PEPE",12,"Mala","Español",new Date(),"Corta","Roja","ISBN",5,"Mala","Buena",12.23,new Categoria(), new Autor());

        Mockito.when(libroService.save(any(Libro.class))).thenReturn(libro);

        mockMvc.perform(post("/api/libro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(libro))
        )       .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Fenomeno"));

    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/api/libro/2")).andExpect(status().isNoContent());
    }
}
