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
import ojoaldato.modelo.Articulo;

import java.math.BigDecimal;
import java.util.List;

public class PedidosListaController {

    @FXML private TableView<Pedido> tablaPedidos;
    @FXML private TableColumn<Pedido, Integer> colNumPedido;
    @FXML private TableColumn<Pedido, String> colCliente;
    @FXML private TableColumn<Pedido, String> colArticulo;
    @FXML private TableColumn<Pedido, Integer> colCantidad;
    @FXML private TableColumn<Pedido, String> colFecha;
    @FXML private TableColumn<Pedido, String> colEstado;
    @FXML private TableColumn<Pedido, String> colTotal;

    private PedidoControlador pedidoControlador = new PedidoControlador();
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
        // Configurar columna de número de pedido
        colNumPedido.setCellValueFactory(new PropertyValueFactory<>("numPedido"));
        
        // Configurar columna de cliente (mostrar nombre y email)
        colCliente.setCellValueFactory(cellData -> {
            Cliente cliente = cellData.getValue().getCliente();
            return new javafx.beans.property.SimpleStringProperty(
                String.format("%s (%s)", 
                    cliente.getNombre(), 
                    cliente.getEmail())
            );
        });
        
        // Configurar columna de artículo
        colArticulo.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getArticulo().getDescripcion()
            )
        );
        
        // Configurar columna de cantidad
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        
        // Configurar columna de fecha
        colFecha.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getFechaHora().toString()
            )
        );
        
        // Configurar columna de estado
        colEstado.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getEnviado() ? "Enviado" : "Pendiente"
            )
        );
        
        // Configurar columna de total
        colTotal.setCellValueFactory(cellData -> {
            BigDecimal total = cellData.getValue().getPrecioTotal();
            return new javafx.beans.property.SimpleStringProperty(
                total != null ? String.format("%.2f €", total) : "0.00 €"
            );
        });
    }

    private void cargarPedidos() {
        try {
            // Obtener todos los pedidos del controlador
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
