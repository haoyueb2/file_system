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
        FCB FCB1 = new FCB("1",FCB.Type.folder, documentFCB);
        directoryTree.add(FCB1);
        FCB FCB2 = new FCB("2",FCB.Type.folder, documentFCB);
        directoryTree.add(FCB2);
        FCB FCB3 = new FCB("3",FCB.Type.folder, imagesFCB);
        directoryTree.add(FCB3);

        directoryTree.add(new FCB("haha.txt",FCB.Type.document, documentFCB));
        directoryTree.add(new FCB("xixi.txt",FCB.Type.document, documentFCB));

        FCB haha = new FCB("haha.png",FCB.Type.document, imagesFCB);
        directoryTree.add(haha);

//        System.out.println(haha.getSize());
//        System.out.println(haha.getCreateTime());
//        System.out.println(haha.getModifyTime());
    }



    //新建子目录
    public void addFolder(FCB parent, String name) {
        //当前目录有重名的
        //
        if(!isSameName(parent,name)) {
            FCB childNode = new FCB(name, FCB.Type.folder, parent);
            directoryTree.add(childNode);
        }
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
        //当前目录有重名的
        if(!isSameName(parent,name)) {
            FCB fcb = new FCB(name, FCB.Type.document, parent);
            directoryTree.add(fcb);
        }
    }

    //删除文件
    // TODO: 2017/6/20 block释放
    public void deleteFile(FCB fcb) {
        directoryTree.remove(fcb);
        fcb = null;
    }

    // TODO: 2017/6/20 重名文件和文件夹?
    //当前目录下是否有重名
    public boolean isSameName(FCB parent, String name) {
        for (int i = 0; i < parent.getChild().size(); i++) {
            if(parent.getChild().get(i).getName() == name)
                return true;
        }
        return false;
    }


    //获取路径
    public String getPath(FCB fcb) {
        String path = root.getName();
        Stack<String> stack = new Stack<String>();
        FCB currentNode = fcb;
        while(currentNode != this.root) {
            stack.push(currentNode.getName());
            currentNode = currentNode.getParent();
        }
        while(!stack.empty()) {
            path += " / " + stack.peek();
            stack.pop();
        }
        return path;
    }

    //通过名字找FCB
    public FCB getFCB(String name, String parent) {
        for (FCB fcb : directoryTree
             ) {
            if(fcb.getName() == name && fcb.getParent().getName() == parent) {
                return fcb;
            }
        }
        return null;
    }

    public FCB getFCB(String name) {
        for (int i = 0; i < directoryTree.size(); i++) {
            if(directoryTree.get(i).getName() == name) {
                return directoryTree.get(i);
            }
        }
        return null;
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
