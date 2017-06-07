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

    }

    public void searchNode() {

    }

    public void addNode() {

    }


}
