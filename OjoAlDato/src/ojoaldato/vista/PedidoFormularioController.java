package ojoaldato.vista;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
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

    private StackPane contenidoPedidos;

    private final PedidoControlador pedidoControlador = new PedidoControlador();
    private final ClienteControlador clienteControlador = new ClienteControlador();
    private final ArticuloControlador articuloControlador = new ArticuloControlador();

    // ======================
    // Inicialización
    // ======================
    @FXML
    public void initialize() {
        cargarClientes();
        cargarArticulos();
        validarCantidadNumerica();
    }

    // ======================
    // Carga de datos
    // ======================
    private void cargarClientes() {
        comboClientes.setItems(
                FXCollections.observableArrayList(clienteControlador.listarClientes())
        );

        comboClientes.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Cliente item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" :
                        item.getNombre() + " (" + item.getEmail() + ")");
            }
        });

        comboClientes.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Cliente item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "Seleccione un cliente" :
                        item.getNombre() + " (" + item.getEmail() + ")");
            }
        });
    }

    private void cargarArticulos() {
        comboArticulos.setItems(
                FXCollections.observableArrayList(articuloControlador.getAllArticulos())
        );

        comboArticulos.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Articulo item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" :
                        item.getCodigo() + " - " + item.getDescripcion() +
                                String.format(" (%.2f€)", item.getPvp()));
            }
        });

        comboArticulos.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Articulo item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "Seleccione un artículo" :
                        item.getCodigo() + " - " + item.getDescripcion());
            }
        });
    }

    private void validarCantidadNumerica() {
        txtCantidad.textProperty().addListener((obs, oldV, newV) -> {
            if (!newV.matches("\\d*")) {
                txtCantidad.setText(newV.replaceAll("[^\\d]", ""));
            }
        });
    }

    // ======================
    // Guardar pedido
    // ======================
    @FXML
    private void guardarPedido() {

        if (comboClientes.getSelectionModel().isEmpty()) {
            mostrarError("Debe seleccionar un cliente");
            return;
        }

        if (comboArticulos.getSelectionModel().isEmpty()) {
            mostrarError("Debe seleccionar un artículo");
            return;
        }

        if (txtCantidad.getText().isBlank()) {
            mostrarError("Debe indicar la cantidad");
            return;
        }

        int cantidad = Integer.parseInt(txtCantidad.getText());
        if (cantidad <= 0) {
            mostrarError("La cantidad debe ser mayor que cero");
            return;
        }

        Cliente cliente = comboClientes.getValue();
        Articulo articulo = comboArticulos.getValue();

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setArticulo(articulo);
        pedido.setCantidad(cantidad);
        pedido.setFechaHora(LocalDateTime.now());
        pedido.setEnviado(false);
        pedido.setPrecioTotal(
                articulo.getPvp().multiply(BigDecimal.valueOf(cantidad))
        );

        String resultado = pedidoControlador.addPedido(cliente, pedido);
        mostrarMensaje("Pedido guardado", resultado);
        limpiarFormulario();
    }

    // ======================
    // Cancelar
    // ======================
    @FXML
    private void cancelar() {
        if (contenidoPedidos != null) {
            contenidoPedidos.getChildren().clear();
            contenidoPedidos.getChildren().add(new Label("Operación cancelada"));
        }
    }

    // ======================
    // Utilidades
    // ======================
    private void limpiarFormulario() {
        comboClientes.getSelectionModel().clearSelection();
        comboArticulos.getSelectionModel().clearSelection();
        txtCantidad.clear();
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

    public void setContenidoPedidos(StackPane contenidoPedidos) {
        this.contenidoPedidos = contenidoPedidos;
    }
}