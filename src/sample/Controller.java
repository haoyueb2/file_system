package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Controller {


    @FXML private TreeView<String> treeView;

    //根目录图标
    private final Node rootIcon = new ImageView(
            new Image(getClass().getResourceAsStream("/images/003-folder-6.png"))
    );

    //目录树
    private DirectoryTree directoryTree = new DirectoryTree();

    //当前路径


    public void initialize() {

        //--------------------------------Tree View-------------------------------------
        //初始化目录树
        TreeItem<String> rootItem = new TreeItem<String>(
                directoryTree.getRoot().getName(), rootIcon
        );
        //根目录展开
        rootItem.setExpanded(true);
        //添加子目录
        for (int i = 1; i < directoryTree.getDirectoryTree().size(); i++) {
            if(directoryTree.getDirectoryTree().get(i).getType() == FCB.Type.folder) {
                TreeItem<String> item = new TreeItem<String> (
                        directoryTree.getDirectoryTree().get(i).getName(), new ImageView(
                        new Image(getClass().getResourceAsStream("/images/004-folder-5.png"))
                )
                );
                rootItem.getChildren().add(item);
                item.setExpanded(true);
            }
        }
        //TreeView添加根目录
        treeView.setRoot(rootItem);

        //--------------------------------Table View-------------------------------------


    }
}
