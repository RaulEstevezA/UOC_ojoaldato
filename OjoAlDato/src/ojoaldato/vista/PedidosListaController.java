package ojoaldato.vista;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import ojoaldato.controlador.PedidoControlador;
import ojoaldato.modelo.Pedido;
import ojoaldato.modelo.Cliente;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class PedidosListaController {

    @FXML private TableView<Pedido> tablaPedidos;
    @FXML private TableColumn<Pedido, Integer> colNumPedido;
    @FXML private TableColumn<Pedido, String> colCliente;
    @FXML private TableColumn<Pedido, String> colArticulo;
    @FXML private TableColumn<Pedido, Integer> colCantidad;
    @FXML private TableColumn<Pedido, String> colFecha;
    @FXML private TableColumn<Pedido, String> colEstado;
    @FXML private TableColumn<Pedido, String> colTotal;

    private final PedidoControlador pedidoControlador = new PedidoControlador();
    private StackPane contenidoPedidos;

    @FXML
    public void initialize() {
        configurarColumnas();
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

    public void cargarPedidos() {
        try {
            List<Pedido> pedidos = pedidoControlador.listarTodosPedidos();
            if (pedidos != null) {
                ObservableList<Pedido> datos = FXCollections.observableArrayList(pedidos);
                tablaPedidos.setItems(datos);
            } else {
                mostrarError("No se pudieron cargar los pedidos");
            }
        } catch (Exception e) {
            mostrarError("Error al cargar los pedidos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void cargarPedidosPendientes() {
        try {
            // Actualizar el estado de los pedidos pendientes
            pedidoControlador.actualizarPedidosPendientes();
            
            // Obtener la lista de pedidos pendientes actualizada
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
            // Asegurarse de que los pedidos estén actualizados
            pedidoControlador.actualizarPedidosPendientes();
            
            // Obtener la lista de pedidos enviados actualizada
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

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
