package com.distribuida.dao;

import com.distribuida.model.Carrito;
import com.distribuida.model.Cliente;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(value = false)
public class CarritoRepositoryIntegrationTest {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void findAll() {
        List<Carrito> carritos = carritoRepository.findAll();
        for (Carrito carrito : carritos) {
            System.out.println(carrito.toString());
        }
    }

    @Test
    public void findOne() {
        Optional<Carrito> carrito = carritoRepository.findById(1L);
        System.out.println(carrito.orElse(null).toString());
    }

    @Test
    public void findByCliente() {
        Optional<Cliente> cliente = clienteRepository.findById(1);
        if (cliente.isPresent()) {
            Optional<Carrito> carrito = carritoRepository.findByCliente(cliente.get());
            System.out.println(carrito.orElse(null).toString());
        }
    }

    @Test
    public void findByToken() {
        Optional<Carrito> carrito = carritoRepository.findByToken("test-token");
        System.out.println(carrito.orElse(null).toString());
    }

    @Test
    public void save() {
        Carrito carrito = new Carrito();
        Optional<Cliente> cliente = clienteRepository.findById(1);

        carrito.setCliente(cliente.orElse(null));
        carrito.setToken("nuevo-token-" + System.currentTimeMillis());
        carrito.setSubtotal(BigDecimal.valueOf(0.00));
        carrito.setDescuento(BigDecimal.valueOf(0.0));
        carrito.setImpuestos(BigDecimal.valueOf(0.0));
        carrito.setTotal(BigDecimal.valueOf(0.0));

        carritoRepository.save(carrito);
        System.out.println("Carrito guardado: " + carrito.getIdCarrito());
    }

    @Test
    public void update() {
        Optional<Carrito> carritoExistente = carritoRepository.findById(1L);
        if (carritoExistente.isPresent()) {
            Carrito carrito = carritoExistente.get();
            carrito.setToken("token-actualizado");
            carrito.setSubtotal(BigDecimal.valueOf(100));
            carrito.setTotal(BigDecimal.valueOf(115)); // Con IVA

            carritoRepository.save(carrito);
            System.out.println("Carrito actualizado: " + carrito.toString());
        }
    }

    @Test
    public void delete() {
        if (carritoRepository.existsById(1L)) {
            carritoRepository.deleteById(1L);
            System.out.println("Carrito eliminado");
        }
    }
}