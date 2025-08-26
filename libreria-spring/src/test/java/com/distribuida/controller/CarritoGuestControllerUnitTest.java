package com.distribuida.controller;

import com.distribuida.model.Carrito;
import com.distribuida.service.CarritoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarritoGuestControllerUnitTest {

    @Mock
    private CarritoService carritoService;

    @InjectMocks
    private CarritoGuestController carritoGuestController;

    private Carrito testCarrito;

    @BeforeEach
    void setUp() {
        testCarrito = new Carrito();
        testCarrito.setIdCarrito(1L);
        testCarrito.setToken("test-token");
        testCarrito.setSubtotal(BigDecimal.valueOf(100.0));
        testCarrito.setTotal(BigDecimal.valueOf(115.0));
    }

    @Test
    void testCreateOrGet_Success() {
        // Arrange
        when(carritoService.getOrCreatedByToken("test-token")).thenReturn(testCarrito);

        // Act
        ResponseEntity<Carrito> response = carritoGuestController.createOrGet("test-token");

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getIdCarrito());
        assertEquals("test-token", response.getBody().getToken());

        verify(carritoService, times(1)).getOrCreatedByToken("test-token");
    }

    @Test
    void testGet_Success() {
        // Arrange
        when(carritoService.getByToken("test-token")).thenReturn(testCarrito);

        // Act
        ResponseEntity<Carrito> response = carritoGuestController.get("test-token");

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getIdCarrito());

        verify(carritoService, times(1)).getByToken("test-token");
    }

    @Test
    void testAddItem_Success() {
        // Arrange
        Map<String, Integer> requestBody = new HashMap<>();
        requestBody.put("libroId", 1);
        requestBody.put("cantidad", 3);

        when(carritoService.addItem("test-token", 1, 3)).thenReturn(testCarrito);

        // Act
        ResponseEntity<Carrito> response = carritoGuestController.addItem("test-token", requestBody);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getIdCarrito());

        verify(carritoService, times(1)).addItem("test-token", 1, 3);
    }

    @Test
    void testAddItem_WithDefaultValues() {
        // Arrange
        Map<String, Integer> requestBody = new HashMap<>(); // Empty map
        requestBody.put("otherField", 999); // Different field

        when(carritoService.addItem("test-token", 0, 0)).thenReturn(testCarrito);

        // Act
        ResponseEntity<Carrito> response = carritoGuestController.addItem("test-token", requestBody);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(carritoService, times(1)).addItem("test-token", 0, 0);
    }

    @Test
    void testAddItem_WithNullBody() {
        // Arrange
        when(carritoService.addItem("test-token", 0, 0)).thenReturn(testCarrito);

        // Act
        ResponseEntity<Carrito> response = carritoGuestController.addItem("test-token", null);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(carritoService, times(1)).addItem("test-token", 0, 0);
    }

    @Test
    void testUpdateItemCantidad_Success() {
        // Arrange
        Map<String, Integer> requestBody = new HashMap<>();
        requestBody.put("cantidad", 5);

        when(carritoService.updateItemCantidad("test-token", 123L, 5)).thenReturn(testCarrito);

        // Act
        ResponseEntity<Carrito> response = carritoGuestController.update("test-token", 123L, requestBody);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getIdCarrito());

        verify(carritoService, times(1)).updateItemCantidad("test-token", 123L, 5);
    }

    @Test
    void testUpdateItemCantidad_WithDefaultValue() {
        // Arrange
        Map<String, Integer> requestBody = new HashMap<>(); // Empty map
        requestBody.put("otherField", 999); // Different field

        when(carritoService.updateItemCantidad("test-token", 123L, 0)).thenReturn(testCarrito);

        // Act
        ResponseEntity<Carrito> response = carritoGuestController.update("test-token", 123L, requestBody);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(carritoService, times(1)).updateItemCantidad("test-token", 123L, 0);
    }

    @Test
    void testUpdateItemCantidad_WithNullBody() {
        // Arrange
        when(carritoService.updateItemCantidad("test-token", 123L, 0)).thenReturn(testCarrito);

        // Act
        ResponseEntity<Carrito> response = carritoGuestController.update("test-token", 123L, null);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(carritoService, times(1)).updateItemCantidad("test-token", 123L, 0);
    }

    @Test
    void testRemoveItem_Success() {
        // Arrange
        doNothing().when(carritoService).removeItem("test-token", 123L);

        // Act
        ResponseEntity<Void> response = carritoGuestController.remove("test-token", 123L);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());

        verify(carritoService, times(1)).removeItem("test-token", 123L);
    }

    @Test
    void testClear_Success() {
        // Arrange
        doNothing().when(carritoService).clearByToken("test-token");

        // Act
        ResponseEntity<Void> response = carritoGuestController.clear("test-token");

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());

        verify(carritoService, times(1)).clearByToken("test-token");
    }

    @Test
    void testAddItem_ServiceThrowsException() {
        // Arrange
        Map<String, Integer> requestBody = new HashMap<>();
        requestBody.put("libroId", 999);
        requestBody.put("cantidad", 1);

        when(carritoService.addItem("test-token", 999, 1))
                .thenThrow(new IllegalArgumentException("Libro no encontrado"));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            carritoGuestController.addItem("test-token", requestBody);
        });

        verify(carritoService, times(1)).addItem("test-token", 999, 1);
    }

    @Test
    void testUpdateItemCantidad_ServiceThrowsException() {
        // Arrange
        Map<String, Integer> requestBody = new HashMap<>();
        requestBody.put("cantidad", 5);

        when(carritoService.updateItemCantidad("test-token", 999L, 5))
                .thenThrow(new IllegalArgumentException("Item no encontrado"));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            carritoGuestController.update("test-token", 999L, requestBody);
        });

        verify(carritoService, times(1)).updateItemCantidad("test-token", 999L, 5);
    }

    @Test
    void testRemoveItem_ServiceThrowsException() {
        // Arrange
        doThrow(new IllegalArgumentException("Item no encontrado"))
                .when(carritoService).removeItem("test-token", 999L);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            carritoGuestController.remove("test-token", 999L);
        });

        verify(carritoService, times(1)).removeItem("test-token", 999L);
    }

    @Test
    void testClear_ServiceThrowsException() {
        // Arrange
        doThrow(new IllegalArgumentException("Carrito no encontrado"))
                .when(carritoService).clearByToken("invalid-token");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            carritoGuestController.clear("invalid-token");
        });

        verify(carritoService, times(1)).clearByToken("invalid-token");
    }

    @Test
    void testCreateOrGet_ServiceThrowsException() {
        // Arrange
        when(carritoService.getOrCreatedByToken("invalid-token"))
                .thenThrow(new IllegalArgumentException("Token inválido"));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            carritoGuestController.createOrGet("invalid-token");
        });

        verify(carritoService, times(1)).getOrCreatedByToken("invalid-token");
    }

    @Test
    void testGet_ServiceThrowsException() {
        // Arrange
        when(carritoService.getByToken("invalid-token"))
                .thenThrow(new IllegalArgumentException("Token inválido"));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            carritoGuestController.get("invalid-token");
        });

        verify(carritoService, times(1)).getByToken("invalid-token");
    }
}