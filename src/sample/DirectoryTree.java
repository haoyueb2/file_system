package sample;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by keke on 2017/6/6.
 */
public class DirectoryTree {

    //根节点
    private final static FCB root = new FCB("File System", FCB.Type.folder, null);
    //最大高度
    private final static int MAX_HEIGHT = 10;
    //存放结点
    public static ArrayList<FCB> directoryTree = new ArrayList<FCB>();

    static {
        directoryTree.add(root);
        FCB documentFCB = new FCB("Documents",FCB.Type.folder, root);
        directoryTree.add(documentFCB);
        FCB imagesFCB = new FCB("Images",FCB.Type.folder, root);
        directoryTree.add(imagesFCB);

        directoryTree.add(new FCB("haha.txt",FCB.Type.document, documentFCB));
        directoryTree.add(new FCB("xixi.txt",FCB.Type.document, documentFCB));

        FCB haha = new FCB("haha.png",FCB.Type.document, imagesFCB);
        directoryTree.add(haha);

//        System.out.println(haha.getSize());
//        System.out.println(haha.getCreateTime());
//        System.out.println(haha.getModifyTime());
    }

    public void searchNode() {

    }

    //新建子目录
    public void addNode(FCB parentNode, String name) {
        FCB childNode = new FCB(name, FCB.Type.folder, parentNode);
        directoryTree.add(childNode);
    }

    //删除子目录
    public void deleteNode() {

    }

    //获取路径
    public String getPath(FCB fcb) {
        String path = root.getName();
        Stack<String> stack = new Stack<String>();
        FCB currentNode = fcb;
        stack.push(currentNode.getName());
        while(currentNode != this.root) {
            stack.push(currentNode.getName());
            currentNode = currentNode.getParent();
        }
        stack.push(root.getName());
        while(!stack.empty()) {
            path += " / " + stack.peek();
            stack.pop();
        }
        return path;
    }


    public static FCB getRoot() {
        return root;
    }

    public static ArrayList<FCB> getDirectoryTree() {
        return directoryTree;
    }
}
