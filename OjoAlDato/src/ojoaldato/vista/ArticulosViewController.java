package ojoaldato.vista;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

import ojoaldato.controlador.ArticuloControlador;
import ojoaldato.modelo.Articulo;

import java.math.BigDecimal;

public class ArticulosViewController {

    // ======================
    // SECCIONES DE LA VISTA
    // ======================
    @FXML private VBox menuBox;
    @FXML private VBox formularioBox;
    @FXML private VBox tablaBox;

    // ======================
    // CAMPOS DEL FORMULARIO
    // ======================
    @FXML private TextField txtCodigo;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtPvp;
    @FXML private TextField txtGastosEnvio;
    @FXML private TextField txtTiempoPreparacion;
    @FXML private TextField txtStock;

    // ======================
    // TABLA
    // ======================
    @FXML private TableView<Articulo> tablaArticulos;
    @FXML private TableColumn<Articulo, String> colCodigo;
    @FXML private TableColumn<Articulo, String> colDescripcion;
    @FXML private TableColumn<Articulo, BigDecimal> colPvp;
    @FXML private TableColumn<Articulo, BigDecimal> colGastosEnvio;
    @FXML private TableColumn<Articulo, Integer> colTiempoPreparacion;
    @FXML private TableColumn<Articulo, Integer> colStock;

    private final ArticuloControlador articuloControlador = new ArticuloControlador();
    private ObservableList<Articulo> listaObservable;


    // ======================
    // INICIALIZACIÓN
    // ======================
    @FXML
    public void initialize() {
        configurarColumnas();
        mostrarMenu();
    }

    private void configurarColumnas() {
        colCodigo.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getCodigo()));
        colDescripcion.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDescripcion()));
        colPvp.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getPvp()));
        colGastosEnvio.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getGastosEnvio()));
        colTiempoPreparacion.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getTiempoPreparacion()));
        colStock.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getStock()));
    }

    // ======================
    // MOSTRAR SECCIONES
    // ======================
    @FXML
    public void mostrarMenu() {
        menuBox.setVisible(true);
        menuBox.setManaged(true);

        formularioBox.setVisible(false);
        formularioBox.setManaged(false);

        tablaBox.setVisible(false);
        tablaBox.setManaged(false);

        limpiarFormulario();
    }

    @FXML
    public void mostrarFormulario() {
        menuBox.setVisible(false);
        menuBox.setManaged(false);

        formularioBox.setVisible(true);
        formularioBox.setManaged(true);

        tablaBox.setVisible(false);
        tablaBox.setManaged(false);
    }

    @FXML
    public void mostrarTabla() {
        cargarArticulos();

        menuBox.setVisible(false);
        menuBox.setManaged(false);

        formularioBox.setVisible(false);
        formularioBox.setManaged(false);

        tablaBox.setVisible(true);
        tablaBox.setManaged(true);
    }

    // ======================
    // CARGAR ARTÍCULOS
    // ======================
    private void cargarArticulos() {
        listaObservable = FXCollections.observableArrayList(articuloControlador.getAllArticulos());
        tablaArticulos.setItems(listaObservable);
    }

    // ======================
    // BOTÓN GUARDAR
    // ======================
    @FXML
    private void onAgregarArticulo() {
        try {
            Articulo articulo = new Articulo(
                    txtCodigo.getText(),
                    txtDescripcion.getText(),
                    new BigDecimal(txtPvp.getText()),
                    new BigDecimal(txtGastosEnvio.getText()),
                    Integer.parseInt(txtTiempoPreparacion.getText()),
                    Integer.parseInt(txtStock.getText())
            );

            String msj = articuloControlador.addArticulo(articulo);
            mostrarAlerta("Artículo añadido", "El artículo se ha registrado correctamente en el sistema.");

            limpiarFormulario();
            mostrarMenu();

        } catch (Exception e) {
            mostrarAlerta("Error", "Datos inválidos: " + e.getMessage());
        }
    }

    // ======================
    // BOTÓN CANCELAR
    // ======================
    @FXML
    private void onCancelar() {
        limpiarFormulario();
        mostrarMenu();
    }

    private void limpiarFormulario() {
        txtCodigo.clear();
        txtDescripcion.clear();
        txtPvp.clear();
        txtGastosEnvio.clear();
        txtTiempoPreparacion.clear();
        txtStock.clear();
    }

    // ======================
    // VENTANAS DE AVISO
    // ======================

    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(titulo);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}