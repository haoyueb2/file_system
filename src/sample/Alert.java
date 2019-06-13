package sample;


import javafx.fxml.FXML;
import javafx.scene.control.Button;



public class Alert {

    @FXML private Button button;



    public void initialize() {
        button.setOnAction(event -> {
            FileController.attentionStages.get(0).close();
            FileController.attentionStages.remove(0);

        });
    }



}
