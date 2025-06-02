package com.distribuida.dao;

import com.distribuida.model.Cliente;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class ClienteRepositorioTestIntegracion {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void findAll(){
        List<Cliente> clientes = clienteRepository.findAll();
        
        assertNotNull(clientes);
        clientes.forEach(System.out::println);
    }

    @Test
    public void findOne(){
        Optional<Cliente> cliente = clienteRepository.findById(1);


        assertNotNull(cliente);
        System.out.println(cliente);
    }

    @Test
    public void save(){
        Cliente cliente = new Cliente(0,"123456789","Marcp","Calle 123","Apellido 1","123456789","sapo@gmail.com");
        clienteRepository.save(cliente);
    }
    @Test
    public void update(){
        Optional<Cliente> cliente = clienteRepository.findById(35);
        cliente.orElse(null).setCedula("123456789");
        cliente.orElse(null).setNombre("Pepe");
        cliente.orElse(null).setApellido("Chupin");
        cliente.orElse(null).setDireccion("Pifo");
        cliente.orElse(null).setTelefono("123456789");
        cliente.orElse(null).setCorreo("test@gmail.com");

        clienteRepository.save(cliente.orElse(null));


    }
}