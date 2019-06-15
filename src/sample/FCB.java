package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class FCB implements Serializable {
    private int fileID;
    public String name;
    private String size;
    public enum Type {
        folder,     //目录文件
        document    //数据文件
    }
    private Type type;
    private String createTime;
    private String modifyTime;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //索引表，指向物理地址
    private int [] indexTable = new int [2000];
    private FCB parent;
    private ArrayList<FCB> child = new ArrayList<FCB>();
    public enum Authority {
        readonly, //只读
        writable  //可写
    }
    private Authority authority;

    //构造函数
    public FCB(String name, Type type, FCB parentNode) {
        this.name = name;
        this.type = type;
        if(type == Type.document) {
            this.size = "0";
        } else {
            this.size = " ";
        }
        this.authority = Authority.writable;
        this.parent = parentNode;
        if(parent != null) {
            parent.addChild(this);
        }

        //获取当前日期
        this.createTime = simpleDateFormat.format(new Date());
        this.modifyTime = simpleDateFormat.format(new Date());

        //索引表全部项初始化为1
        Arrays.fill(indexTable, -1);
    }
    //文件详情
    public String displayDetails(FCB fcb) {
        String details = "";
        details += "Name : " + fcb.getName() + "\n";
        details += "Size : " + fcb.getSize() + "\n";
        details += "Type : " + fcb.getType() + "\n";
        details += "Create Time : " + fcb.getCreateTime() + "\n";
        details += "Modify Time : " + fcb.getModifyTime() + "\n";
        return details;
    }


    //添加子目录
    public void addChild(FCB childNode) {
        child.add(childNode);
    }
    //删除子目录

    //-----------------------------get & set---------------------------------------
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FCB getParent() {
        return parent;
    }

    public void setParent(FCB parent) {
        this.parent = parent;
    }

    public ArrayList<FCB> getChild() {
        return child;
    }

    public void setChild(ArrayList<FCB> child) {
        this.child = child;
    }

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime() {

        this.modifyTime = simpleDateFormat.format(new Date());;
    }

    public SimpleDateFormat getSimpleDateFormat() {
        return simpleDateFormat;
    }

    public void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
        this.simpleDateFormat = simpleDateFormat;
    }

    public int[] getIndexTable() {
        return indexTable;
    }

    public void setIndexTableI(int i, int x) {
        this.indexTable[i] = x;
    }

}
