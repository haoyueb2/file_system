package sample;

import java.util.ArrayList;

/**
 * Created by keke on 2017/6/6.
 */
public class DirectoryTree {

    //根节点
    private final static DirectoryNode root = new DirectoryNode("File System",null);
    //最大高度
    private final static int MAX_HEIGHT = 10;
    //存放结点
    public static ArrayList<DirectoryNode> directoryTree = new ArrayList<DirectoryNode>();

    static {
        directoryTree.add(root);
        directoryTree.add(new DirectoryNode("Documents",root));
        directoryTree.add(new DirectoryNode("Images",root));
    }

    public void searchNode() {

    }

    //新建子目录
    public void addNode(DirectoryNode parentNode, String name) {
        DirectoryNode childNode = new DirectoryNode(name, parentNode);
        directoryTree.add(childNode);
    }

    //删除子目录
    public void deleteNode() {

    }

    public static DirectoryNode getRoot() {
        return root;
    }

    public static ArrayList<DirectoryNode> getDirectoryTree() {
        return directoryTree;
    }
}
