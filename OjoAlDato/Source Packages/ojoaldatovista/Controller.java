package ojoaldatovista;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        this.welcomeText.setText("Esto es una prueba - Grupo OjoAlDato");
    }
}
