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

    //一般新建只在当前目录下  还需考虑
    public FCB searchFolder(String name) {
        for (int i = 0; i < directoryTree.size(); i++) {
            if(directoryTree.get(i).getName() == name) {
                return directoryTree.get(i);
            }
        }
        return null;
    }

    //新建子目录
    public void addFolder(FCB parentNode, String name) {
        //todo:当前目录有重名的
        //
        FCB childNode = new FCB(name, FCB.Type.folder, parentNode);
        directoryTree.add(childNode);
    }

    //删除子目录
    public void deleteFolder(FCB fcb) {
        if (directoryTree.contains(fcb)) {
            while (fcb.getChild().size() != 0) {
                deleteFolder(fcb.getChild().get(0));
            }
            directoryTree.remove(fcb);
            fcb = null;
        }
    }

    //新建文件
    public void addFile(FCB parent, String name) {
        //todo:当前目录有重名的
        FCB fcb = new FCB(name,FCB.Type.document, parent);
        directoryTree.add(fcb);
    }

    //删除文件
    public void deleteFile(FCB fcb) {
        directoryTree.remove(fcb);
        fcb = null;
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

    public static void main(String[] args) {
        DirectoryTree d = new DirectoryTree();
        d.addFolder(root,"music");

    }
}
