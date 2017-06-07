package sample;

import java.util.ArrayList;

/**
 * Created by keke on 2017/6/7.
 */
public class DirectoryNode {
    //名字
    private String name;
    //父目录
    private DirectoryNode parent;
    //子目录
    private ArrayList<DirectoryNode> child = new ArrayList<DirectoryNode>();


    public DirectoryNode(String name, DirectoryNode parent) {
        this.name = name;
        this.parent = parent;
        if(parent != null) {
            parent.addChild(this);
        }
    }

    //添加子目录
    public void addChild(DirectoryNode childNode) {
        child.add(childNode);
    }
    //删除子目录


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
