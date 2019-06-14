package sample;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;


public class Controller {

    public Pane root;
    public TableView<ListItem> tableView;
    public TableColumn<ListItem, ImageView> columnChoice;
    public TableColumn<ListItem, String> columnName;
    public TableColumn<ListItem, String> columnSize;
    public TableColumn<ListItem, String> columnTime;
    public TableColumn<ListItem, Button> columnOpen;
    public TableColumn<ListItem, Button> columnDelete;
    public TableColumn<ListItem, Button> columnDetail;
    public TextField currentPathText;
    public Button back;
    public Button makeDir;
    public Button makeFile;
    public Button format;
    public  Label detail;

    private static StringProperty path = new SimpleStringProperty();
    private static StringProperty detailText = new SimpleStringProperty();


    //编号
    private int newFileNumber = 0;
    private int newFOlderNumber = 0;


    public static FCB currentFCB = null;

    public static ObservableList<ListItem> list = FXCollections.observableArrayList();

    //目录树
    public static DirectoryTree directoryTree = new DirectoryTree();


    //当前目录
    public static FCB currentDirectory = directoryTree.getRoot();


    public static Disk disk = new Disk();

    public static DiskManager diskManager = new DiskManager(disk);

    public boolean file_not_found_flag = false;
    public boolean disk_not_found_flag = false;
    public void initialize() {
        //change current path
        path.addListener((observable, oldValue, newValue) -> currentPathText.setText(newValue));
        detailText.addListener((observable, oldValue, newValue) -> detail.setText(newValue));

        initTableView();
//        root.getScene().getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
//            @Override
//            public void handle(WindowEvent event) {
//                System.out.print("监听到窗口关闭");
//                //System.exit(0);
//            }
//        });


        DirectoryTree tmp = getObjFromFile();
        if(!file_not_found_flag) {
            directoryTree = tmp;
            currentDirectory = directoryTree.getRoot();
        }
        Disk tmp2 = getDiskFromFile();
        if(!disk_not_found_flag) {
            disk = tmp2;
            diskManager = new DiskManager(disk);
        }
        //需要格式化
        format.setOnAction(e-> {
            //此时不用用for循环，索引会变化会出问题
            while(directoryTree.getRoot().getChild().size() != 0) {
                FCB fcb = directoryTree.getRoot().getChild().get(0);
                if(fcb.getType() == FCB.Type.document) {
                    Controller.directoryTree.deleteFile(fcb);
                    Controller.diskManager.delete(fcb);
                    Controller.updateFileList();
                } else {
                    Controller.directoryTree.deleteFolder(fcb);
                    Controller.diskManager.delete(fcb);
                    Controller.updateFileList();
                }
            }

        });

        back.setOnAction(e-> {
            if(currentFCB!=null) {
                currentDirectory = currentFCB.getParent();
                updateCurrentPath();
                updateFileList();
                currentFCB = currentFCB.getParent();
            }
        });
        makeDir.setOnAction(e-> {
            directoryTree.addFolder(currentDirectory,"new folder " + newFOlderNumber++);
            updateFileList();

        });
        makeFile.setOnAction(e-> {
            directoryTree.addFile(currentDirectory,"new file " + newFileNumber++);
            updateFileList();
        });

        updateCurrentPath();
        updateFileList();
    }




    private void initTableView() {
        columnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        columnSize.setCellValueFactory(cellData -> cellData.getValue().sizeProperty());
        columnTime.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
        columnChoice.setCellValueFactory(cellData -> cellData.getValue().imageViewProperty());
        columnOpen.setCellValueFactory(cellData -> cellData.getValue().openButtonProperty());
        columnDelete.setCellValueFactory(cellData -> cellData.getValue().deleteButtonProperty());
        columnDetail.setCellValueFactory(cellData -> cellData.getValue().detailButtonProperty());
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
            list.add(new ListItem(fcb));
//            System.out.println(fcb.getName());
        }
        updateDetail("");
    }
    public DirectoryTree getObjFromFile(){
        try {
            ObjectInputStream ois=new ObjectInputStream(new FileInputStream("data.txt"));

            DirectoryTree test = (DirectoryTree)ois.readObject();              //读出对象

            return test;                                       //返回对象
        } catch (FileNotFoundException e) {
            file_not_found_flag = true;
//            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Disk getDiskFromFile(){
        try {
            ObjectInputStream ois=new ObjectInputStream(new FileInputStream("disk.txt"));

            Disk test = (Disk)ois.readObject();              //读出对象

            return test;                                       //返回对象
        } catch (FileNotFoundException e) {
            disk_not_found_flag = true;
//            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void updateDetail(String value) {
        detailText.setValue(value);
    }
}





