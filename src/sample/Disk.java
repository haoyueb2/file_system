package sample;

import java.util.ArrayList;
import java.util.Stack;

import static sample.Disk.diskSize;

/**
 * Created by keke on 2017/6/7.
 */
public class Disk {

    //磁盘块数量
    public static final int diskBlockCount = 100;
    //每个磁盘块大小
    public static final int diskSize = 256;
    //全部存储
    public static ArrayList<DiskBlock> blockList = new ArrayList<DiskBlock>();


    static {
        for (int i = 0; i < diskBlockCount; i++) {
            blockList.add(new DiskBlock(i));
        }
    }

    //空块栈
    private Stack<Integer> freeDiskBlock = new Stack<Integer>();
    //已使用块栈
    private Stack<Integer> fullDiskBlock = new Stack<Integer>();

    public Disk() {
        this.initStack();
    }

    private void initStack() {
        for (int i = diskBlockCount - 1; i >= 0; i--) {
            freeDiskBlock.push(new Integer(i));
        }
    }
    //获取一个空闲块
    public int getFreeBlock() {
        if(!freeDiskBlock.empty()) {
            int index = freeDiskBlock.peek();
            fullDiskBlock.push(new Integer(index));
            freeDiskBlock.pop();
            return index;
        }
        return -1;
    }
    //释放掉一个块
    public void releaseBlock(int index) {
        freeDiskBlock.push(new Integer(index));
    }
    //格式化
    public void format() {
        this.initStack();
        fullDiskBlock.clear();
    }


}


class DiskBlock {
    //编号
    private int number;
    //存储
    private String [] content = new String[diskSize];

    public DiskBlock(int n) {
        this.number = n;
    }
}


//    // 位示图
//    // 1 : 已占用
//    // 0 : 可用
//    private int [] bitmap = new int[diskCount];

//    //找到第一个空位
//    public int getFisrtEmptyBlock() {
//        return getNextEmptyBlock(0);
//    }
//
//    //找到下一个空位
//    public int getNextEmptyBlock(int index) {
//        for (int i = index; i < diskCount; i++) {
//            if(bitmap[i] == 0) {
//                return i;
//            }
//        }
//        return -1;
//    }