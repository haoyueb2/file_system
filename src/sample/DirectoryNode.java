package sample;

import java.util.ArrayList;

/**
 * Created by keke on 2017/6/7.
 */
public class DirectoryNode {
    //名字
    private String name;
    //父节点
    private DirectoryNode parent;
    //子节点
    private ArrayList<DirectoryNode> child = new ArrayList<DirectoryNode>();


    public DirectoryNode(String name, DirectoryNode parent) {
        this.name = name;
        this.parent = parent;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DirectoryNode getParent() {
        return parent;
    }

    public void setParent(DirectoryNode parent) {
        this.parent = parent;
    }

    public ArrayList<DirectoryNode> getChild() {
        return child;
    }

    public void setChild(ArrayList<DirectoryNode> child) {
        this.child = child;
    }
}
