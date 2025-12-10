package ojoaldato.vista;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.fxml.Initializable;
import ojoaldato.controlador.PedidoControlador;
import ojoaldato.modelo.Pedido;
import ojoaldato.modelo.Cliente;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PedidosListaController implements Initializable {

    @FXML
    private TableView<Pedido> tablaPedidos;
    @FXML
    private Button btnCancelarPedido;
    @FXML private TableColumn<Pedido, Integer> colNumPedido;
    @FXML private TableColumn<Pedido, String> colCliente;
    @FXML private TableColumn<Pedido, String> colArticulo;
    @FXML private TableColumn<Pedido, Integer> colCantidad;
    @FXML private TableColumn<Pedido, String> colFecha;
    @FXML private TableColumn<Pedido, String> colEstado;
    @FXML private TableColumn<Pedido, String> colTotal;
    @FXML private TextField txtBuscarEmail;

    private final PedidoControlador pedidoControlador = new PedidoControlador();
    private StackPane contenidoPedidos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        // Inicialmente ocultar el botón de cancelar
        if (btnCancelarPedido != null) {
            btnCancelarPedido.setVisible(false);
        }
        cargarPedidos();
    }

    public void setContenidoPedidos(StackPane contenidoPedidos) {
        this.contenidoPedidos = contenidoPedidos;
    }

    private void configurarColumnas() {
        colNumPedido.setCellValueFactory(new PropertyValueFactory<>("numPedido"));
        
        colCliente.setCellValueFactory(cellData -> {
            Cliente cliente = cellData.getValue().getCliente();
            return new javafx.beans.property.SimpleStringProperty(
                String.format("%s (%s)", 
                    cliente.getNombre(), 
                    cliente.getEmail())
            );
        });
        
        colArticulo.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getArticulo().getDescripcion()
            )
        );
        
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        
        colFecha.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getFechaHora().toString()
            )
        );
        
        // Ocultar la columna de estado
        colEstado.setVisible(false);
        
        colTotal.setCellValueFactory(cellData -> {
            BigDecimal total = cellData.getValue().getPrecioTotal();
            return new javafx.beans.property.SimpleStringProperty(
                total != null ? String.format("%.2f €", total) : "0.00 €"
            );
        });
    }

    @FXML
    private void buscarPorEmail() {
        String email = txtBuscarEmail.getText().trim();
        if (email.isEmpty()) {
            mostrarError("Por favor, ingrese un email para buscar");
            return;
        }
        
        try {
            // Usar el método existente para buscar por email
            List<Pedido> pedidos = pedidoControlador.listarPedidosPorCliente(email);
            
            if (pedidos == null || pedidos.isEmpty()) {
                mostrarMensaje("No se encontraron pedidos para el email: " + email);
            } else {
                ObservableList<Pedido> datos = FXCollections.observableArrayList(pedidos);
                tablaPedidos.setItems(datos);
                // Mostrar el botón de cancelar solo si hay pedidos pendientes
                if (btnCancelarPedido != null) {
                    boolean tienePendientes = pedidos.stream()
                        .anyMatch(p -> !p.getEnviado());
                    btnCancelarPedido.setVisible(tienePendientes);
                }
            }
        } catch (Exception e) {
            mostrarError("Error al buscar pedidos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void cargarPedidos() {
        try {
            // Limpiar el campo de búsqueda
            if (txtBuscarEmail != null) {
                txtBuscarEmail.clear();
            }
            
            // Ocultar el botón de cancelar
            if (btnCancelarPedido != null) {
                btnCancelarPedido.setVisible(false);
            }
            
            List<Pedido> pedidos = pedidoControlador.listarTodosPedidos();
            if (pedidos != null && !pedidos.isEmpty()) {
                ObservableList<Pedido> datos = FXCollections.observableArrayList(pedidos);
                tablaPedidos.setItems(datos);
            } else {
                mostrarError("No se encontraron pedidos");
            }
        } catch (Exception e) {
            mostrarError("Error al cargar los pedidos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void cargarPedidosPendientes() {
        try {
            // Limpiar el campo de búsqueda
            if (txtBuscarEmail != null) {
                txtBuscarEmail.clear();
            }
            
            // Mostrar el botón de cancelar
            if (btnCancelarPedido != null) {
                btnCancelarPedido.setVisible(true);
            }
            
            // Obtener la lista de pedidos pendientes
            List<Pedido> pedidos = pedidoControlador.listarPedidosPendientes(null);
            
            // Actualizar la tabla
            ObservableList<Pedido> datos = FXCollections.observableArrayList(pedidos);
            tablaPedidos.setItems(datos);
        } catch (Exception e) {
            mostrarError("Error al cargar pedidos pendientes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void cargarPedidosEnviados() {
        try {
            // Limpiar el campo de búsqueda
            if (txtBuscarEmail != null) {
                txtBuscarEmail.clear();
            }
            
            // Ocultar el botón de cancelar
            if (btnCancelarPedido != null) {
                btnCancelarPedido.setVisible(false);
            }
            
            // Obtener la lista de pedidos enviados
            List<Pedido> pedidos = pedidoControlador.listarPedidosEnviados(null);
            
            // Actualizar la tabla
            ObservableList<Pedido> datos = FXCollections.observableArrayList(pedidos);
            tablaPedidos.setItems(datos);
        } catch (Exception e) {
            mostrarError("Error al cargar pedidos enviados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void volver() {
        if (contenidoPedidos != null) {
            contenidoPedidos.getChildren().clear();
        }
    }
    
    @FXML
    private void cancelarPedido() {
        Pedido pedidoSeleccionado = tablaPedidos.getSelectionModel().getSelectedItem();
        
        if (pedidoSeleccionado == null) {
            mostrarError("Por favor, seleccione un pedido para cancelar.");
            return;
        }
        
        if (pedidoSeleccionado.getEnviado()) {
            mostrarError("No se puede cancelar un pedido que ya ha sido enviado.");
            return;
        }
        
        try {
            // Eliminar el pedido
            String resultado = pedidoControlador.deletePedido(pedidoSeleccionado.getNumPedido());
            
            // Actualizar la vista
            if (resultado.contains("eliminado")) {
                // Recargar la vista actual
                if (tablaPedidos.getScene().lookup("#btnPendientes") != null) {
                    cargarPedidosPendientes();
                } else {
                    cargarPedidos();
                }
                mostrarMensaje("Pedido cancelado correctamente.");
            } else {
                mostrarError("No se pudo cancelar el pedido: " + resultado);
            }
        } catch (Exception e) {
            mostrarError("Error al cancelar el pedido: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
