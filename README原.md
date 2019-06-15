# File-System


## 1. 项目内容

在内存中开辟一个空间作为文件存储器，在其上实现一个简单的文件系统。

可以实现的操作有：文件的新建、打开、删除、保存、读、写、排序、重命名、修改操作权限，子目录的创建、打开、删除，显示当前路径等。

## 2. 开发环境

JDK 1.8.0

## 3. 设计方案

#### 3.1 物理结构

采用索引结构，支持随机存取和文件的动态增减。每个文件拥有自己的索引表，索引表中存储磁盘块号。

#### 3.2 目录结构

**FCB**中的属性有文件名称、文件大小、文件类型（目录文件 / 数据文件 ）、使用权限（只读 / 可写）、创建时间、最近修改时间、索引、父目录、子目录。

##### 3.2.1 树形目录

FCB以树形结构连接，支持不同目录下的文件重名。

#### 3.3 空闲空间管理

用两个栈来管理空闲空间，空磁盘块栈和已使用磁盘块栈，文件申请空间时空磁盘块栈出栈并压入已使用磁盘块栈，释放空间时回收的磁盘块压入空磁盘块栈。

#### 3.4 系统打开文件表

记录文件共享计数。

#### 3.5 文件操作

##### 3.5.1 新建

新建FCB，添加进目录树。

##### 3.5.2 删除

文件：从目录树中移除，占用的磁盘块释放，删除FCB。

子目录：目录下所有文件删除，从目录树移除，删除FCB。

##### 3.5.3 打开

从目录树中检索要打开文件的FCB，通过索引表找到占用的磁盘块，共享计数加一。


##### 3.5.4 保存

修改的文件信息在FCB中更新，写入的内容存入磁盘块中。

##### 3.5.5 读

通过索引表找到占用的磁盘块，读入文件内容。

##### 3.5.6 写

申请空磁盘块，加入文件索引表，将写的内容存入相应磁盘块。

##### 3.5.7 重命名

检查当前目录下是否有重名文件，若没有，更改文件名，否则不更改。



## 4. 代码结构

### 4.1 文件目录树

```
	sample   
	├── Attention.java
    	├── DirectoryTree.java
    	├── Disk.java
    	├── DiskManager.java
    	├── FCB.java
    	├── FCBProperty.java
    	├── FileController.java
    	├── Main.java
    	├── SystemController.java
    	├── attention.fxml
    	├── file.fxml
    	└── system.fxml
```

### 4.2 详细说明

以下所有成员变量的get & set方法均不记入：

#### 4.2.1 Attention.java

**class Attention**：attention.fxml的controller。

- 成员变量

  | 变量                          | 描述   |
  | --------------------------- | ---- |
  | @FXML private Button button | 确认按钮 |



- 成员函数

  | 函数                       | 描述    |
  | ------------------------ | ----- |
  | public void initialize() | 初始化函数 |

#### 4.2.2 DirectoryTree.java

**class DirectoryTree**：目录树。

- 成员变量

  | 变量                                       | 描述   |
  | ---------------------------------------- | ---- |
  | private final static FCB root            | 根节点  |
  | public static ArrayList\<FCB> directoryTree | 存放结点 |

- 成员函数

  | 函数                                       | 描述         |
  | ---------------------------------------- | ---------- |
  | public void addFolder(FCB parent, String name) | 新建子目录      |
  | public void deleteFolder(FCB fcb)        | 删除子目录      |
  | public void addFile(FCB parent, String name) | 新建文件       |
  | public void deleteFile(FCB fcb）          | 删除文件       |
  | public boolean isSameName(FCB parent, String name) | 当前目录下是否有重名 |
  | public String getPath(FCB fcb)           | 获取路径       |
  | public String getPath2(FCB fcb)          | 获取路径       |
  | public FCB getFCB(String name, String parent) | 通过名字找*FCB* |



#### 4.2.3 Disk.java

**class Disk**：磁盘。

- 成员变量

  | 变量                                       | 描述      |
  | ---------------------------------------- | ------- |
  | private int diskSize                     | 每个磁盘块大小 |
  | public static int diskBlockCount         | 磁盘块数量   |
  | private  ArrayList\<String> blockList    | 磁盘块全部存储 |
  | private static Stack\<Integer> freeDiskBlock | 空块栈     |
  | private static Stack\<Integer> fullDiskBlock | 已使用块栈   |

- 成员函数

  | 函数                                  | 描述      |
  | ----------------------------------- | ------- |
  | private static void initStack()     | 初始化栈    |
  | public static int getFreeBlock()    | 获取一个空闲块 |
  | public void releaseBlock(int index) | 释放掉一个块  |
  | public void format()                | 格式化     |

#### 4.2.4 DiskManager.java

**class DiskManager**：磁盘上的一系列操作。

- 成员变量

  | 变量                                       | 描述       |
  | ---------------------------------------- | -------- |
  | private Disk disk                        | 要管理的磁盘对象 |
  | private DirectoryTree directoryTree      | 目录树      |
  | public static HashMap\<FCB, Integer> openFileTable | 系统打开文件表  |
  | private int diskSize                     | 每个磁盘块大小  |


- 成员函数

  | 函数                                     | 描述            |
  | -------------------------------------- | ------------- |
  | public void delete(FCB fcb)            | 删除文件，释放占用的磁盘块 |
  | public String read(FCB fcb)            | 读文件           |
  | public void write(FCB fcb, String str) | 写文件           |
  | private int blockCount(FCB fcb)        | 返回文件已占用磁盘块数   |
  | public String displayDetails(FCB fcb)  | 展示文件详情        |

#### 4.2.5 FCB.java

**class FCB**：文件控制块。

- 成员变量

  | 变量                                       | 描述     |
  | ---------------------------------------- | ------ |
  | public String name                       | 文件名称   |
  | private String size                      | 文件大小   |
  | public enum Type {    folder,   document   } | 枚举文件类型 |
  | private Type type                        | 文件类型   |
  | public enum Authority {    readonly, writable} | 枚举文件权限 |
  | private Authority authority              | 文件权限   |
  | private String createTime                | 创建时间   |
  | private String modifyTime                | 最后修改时间 |
  | private SimpleDateFormat simpleDateFormat | 时间格式   |
  | private int [] indexTable                | 索引表    |
  | private FCB parent                       | 父目录    |
  | private ArrayList\<FCB> child            | 子目录列表  |

- 成员函数

  | 函数                                       | 描述           |
  | ---------------------------------------- | ------------ |
  | public void addChild(FCB childNode)      | 添加子目录        |
  | public void setIndexTableI(int i, int x) | 添加磁盘块的地址到索引表 |



#### 4.2.6 FCBProperty.java

**class FCBProperty**：FCB绑定Property。

- 成员变量

  | 变量                                       | 描述                   |
  | ---------------------------------------- | -------------------- |
  | private StringProperty name              | 文件名称                 |
  | private StringProperty size              | 文件大小                 |
  | private StringProperty time              | 文件最后修改时间             |
  | private FCB fcb                          | *property*绑定的对象*fcb* |
  | private ObjectProperty\<ImageView> imageView | 文件图像                 |
  | private Button open                      | 打开文件按钮               |
  | private Button delete                    | 删除文件按钮               |
  | private ObjectProperty\<Button> openButton | 打开文件按钮的*property*    |
  | private ObjectProperty\<Button> deleteButton | 删除文件按钮的*property*    |



#### 4.2.7 FileController.java

**class FileController**：file.fxml的controller。

- 成员变量

  | 变量                                       | 描述                 |
  | ---------------------------------------- | ------------------ |
  | @FXML private TextField nameText         | 文件名称的文字框           |
  | @FXML private TextArea context           | 文件内容的文字框           |
  | private boolean isWritable               | 文件是否可写             |
  | private FCB fcb                          | 当前的*FCB*对象         |
  | private DiskManager diskManager          | 当前的*DiakManager*对象 |
  | public static ArrayList\<Stage> attentionStages | 存放*AttentionStage* |



- 成员函数

  | 函数                       | 描述                |
  | ------------------------ | ----------------- |
  | public void initialize() | *controller*初始化   |
  | private void save()      | *save*选项引发的动作     |
  | private void delete()    | *delete*选项引发的动作   |
  | private void close()     | *close*选项引发的动作    |
  | private void readonly()  | *readonly*选项引发的动作 |
  | private void writable()  | *writable*选项引发的动作 |

#### 4.2.8 Main.java

**class Main**：程序入口。

- 成员变量

  | 变量                                       | 描述             |
  | ---------------------------------------- | -------------- |
  | public static HashMap\<FCB, Stage> stages | 存放所有打开的*Stage* |

- 成员函数

  | 函数                                     | 描述           |
  | -------------------------------------- | ------------ |
  | public void start(Stage primaryStage)  | 程序开始执行时引发的动作 |
  | public static void main(String[] args) | 程序入口         |

#### 4.2.9 SystemController.java

**class NameComparator**：名字比较器

**class SizeComparator**：大小比较器

**class TimeComparator**：时间比较器

**class PathComparator**：时间比较器


**class SystemController**：sysdem.fxml的controller

- 成员变量

  | 变量                                       | 描述                |
  | ---------------------------------------- | ----------------- |
  | private static StringProperty *path*     | 当前路径              |
  | private int newFileNumber                | 新建文件编号            |
  | private int newFOlderNumber              | 新建文件夹编号           |
  | public static FCB currentFCB             | 当前打开的*FCB*        |
  | private static ObservableList\<FCBProperty> list | *TableView*中的文件列表 |
  | private final Node rootIcon              | 根目录图标             |
  | public static DirectoryTree directoryTree | 目录树对象             |
  | public static FCB currentDirectory       | 当前打开的子目录          |
  | private static Disk disk                 | 磁盘对象              |
  | public static DiskManager diskManager    | 磁盘管理器对象           |
  | private Image folderImage                | 文件夹图标             |
  | private ArrayList\<String> allPath       | 所有路径列表            |

  ​





- 成员函数

  | 函数                                       | 描述                              |
  | ---------------------------------------- | ------------------------------- |
  | public void initialize()                 | *controller*初始化函数               |
  | public void getAllPath()                 | 通过*directoryTree*中的*fcb*列出路径字符串 |
  | private TreeItem\<String> getOrCreateChild(TreeItem\<String> parent, String value) | 获得*parent*的子节点或者创建新子节点          |
  | private void initTreeView()              | 初始化*TreeView*                   |
  | private void initTableView()             | 初始化*TableView*                  |
  | public static void updateCurrentPath()   | 刷新路径                            |
  | public static void updateFileList()      | 更新文件列表                          |

  ​



#### 4.2.10 attention.fxml

提醒有重名文件的Stage的fxml文件。

#### 4.2.11 file.fxml

文件页面Stage的fxml文件。

#### 4.2.12 system.fxml

系统主页Stage的fxml文件。

## 5. 界面展示

### 系统主页面

![Markdown](http://i2.kiimg.com/1949/365aa0587f8aa06d.png)

### 文件页面

![Markdown](http://i2.kiimg.com/1949/91503bb1783c8a73.png)

