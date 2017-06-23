package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


/**
 * Created by keke on 2017/6/23.
 */
public class Attention {

    @FXML private Button button;



    public void initialize() {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileController.attentionStages.get(0).close();
                FileController.attentionStages.remove(0);

            }
        });
    }



}
