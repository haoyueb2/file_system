package sample;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by keke on 2017/6/2.
 */
public class File {
    //文件名称
    private String name;
    //文件大小
    private int size;
    //限制大小
    private static final int MAX_SIZE = 64;
    //文件类型
    private enum Type {
        text, //文本
        image //图像
    }
    //权限
    private enum Authority {
        readonly, //只读
        writable  //可写
    }
    //位置
    private ArrayList<String> paths = new ArrayList<String>();
    //创建时间
    private String createTime;
    //修改时间
    private String updateTime;
    //文件内容
    private String content;

    // 时间格式
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //日期
    private Date date = new Date();
    //现在的日期 String dateNowStr = format.format(date);

    public File() {

    }


    //打开
    public void Open() {

    }

    //关闭
    public void close() {

    }

    //读
    public void read() {

    }

    //写
    public void write() {

    }

    //保存
    public void save() {

    }

    //删除
    public void delete() {

    }

    //重命名
    public void rename() {

    }

    //文件详情
    public void displayDetails() {

    }
}
