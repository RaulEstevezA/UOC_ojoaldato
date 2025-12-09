package ojoaldato.vista;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import java.util.List;
import ojoaldato.controlador.ArticuloControlador;
import ojoaldato.controlador.ClienteControlador;
import ojoaldato.controlador.PedidoControlador;
import ojoaldato.modelo.Articulo;
import ojoaldato.modelo.Cliente;
import ojoaldato.modelo.Pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PedidoFormularioController {

    @FXML private ComboBox<Cliente> comboClientes;
    @FXML private ComboBox<Articulo> comboArticulos;
    @FXML private TextField txtCantidad;
    @FXML private ComboBox<String> comboEstado;

    private StackPane contenidoPedidos;
    private PedidoControlador pedidoControlador;
    private ClienteControlador clienteControlador;
    private ArticuloControlador articuloControlador;

    @FXML
    public void initialize() {
        // Inicializar controladores
        pedidoControlador = new PedidoControlador();
        clienteControlador = new ClienteControlador();
        articuloControlador = new ArticuloControlador();
        
        // Configurar combo de clientes
        cargarClientes();
        
        // Configurar combo de artículos
        cargarArticulos();
        
        // Configurar combo de estado
        comboEstado.getItems().addAll("Pendiente", "Enviado");
        comboEstado.getSelectionModel().selectFirst();
        
        // Validar que la cantidad sea numérica
        txtCantidad.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.matches("\\d*")) {
                txtCantidad.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private void cargarClientes() {
        try {
            ObservableList<Cliente> clientes = FXCollections.observableArrayList(
                clienteControlador.listarClientes()
            );
            comboClientes.setItems(clientes);
            
            // Configurar cómo se muestra cada cliente en el ComboBox
            comboClientes.setCellFactory(lv -> new javafx.scene.control.ListCell<Cliente>() {
                @Override
                protected void updateItem(Cliente item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? "" : item.getNombre() + " (" + item.getEmail() + ")");
                }
            });
            
            comboClientes.setButtonCell(new javafx.scene.control.ListCell<Cliente>() {
                @Override
                protected void updateItem(Cliente item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? "Seleccione un cliente" : item.getNombre() + " (" + item.getEmail() + ")");
                }
            });
            
        } catch (Exception e) {
            mostrarError("Error al cargar los clientes: " + e.getMessage());
        }
    }

    private void cargarArticulos() {
        try {
            ObservableList<Articulo> articulos = FXCollections.observableArrayList(
                articuloControlador.getAllArticulos()
            );
            comboArticulos.setItems(articulos);
            
            // Configurar cómo se muestra cada artículo en el ComboBox
            comboArticulos.setCellFactory(lv -> new javafx.scene.control.ListCell<Articulo>() {
                @Override
                protected void updateItem(Articulo item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText("");
                    } else {
                        setText(String.format("%s - %s (%.2f€)", 
                            item.getCodigo(), 
                            item.getDescripcion(), 
                            item.getPvp()));
                    }
                }
            });
            
            comboArticulos.setButtonCell(new javafx.scene.control.ListCell<Articulo>() {
                @Override
                protected void updateItem(Articulo item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? "Seleccione un artículo" : 
                        String.format("%s - %s", item.getCodigo(), item.getDescripcion()));
                }
            });
            
        } catch (Exception e) {
            mostrarError("Error al cargar los artículos: " + e.getMessage());
        }
    }

    @FXML
    private void guardarPedido() {
        try {
            // Validar campos obligatorios
            if (comboClientes.getSelectionModel().isEmpty()) {
                mostrarError("Debe seleccionar un cliente");
                return;
            }
            
            if (comboArticulos.getSelectionModel().isEmpty()) {
                mostrarError("Debe seleccionar un artículo");
                return;
            }
            
            if (txtCantidad.getText().trim().isEmpty()) {
                mostrarError("Debe especificar la cantidad");
                return;
            }
            
            // Obtener los valores del formulario
            Cliente cliente = comboClientes.getSelectionModel().getSelectedItem();
            Articulo articulo = comboArticulos.getSelectionModel().getSelectedItem();
            int cantidad;
            
            try {
                cantidad = Integer.parseInt(txtCantidad.getText().trim());
                if (cantidad <= 0) {
                    mostrarError("La cantidad debe ser mayor que cero");
                    return;
                }
            } catch (NumberFormatException e) {
                mostrarError("La cantidad debe ser un número válido");
                return;
            }
            
            // Crear el pedido
            Pedido pedido = new Pedido();
            pedido.setCliente(cliente);
            pedido.setArticulo(articulo);
            pedido.setCantidad(cantidad);
            pedido.setFechaHora(LocalDateTime.now());
            
            // Establecer el estado del pedido
            String estado = comboEstado.getSelectionModel().getSelectedItem();
            pedido.setEnviado("Enviado".equals(estado));
            if (pedido.getEnviado()) {
                pedido.setFechaEnvio(LocalDateTime.now());
            }
            
            // Calcular precios (esto podría moverse al modelo o al controlador de negocio)
            BigDecimal precioTotal = articulo.getPvp().multiply(new BigDecimal(cantidad));
            pedido.setPrecioTotal(precioTotal);
            
            // Guardar el pedido
            String resultado = pedidoControlador.addPedido(cliente, pedido);
            
            // Mostrar mensaje de éxito
            mostrarMensaje("Pedido guardado", resultado);
            
            // Limpiar el formulario
            limpiarFormulario();
            
        } catch (Exception e) {
            mostrarError("Error al guardar el pedido: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void cancelar() {
        if (contenidoPedidos != null) {
            contenidoPedidos.getChildren().clear();
            contenidoPedidos.getChildren().add(new javafx.scene.control.Label("Operación cancelada"));
        }
    }

    // Métodos auxiliares
    private void limpiarFormulario() {
        comboClientes.getSelectionModel().clearSelection();
        comboArticulos.getSelectionModel().clearSelection();
        txtCantidad.clear();
        comboEstado.getSelectionModel().selectFirst();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Getters y setters para la inyección de dependencias
    public void setContenidoPedidos(StackPane contenidoPedidos) {
        this.contenidoPedidos = contenidoPedidos;
    }

    public void setPedidoControlador(PedidoControlador pedidoControlador) {
        this.pedidoControlador = pedidoControlador;
    }

    public void setClienteControlador(ClienteControlador clienteControlador) {
        this.clienteControlador = clienteControlador;
    }

    public void setArticuloControlador(ArticuloControlador articuloControlador) {
        this.articuloControlador = articuloControlador;
    }
}
