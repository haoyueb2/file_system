package sample;

import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

//    ObservableList<Stage> stage = FXRobotHelper.getStages();
//    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("file.fxml")));
//    stage.get(0).setScene(scene);

    public static ArrayList<Stage> stages = new ArrayList<Stage>();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("system.fxml"));
        stages.add(primaryStage);
        primaryStage.setTitle("File System");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();

//        FXMLLoader loader = new FXMLLoader(getClass().getResource("file.fxml"));
//        FileController controller = loader.<FileController>getController();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
