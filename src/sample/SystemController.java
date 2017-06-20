package sample;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;


public class SystemController {


    @FXML private TreeView<String> treeView;

    @FXML private TableView<FCBProperty> tableView;
    @FXML private TableColumn<FCBProperty, CheckBox> columnChoice;
    @FXML private TableColumn<FCBProperty, String> columnName;
    @FXML private TableColumn<FCBProperty, String> columnSize;
    @FXML private TableColumn<FCBProperty, String> columnTime;

    @FXML private TextField currentPathText;

    private ObservableList<FCBProperty> list = FXCollections.observableArrayList();

    //根目录图标
    private final Node rootIcon = new ImageView(
            new Image(getClass().getResourceAsStream("/images/003-folder-6.png"))
    );

    //目录树
    private DirectoryTree directoryTree = new DirectoryTree();

    //当前目录
    private FCB currentDirectory = directoryTree.getRoot();

    private Disk disk;

    private DiskManager diskManager = new DiskManager(disk, directoryTree);

    private Image folderImage = new Image(getClass().getResourceAsStream("/images/004-folder-5.png"));

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
                System.out.println(i);
                return treeView.getTreeItem(i);
            }

        }
        return null;
    }

    public void initialize() {

        //--------------------------------Tree View-------------------------------------
        //初始化目录树
        TreeItem<String> rootItem = new TreeItem<String>(
                directoryTree.getRoot().getName(), rootIcon
        );
        //根目录展开
        rootItem.setExpanded(true);
        //添加子目录
//        for (int i = 1; i < directoryTree.getDirectoryTree().size(); i++) {
//            FCB current = directoryTree.getDirectoryTree().get(i);
//            TreeItem<String> parent = getParentItem(current);
//
//            if(current.getType() == FCB.Type.folder) {
//                TreeItem<String> item = new TreeItem<String> (current.getName(), new ImageView(folderImage));
//                if(parent == rootItem) {
//                    rootItem.getChildren().add(item);
//                } else if (parent == null){
//                    System.out.println("null");
//                } else {
//                    parent.getChildren().add(item);
//                }
//                item.setExpanded(true);
//            }
//        }
        addTree(rootItem);
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
                            updateCurrentPath();
                            for (FCB fcb : currentDirectory.getChild()
                                 ) {
                                list.clear();
                                list.add(new FCBProperty(fcb));
                            }
                        }
                    }
                });

        // TODO: 2017/6/17 treeview更新


        //--------------------------------Table View-------------------------------------





//        ObservableList<FCBProperty> list = FXCollections.observableArrayList();


        list.add(new FCBProperty(new FCB("1", FCB.Type.document,null)));
        list.add(new FCBProperty(new FCB("2", FCB.Type.document,null)));

        columnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        columnSize.setCellValueFactory(cellData -> cellData.getValue().sizeProperty());
        columnTime.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
        columnChoice.setCellValueFactory(cellData -> cellData.getValue().checkbox.getCheckBox());




//        for (int i = 0; i < directoryTree.getDirectoryTree().size(); i++) {
//            list.add(directoryTree.getDirectoryTree().get(i));
//        }
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i).getName());
//        }



        // FIXME: 2017/6/8 tableview加载不了数据, 可能因为Property? tableview fxid忘记设置了智障!
        tableView.setItems(list);


    }

    // TODO: 2017/6/17 checkbox动作现在绑定在button上
    @FXML
    private void check()
    {
        int selectCount = 0;
        ObservableList<FCBProperty> list=tableView.getItems();
        for(FCBProperty fcbProperty:list)
        {
            if(fcbProperty.checkbox.isSelected())
            {
                selectCount += 1;
                System.out.println(fcbProperty.getName());
            }
        }

    }

    @FXML
    private void open() {
//        FXMLLoader loader = FXMLLoader.load(getClass().getResource("file.fxml"));
//        Stage stage = new Stage();
//        stage.setTitle("File System");
//        stage.setScene(new Scene(root, 800, 500));
//        stage.show();
    }
    @FXML
    private void delete() {

    }
    @FXML
    private void showDetails() {

    }
    @FXML
    private void newFile() {

    }
    @FXML
    private void newFolder() {

    }
    @FXML
    private void sortByName() {
        Comparator<>
        list.sort();
    }
    @FXML
    private void sortBySize() {

    }
    @FXML
    private void sortByType() {

    }
    @FXML
    private void sortByModifyTime() {

    }
    @FXML
    private void help() {

    }
    @FXML
    private void about() {

    }

    //刷新路径
    private void updateCurrentPath() {
        String path = directoryTree.getPath(currentDirectory);
        currentPathText.setText(path);
    }
}



