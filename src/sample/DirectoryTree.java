package sample;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.Stack;


public class DirectoryTree implements Serializable {

    private  FCB root = new FCB("root", FCB.Type.folder, null);
    public  ArrayList<FCB> directoryTree = new ArrayList<FCB>();



    DirectoryTree() {

        directoryTree.add(root);
        FCB documentFCB = new FCB("Documents",FCB.Type.folder, root);
        directoryTree.add(documentFCB);
        FCB imagesFCB = new FCB("Images",FCB.Type.folder, root);
        directoryTree.add(imagesFCB);
        FCB FCB1 = new FCB("A",FCB.Type.folder, documentFCB);
        directoryTree.add(FCB1);
        FCB FCB2 = new FCB("B",FCB.Type.folder, documentFCB);
        directoryTree.add(FCB2);
        FCB FCB3 = new FCB("C",FCB.Type.folder, imagesFCB);
        directoryTree.add(FCB3);

        FCB FCB4 = new FCB("D",FCB.Type.folder, imagesFCB);
        directoryTree.add(FCB4);

        directoryTree.add(new FCB("a",FCB.Type.document, documentFCB));
        directoryTree.add(new FCB("c",FCB.Type.document, documentFCB));

        FCB haha = new FCB("h",FCB.Type.document, imagesFCB);
        directoryTree.add(haha);
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
        }
        fcb.getParent().getChild().remove(fcb);
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
    public void deleteFile(FCB fcb) {
        directoryTree.remove(fcb);
        //remember to remove from parent
        fcb.getParent().getChild().remove(fcb);
    }

    //当前目录下是否有重名
    public boolean isSameName(FCB parent, String name) {
        for (int i = 0; i < parent.getChild().size(); i++) {
            FCB child = parent.getChild().get(i);
            System.out.println(child.getName());
            //System.out.println("fcb:"+(getFCB(name, parent.getName())).getName());

            if(child.getName().equals(name) && child.getName().equals(getFCB(name, parent.getName()))) {
                System.out.println(child.getName()+"is same");
                return true;
            }
        }
        return false;
    }


    //获取路径
    public String getPath(FCB fcb) {
//        System.out.println("getpathof:"+fcb.getName());
        String path = root.getName();
        Stack<String> stack = new Stack<String>();
        FCB currentNode = fcb;
        while(currentNode != this.root) {
//            System.out.println("currentNode:"+currentNode.getName());
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
        for (FCB fcb : directoryTree) {
            if(fcb.getParent()!= null && fcb.getName().equals(name) && fcb.getParent().getName().equals(parent)) {
                return fcb;
            }
        }
        return null;
    }
    public  FCB getRoot() {
        return root;
   }
}

