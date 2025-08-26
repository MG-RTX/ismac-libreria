package com.distribuida.controller;

import com.distribuida.model.Carrito;
import com.distribuida.service.CarritoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarritoGuestController.class)
class CarritoGuestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CarritoService carritoService;

    private Carrito createTestCarrito() {
        Carrito carrito = new Carrito();
        carrito.setIdCarrito(1L);
        carrito.setToken("test-token");
        carrito.setSubtotal(BigDecimal.valueOf(100.0));
        carrito.setTotal(BigDecimal.valueOf(115.0));
        return carrito;
    }

    @Test
    void testCreateOrGet() throws Exception {
        Carrito carrito = createTestCarrito();
        when(carritoService.getOrCreatedByToken("test-token")).thenReturn(carrito);

        mockMvc.perform(post("/api/guest/cart")
                        .param("token", "test-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCarrito").value(1))
                .andExpect(jsonPath("$.token").value("test-token"));
    }

    @Test
    void testAddItem() throws Exception {
        Carrito carrito = createTestCarrito();
        Map<String, Integer> requestBody = new HashMap<>();
        requestBody.put("libroId", 1);
        requestBody.put("cantidad", 3);

        when(carritoService.addItem(eq("test-token"), eq(1), eq(3))).thenReturn(carrito);

        mockMvc.perform(post("/api/guest/cart/items")
                        .param("token", "test-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCarrito").value(1));
    }

    @Test
    void testUpdateItemCantidad() throws Exception {
        Carrito carrito = createTestCarrito();
        Map<String, Integer> requestBody = new HashMap<>();
        requestBody.put("cantidad", 5);

        when(carritoService.updateItemCantidad(eq("test-token"), eq(123L), eq(5))).thenReturn(carrito);

        mockMvc.perform(put("/api/guest/cart/items/123")
                        .param("token", "test-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCarrito").value(1));
    }

    @Test
    void testRemoveItem() throws Exception {
        doNothing().when(carritoService).removeItem("test-token", 123L);

        mockMvc.perform(delete("/api/guest/cart/items/123")
                        .param("token", "test-token"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testClear() throws Exception {
        doNothing().when(carritoService).clearByToken("test-token");

        mockMvc.perform(delete("/api/guest/cart/clear")
                        .param("token", "test-token"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetCarritoNotFound() throws Exception {
        // Simular que el servicio retorna un carrito vacío o null
        Carrito emptyCarrito = new Carrito();
        when(carritoService.getByToken("non-existent-token")).thenReturn(emptyCarrito);

        mockMvc.perform(get("/api/guest/cart")
                        .param("token", "non-existent-token"))
                .andExpect(status().isOk());
    }
}