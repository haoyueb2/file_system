package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by keke on 2017/6/2.
 */
public class FCB {

    //文件标识
    private int fileID;

    //文件名称
    public String name;
    //文件大小
    private int size;
//    //限制大小
//    private static final int MAX_SIZE = ;

    //文件类型
    public enum Type {
        folder,     //目录文件
        document    //数据文件
    }
    private Type type;

    //权限
    public enum Authority {
        readonly, //只读
        writable  //可写
    }
    private Authority authority;
//    //位置
//    private ArrayList<String> paths = new ArrayList<String>();

    //创建时间
    private String createTime;
    //最近修改时间
    private String modifyTime;

    // 时间格式
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //索引结构
    private int [] indexTable = new int [11];

//    //文件操作
//    private FileOperations fileOperations = new FileOperations(this);

    //父目录
    private FCB parent;
    //子目录
    private ArrayList<FCB> child = new ArrayList<FCB>();


    //构造函数
    public FCB(String name, Type type, FCB parentNode) {
        this.name = name;
        this.type = type;
        this.size = 0;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
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
