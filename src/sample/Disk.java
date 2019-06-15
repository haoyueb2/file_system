package sample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;


public class Disk implements Serializable {
    //每个磁盘块大小
    private int diskSize = 512;
    //磁盘块数量
    public  int diskBlockCount = 2000;
    //全部存储
    private  ArrayList<String> blockList = new ArrayList<>();
    //空块栈
    private  Stack<Integer> freeDiskBlock = new Stack<>();


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

    public ArrayList<String> getBlockList() {
        return blockList;
    }

    public int getDiskSize() {
        return diskSize;
    }

    public  void main(String[] args) {
        initStack();

    }

}





