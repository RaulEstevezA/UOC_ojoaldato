

// Clase NO terminada

package ojoaldato.controlador; //Indica que esta clase pertenece al paquete (carpeta) controlador, dentro de nuestra estructura MVC.

// Importo Cliente (de nuestro modelo) y las clases List y ArrayList para manejar colecciones dinámicas de clientes.
import ojoaldato.modelo.Cliente;
import java.util.ArrayList;
import java.util.List;


public class ClienteControlador {
    public List<Cliente> clientes = new ArrayList<>(); // Aquí creo una lista donde guardaremos todos los clientes que maneja el programa.

    public Cliente buscarPorNif(String nif) {  // He configurado este metodo para poder buscar si ya existe el CLiente o no. Si existe devolvera el Cliente y si no Null.
        for (Cliente c : clientes) {
            if (c.getNif().equalsIgnoreCase(nif)) {
                return c;
            }
        }
        return null;
    }

    public boolean agregarCliente(Cliente cliente) {  // He puesto en boolean para así después en la Vista poder configurar más fácil.
        if (buscarPorNif(cliente.getNif()) != null) {
            clientes.add(cliente);
            return true;
        } else  {
            return false;
        }
    }

    // ME HE QUEDADO POR AQUÍ

    public void eliminarCliente(Cliente cliente) {
        clientes.remove(cliente);  // Sería interesante añadir algún tipo de busqueda y coincidencia, para asegurarse de que existe.
    }

    public List<Cliente> listarClientes() {
        return clientes;
    }
}
