package sample;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by keke on 2017/6/2.
 */
public class FCB {
    //文件名称
    private String name;
    //文件大小
    private int size;
    //限制大小
    private static final int MAX_SIZE = 64;
    //文件类型
    private enum Type {
        folder, //文件夹
        text,   //文本
        image   //图像
    }
    private Type type;
    //权限
    private enum Authority {
        readonly, //只读
        writable  //可写
    }
    private Authority authority;
//    //位置
//    private ArrayList<String> paths = new ArrayList<String>();
    //目录结点
    private DirectoryNode directoryNode;
    //创建时间
    private String createTime;
    //最近修改时间
    private String updateTime;


    // 时间格式
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    //索引结构
    private int [] indexTable = new int [11];

    //文件操作
    private FileOperations fileOperations = new FileOperations(this);

    public FCB(String name, Type type, DirectoryNode directoryNode) {
        this.name = name;
        this.type = type;
        this.directoryNode = directoryNode;

        //获取当前日期
        this.createTime = simpleDateFormat.format(new Date());

    }



}
