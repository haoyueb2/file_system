package sample;

//import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
                //saveObjToFile(SystemController.list);
            }
        });

    }
    public void saveObjToFile(ObservableList<FCBProperty> list){
        try {
            //写对象流的对象
            ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("data.dmg"));

            oos.writeObject(list);                 //将Person对象p写入到oos中

            oos.close();                        //关闭文件流
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
