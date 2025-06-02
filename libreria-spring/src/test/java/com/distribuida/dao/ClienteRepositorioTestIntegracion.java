package com.distribuida.dao;

import com.distribuida.model.Cliente;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(value = false)
public class ClienteRepositorioTestIntegracion {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void findAll(){
        List<Cliente> clientes = clienteRepository.findAll();
        assertNotNull(clientes);
        assertTrue(clientes.size() > 0);
        for(Cliente item: clientes){
            System.out.println(item.toString());
        }
    }

    @Test
    public void findOne(){
        Optional<Cliente> cliente = clienteRepository.findById(1);
        assertTrue(cliente.isPresent(),"El cliente con id = 1, deberia existir");
        System.out.println(cliente.toString());
    }

    @Test
    public void save(){
        Cliente cliente = new Cliente(0,"123456789","Marcp","Calle 123","Apellido 1","123456789","sapo@gmail.com");
        clienteRepository.save(cliente);
        assertNotNull(cliente.getIdCliente(),"El cliente guardado debe tener un ID");
        assertEquals("123456789", cliente.getCedula());
        assertEquals("Marcp", cliente.getNombre() );
    }

    @Test
    public void update(){
        Optional<Cliente> cliente = clienteRepository.findById(35);

        assertTrue(cliente.isPresent(),"El cliente con ID=35 debe de existir para actualizarse");
        cliente.orElse(null).setCedula("123456789");
        cliente.orElse(null).setNombre("Pepe");
        cliente.orElse(null).setApellido("Chupin");
        cliente.orElse(null).setDireccion("Pifo");
        cliente.orElse(null).setTelefono("123456789");
        cliente.orElse(null).setCorreo("test@gmail.com");

       Cliente clienteactualizacdo =  clienteRepository.save(cliente.orElse(null));

        assertEquals("Pepe", clienteactualizacdo.getNombre());
        assertEquals("Chupin", clienteactualizacdo.getApellido());

    }

    @Test
    public void delete(){
        if(clienteRepository.existsById(45)){
            clienteRepository.deleteById(45);
        }
        assertFalse(clienteRepository.existsById(45));
    }
}