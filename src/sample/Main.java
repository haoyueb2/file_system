package sample;

import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application {


    public static HashMap<FCB, Stage> stages = new HashMap<>();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("system.fxml"));
        stages.put(null,primaryStage);
        primaryStage.setTitle("File System");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.print("监听到窗口关闭");
            }
        });

    }


    public static void main(String[] args) {
        launch(args);
    }
}
