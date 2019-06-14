package sample;

//import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.application.Application;
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
import java.util.HashMap;

public class Main extends Application {


    public static HashMap<FCB, Stage> stages = new HashMap<>();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        stages.put(null,primaryStage);
        primaryStage.setTitle("File System");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            System.out.print("监听到窗口关闭");
            saveObjToFile(Controller.directoryTree);
            saveDiskToFile(Controller.disk);
        });

    }
    public void saveObjToFile(DirectoryTree directoryTree){
        try {
            //写对象流的对象
            ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("data.txt"));
            oos.writeObject(directoryTree);
            oos.close();                        //关闭文件流
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveDiskToFile(Disk disk){
        try {
            //写对象流的对象
            ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("disk.txt"));
            oos.writeObject(disk);
            oos.close();                        //关闭文件流
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
