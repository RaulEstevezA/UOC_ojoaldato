package ojoaldato.controlador;

import ojoaldato.modelo.Cliente;
import ojoaldato.modelo.Datos;
import ojoaldato.modelo.Pedido;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 * Controlador responsable de la lógica relacionada con clientes.
 * No realiza E/S ni persistencia directa; usa la clase Datos para almacenar/recuperar.
 */

public class ClienteControlador {
    private Datos datos;
    // Lista donde se almacenan los clientes en memoria

    // Constructor: el controlador necesita el modelo para trabajar
    public ClienteControlador(Datos datos) {
        this.datos = datos;
    }
    /**
     * Agrega un nuevo cliente si no existe otro con el mismo email.
     * @param cliente Cliente que se desea agregar
     * @return true si se agregó correctamente, false si ya existía
     */

    public boolean agregarCliente(Cliente cliente) {
        // Llama al metodo del modelo
        if (datos.buscarCliente(cliente.getEmail()) == null) {
            datos.agregarCliente(cliente);
            return true;
        } else {
            return false; // Ya existe un cliente con ese email
        }
    }

    public boolean eliminarCliente(String email) {
        Cliente cliente = datos.buscarCliente(email);
        if (cliente != null) {
            datos.eliminarCliente(cliente);
            return true;
        } else {
            return false;
        }
    }

