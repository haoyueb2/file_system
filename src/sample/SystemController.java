package sample;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;


public class SystemController {

    @FXML private Pane root;


    @FXML private TableView<FCBProperty> tableView;
    @FXML private TableColumn<FCBProperty, ImageView> columnChoice;
    @FXML private TableColumn<FCBProperty, String> columnName;
    @FXML private TableColumn<FCBProperty, String> columnSize;
    @FXML private TableColumn<FCBProperty, String> columnTime;
    @FXML private TableColumn<FCBProperty, Button> columnOpen;
    @FXML private TableColumn<FCBProperty, Button> columnDelete;

    @FXML private TextField currentPathText;

    @FXML private Button back;
    private static StringProperty path = new SimpleStringProperty();



    //编号
    private int newFileNumber = 0;
    private int newFOlderNumber = 0;

    public static FCB currentFCB = null;

    public static ObservableList<FCBProperty> list = FXCollections.observableArrayList();

    //目录树
    public static DirectoryTree directoryTree = new DirectoryTree();


    //当前目录
    public static FCB currentDirectory = directoryTree.getRoot();


    private static Disk disk = new Disk();

    public static DiskManager diskManager = new DiskManager(disk, directoryTree);



    public void initialize() {
        //change current path
        path.addListener((observable, oldValue, newValue) -> currentPathText.setText(newValue));


        initTableView();
//        root.getScene().getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
//            @Override
//            public void handle(WindowEvent event) {
//                System.out.print("监听到窗口关闭");
//                //System.exit(0);
//            }
//        });


        directoryTree = getObjFromFile();
        currentDirectory = directoryTree.getRoot();
        updateCurrentPath();
        updateFileList();
        back.setOnAction(e-> {
            currentDirectory = currentFCB.getParent();
            updateCurrentPath();
            updateFileList();
            currentFCB = currentFCB.getParent();
        });

    }

    @FXML
    private void newFile() {
        directoryTree.addFile(currentDirectory,"new file " + newFileNumber++);
        updateFileList();
    }
    @FXML
    private void newFolder() {
        directoryTree.addFolder(currentDirectory,"new folder " + newFOlderNumber++);
        updateFileList();
    }


    private void initTableView() {
        columnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        columnSize.setCellValueFactory(cellData -> cellData.getValue().sizeProperty());
        columnTime.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
        columnChoice.setCellValueFactory(cellData -> cellData.getValue().imageViewProperty());
        columnOpen.setCellValueFactory(cellData -> cellData.getValue().openButtonProperty());
        columnDelete.setCellValueFactory(cellData -> cellData.getValue().deleteButtonProperty());

        tableView.setItems(list);
    }


    //刷新路径
    public static void updateCurrentPath() {
        path.setValue(directoryTree.getPath(currentDirectory));
    }

    //更新文件列表
    public static void updateFileList() {
        list.clear();
//        System.out.println("result:");
        for (FCB fcb : currentDirectory.getChild()
                ) {
            list.add(new FCBProperty(fcb));
//            System.out.println(fcb.getName());
        }
    }
    public DirectoryTree getObjFromFile(){
        try {
            ObjectInputStream ois=new ObjectInputStream(new FileInputStream("data.dmg"));

            DirectoryTree test = (DirectoryTree)ois.readObject();              //读出对象

            return test;                                       //返回对象
        } catch (FileNotFoundException e) {
            //file_not_found_flag = true;
//            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

}





