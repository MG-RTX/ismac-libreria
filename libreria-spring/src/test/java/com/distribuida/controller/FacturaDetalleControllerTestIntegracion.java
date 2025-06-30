package com.distribuida.controller;

import com.distribuida.model.Factura;
import com.distribuida.model.FacturaDetalle;
import com.distribuida.model.Libro;
import com.distribuida.service.FacturaDetalleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

//import static java.lang.reflect.Array.get;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("removal")
@WebMvcTest(FacturaDetalleController.class)
public class FacturaDetalleControllerTestIntegracion {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacturaDetalleService facturaDetalleService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testFindAll() throws Exception {
        FacturaDetalle facturaDetalle = new FacturaDetalle(1,12,12.21,new Libro(),new Factura());

        Mockito.when(facturaDetalleService.findAll()).thenReturn(List.of(facturaDetalle));

        mockMvc.perform(get("/api/factura_detalle"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidad").value(12));
    }

    @Test
    public void testSave() throws Exception {
        FacturaDetalle facturaDetalle = new FacturaDetalle(1,12,12.21,new Libro(),new Factura());
        Mockito.when(facturaDetalleService.save(any(FacturaDetalle.class))).thenReturn(facturaDetalle);

        mockMvc.perform(post("/api/factura_detalle")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(facturaDetalle))
        )       .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidad").value(12));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/api/factura_detalle/2")).andExpect(status().isNoContent());
    }

}
