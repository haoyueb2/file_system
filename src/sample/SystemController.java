package sample;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;


public class SystemController {

    @FXML private Pane root;
    @FXML private TreeView<String> treeView;

    @FXML private TableView<FCBProperty> tableView;
    @FXML private TableColumn<FCBProperty, ImageView> columnChoice;
    @FXML private TableColumn<FCBProperty, String> columnName;
    @FXML private TableColumn<FCBProperty, String> columnSize;
    @FXML private TableColumn<FCBProperty, String> columnTime;
    @FXML private TableColumn<FCBProperty, Button> columnOpen;
    @FXML private TableColumn<FCBProperty, Button> columnDelete;

    @FXML private TextField currentPathText;

    private static StringProperty path = new SimpleStringProperty();



    //编号
    private int newFileNumber = 0;
    private int newFOlderNumber = 0;

    public static FCB currentFCB = null;

    public static ObservableList<FCBProperty> list = FXCollections.observableArrayList();

    //根目录图标
    private final Node rootIcon = new ImageView(
            new Image(getClass().getResourceAsStream("/images/003-folder-6.png"))
    );

    //目录树
    public static DirectoryTree directoryTree = new DirectoryTree();


    //当前目录
    public static FCB currentDirectory = directoryTree.getRoot();


    private static Disk disk = new Disk();

    public static DiskManager diskManager = new DiskManager(disk, directoryTree);

    private Image folderImage = new Image(getClass().getResourceAsStream("/images/004-folder-5.png"));
    //
    private ArrayList<String> allPath = new ArrayList<>();

    public void initialize() {
        //change current path
        path.addListener((observable, oldValue, newValue) -> currentPathText.setText(newValue));

        initTreeView();
        initTableView();
//        root.getScene().getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
//            @Override
//            public void handle(WindowEvent event) {
//                System.out.print("监听到窗口关闭");
//                //System.exit(0);
//            }
//        });
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



    public void getAllPath() {
        for (FCB fcb : directoryTree.getDirectoryTree()
                ) {
            if(fcb != directoryTree.getRoot() && fcb.getType() == FCB.Type.folder) {
                allPath.add(directoryTree.getPath2(fcb));
            }
        }
        allPath.sort(new PathComparator());
    }

    private TreeItem<String> getOrCreateChild(TreeItem<String> parent, String value) {

        for (TreeItem<String> child : parent.getChildren()) {
            if (value.equals(child.getValue())) {
                return child ;
            }
        }

        TreeItem<String> newChild = new TreeItem<>(value, new ImageView(folderImage));
        parent.getChildren().add(newChild);
        newChild.setExpanded(true);
        return newChild ;
    }

    //初始化 TreeView
    private void initTreeView() {
        getAllPath();
        //初始化目录树
        TreeItem<String> rootItem = new TreeItem<String>(
                directoryTree.getRoot().getName(), rootIcon
        );
        //根目录展开
        rootItem.setExpanded(true);

        for (String path : allPath) {
            TreeItem<String> current = rootItem ;
            for (String component : path.split("\\.")) {
                current = getOrCreateChild(current, component);
            }
        }

//        addTree(rootItem);
        //TreeView添加根目录
        treeView.setRoot(rootItem);
        //监听
        treeView.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldItem, newItem) -> {
                    //string
                    String clickedItemName = newItem.getValue();
                    //index
                    int clickedItemIndex = treeView.getSelectionModel().getSelectedIndex();

                    if (clickedItemIndex != 0) {
                        currentDirectory = directoryTree.getFCB(newItem.getValue(), newItem.getParent().getValue());
//                            System.out.println("currentDirectory:"+currentDirectory.getName());
                    } else {
                        currentDirectory = directoryTree.getRoot();
                    }
                    updateCurrentPath();
                    updateFileList();
                });
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


}



//path比较器
class PathComparator implements Comparator{

    @Override
    public int compare(Object o1, Object o2) {
        String i1 = (String)o1;
        String i2 = (String)o2;
        if (i1.compareTo(i2) >=  0){
            return 1;
        }
        if (i1.compareTo(i2) <  0){
            return -1;
        }
        return 0;
    }
}





