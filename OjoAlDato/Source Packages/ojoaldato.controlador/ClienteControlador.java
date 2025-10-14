

// Clase NO terminada

package ojoaldato.controlador;

import ojoaldato.modelo.Cliente;
import java.util.ArrayList;
import java.util.List;


public class ClienteControlador {
    public List<Cliente> clientes = new ArrayList<>();

    public void agregarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public void eliminarCliente(Cliente cliente) {
        clientes.remove(cliente);  // Sería interesante añadir algún tipo de busqueda y coincidencia, para asegurarse de que existe.
    }

    public List<Cliente> listarClientes() {
        return clientes;
    }
}
