package sample;

/**
 * Created by keke on 2017/6/7.
 */
public class DiskManager {

    private Disk disk;

    private DirectoryTree directoryTree;

    public DiskManager(Disk d, DirectoryTree directoryTree) {
        this.disk = d;
//        this.disk = ;
        this.directoryTree = directoryTree;
    }

    //打开
    public void Open(FCB fcb) {

    }

    //关闭 ~
    public void close(FCB fcb) {

    }

    //读
    public String read(FCB fcb) {
        int i = 0;
        String str = "";
        int index = fcb.getIndexTable()[i];
        while(index != -1) {
            str += disk.getBlockList().get(index);
            i++;
            index = fcb.getIndexTable()[i];
        }
        return str;
    }

    //写 ~
    // TODO: 2017/6/20 追加写?
    public void write(FCB fcb, String str) {
        int occupiedBlockCount =  blockCount(fcb);

        //todo:为什么getDiskSize()不对???
//        System.out.println("size:"+ Integer.toString(disk.getDiskSize()));

        System.out.println("size:" + disk.getDiskSize());
//        int needBlock = str.length() / disk.getDiskSize() + 1 ;
        int needBlock = str.length() / 256 + 1 ;
        for (int i = occupiedBlockCount; i < occupiedBlockCount + needBlock; i++) {
            // TODO: 2017/6/21 这里有问题
            fcb.setIndexTableI(i, disk.getFreeBlock());
//            fcb.setIndexTableI(i, 1);
        }
    }

    //文件已占用块数
    private int blockCount(FCB fcb) {
        int count = 0;
        int i = 0;
        while(fcb.getIndexTable()[i] != -1) {
            count++;
            i++;
        }
        return count;
    }



    //文件详情
    public String displayDetails(FCB fcb) {
        String details = "";
        details += "Name : " + fcb.getName() + "\n";
        details += "Size : " + fcb.getSize() + "\n";
        details += "Type : " + fcb.getType() + "\n";
        details += "Authority : " + fcb.getAuthority() + "\n";
        details += "Create Time : " + fcb.getCreateTime() + "\n";
        details += "Modify Time : " + fcb.getModifyTime() + "\n";
        return details;
    }


}
