package sample;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Comparator;


public class SystemController {


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


    public static int selectCount = 0;

    //编号
    private int newFileNumber = 0;
    private int newFOlderNumber = 0;

    public static FCB currentFCB = null;

    private static ObservableList<FCBProperty> list = FXCollections.observableArrayList();

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
        path.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                currentPathText.setText(newValue);
            }
        });

        initTreeView();
        initTableView();
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
    @FXML
    private void sortByName() {
        NameComparator nameComparator = new NameComparator();
        list.sort(nameComparator);
    }
    @FXML
    private void sortBySize() {
        SizeComparator sizeComparator = new SizeComparator();
        list.sort(sizeComparator);
    }

    @FXML
    private void sortByModifyTime() {
        TimeComparator timeComparator = new TimeComparator();
        list.sort(timeComparator);
    }


    private void addTree(TreeItem<String> parent) {
        String parentName = parent.getValue();
        FCB parentFCB = directoryTree.getFCB(parentName);
        ArrayList<FCB> child = parentFCB.getChild();
        for (int i = 0; i < child.size(); i++) {
            FCB current = parentFCB.getChild().get(i);
            if(current.getType() == FCB.Type.folder) {
                TreeItem<String> item = new TreeItem<String> (current.getName(), new ImageView(folderImage));
                parent.getChildren().add(item);
                item.setExpanded(true);
            }
        }
    }
    
    private TreeItem<String> getParentItem(FCB fcb) {
        FCB parentFCB = fcb.getParent();

        for (int i = 0; i < 10; i++) {
            if(treeView.getTreeItem(i).getValue() == parentFCB.getName()) {
                return treeView.getTreeItem(i);
            }

        }
        return null;
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
                new ChangeListener<TreeItem <String>>() {
                    @Override
                    public void changed(ObservableValue<? extends TreeItem<String>> observableValue,
                                        TreeItem<String> oldItem, TreeItem<String> newItem) {
                        //string
                        String clickedItemName = newItem.getValue();
                        //index
                        int clickedItemIndex = treeView.getSelectionModel().getSelectedIndex();

                        if(clickedItemIndex != 0) {
                            currentDirectory = directoryTree.getFCB(newItem.getValue(), newItem.getParent().getValue());
//                            System.out.println("currentDirectory:"+currentDirectory.getName());
                        } else {
                            currentDirectory = directoryTree.getRoot();
                        }
                        updateCurrentPath();
                        updateFileList();
                    }
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

//名字比较器
class NameComparator implements Comparator{

    @Override
    public int compare(Object o1, Object o2) {
        String i1 = ((FCBProperty)o1).getName();
        String i2 = ((FCBProperty)o2).getName();
        if (i1.compareTo(i2) >=  0){
            return 1;
        }
        if (i1.compareTo(i2) <  0){
            return -1;
        }
        return 0;
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

//大小比较器
class SizeComparator implements Comparator{

    @Override
    public int compare(Object o1, Object o2) {
        String i1 = ((FCBProperty)o1).getSize();
//        String i2 = Integer.valueOf(((FCBProperty)o2).getSize());
        String i2 = ((FCBProperty)o2).getSize();
        if (i1.compareTo(i2) >=  0){
            return 1;
        }
        if (i1.compareTo(i2) <  0){
            return -1;
        }
        return 0;
    }
}

//时间比较器
class TimeComparator implements Comparator{
    @Override
    public int compare(Object o1, Object o2) {
        String i1 = ((FCBProperty)o1).getTime();
        String i2 = ((FCBProperty)o2).getTime();
        if (i1.compareTo(i2) <  0){
            return 1;
        }
        if (i1.compareTo(i2) >=  0){
            return -1;
        }
        return 0;
    }
}




