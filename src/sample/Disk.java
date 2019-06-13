package sample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;


public class Disk implements Serializable {
    //每个磁盘块大小
    private int diskSize = 256;

    //磁盘块数量
    public  int diskBlockCount = 100;

    //全部存储
    private  ArrayList<String> blockList = new ArrayList<>();


    //空块栈
    private  Stack<Integer> freeDiskBlock = new Stack<>();
    //已使用块栈
    private  Stack<Integer> fullDiskBlock = new Stack<Integer>();

    public Disk() {
        this.initStack();
        for (int i = 0; i < diskBlockCount; i++) {
            blockList.add(new String());
        }
    }

    private void initStack() {
        for (int i = diskBlockCount - 1; i >= 0; i--) {
            freeDiskBlock.push(i);
        }
    }
    //获取一个空闲块
    public  int getFreeBlock() {
        if(!freeDiskBlock.empty()) {
            int index = freeDiskBlock.peek();
            fullDiskBlock.push(index);
            freeDiskBlock.pop();
            return index;
        }
        return -1;
    }
    //释放掉一个块
    public void releaseBlock(int index) {
        freeDiskBlock.push(index);
        //clear

        blockList.set(index, "");
    }
    //格式化
    public void format() {
        this.initStack();
        fullDiskBlock.clear();
    }

    public ArrayList<String> getBlockList() {
        return blockList;
    }

    public Stack<Integer> getFreeDiskBlock() {
        return freeDiskBlock;
    }

    public Stack<Integer> getFullDiskBlock() {
        return fullDiskBlock;
    }

//    public int getDiskSize() {
//        return diskSize;
//    }
    public int getDiskSize() {
        return diskSize;
    }

    public  void main(String[] args) {
        initStack();
//        while(!freeDiskBlock.empty()) {
//            System.out.println(freeDiskBlock.peek());
//            freeDiskBlock.pop();
//        }
        for (int i = 0; i < 10; i++) {
            System.out.println("index:"+getFreeBlock());
            System.out.println("free:"+freeDiskBlock.peek());
            System.out.println("full:"+fullDiskBlock.peek());
        }
        for (int i = 0; i < 5; i++) {
            System.out.println("index:"+getFreeBlock());
            System.out.println("free:"+freeDiskBlock.peek());
            System.out.println("full:"+fullDiskBlock.peek());
        }


    }
//    class DiskBlock {
//
//        //编号
//        private int number;
//        //存储
//        private String [] content = new String[getDiskSize()];
//
//        public DiskBlock(int n) {
//            this.number = n;
//        }
//    }
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