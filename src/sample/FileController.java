package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 * Created by keke on 2017/6/10.
 */
public class FileController {

    @FXML private TextField nameText;
    @FXML private TextArea context;

    private boolean isWritable = true;

    private FCB fcb = SystemController.currentFCB;
    private DiskManager diskManager = SystemController.diskManager;

//    // TODO: 2017/6/21 这两个参数怎么传的?  没有找到很好的方法,用static了
//    public FileController(FCB fcb, DiskManager diskManager) {
//        this.fcb = fcb;
//        this.diskManager = diskManager;
//    }

    public void initialize() {
        nameText.setText(fcb.getName());
        context.setText(diskManager.read(fcb));
    }

    @FXML
    private void save() {
        fcb.setModifyTime();
        fcb.setName(nameText.getText());
        System.out.println(context.getText());
        diskManager.write(fcb,context.getText());
        if(isWritable) {
            fcb.setAuthority(FCB.Authority.writable);
        } else {
            fcb.setAuthority(FCB.Authority.readonly);
        }
    }

    @FXML
    private void delete() {
        for (int i = 0; i < fcb.getIndexTable().length; i++) {

        }
    }

    @FXML
    private void close() {
        Main.stages.get(1).close();
//        Platform.exit();

        this.save();
        fcb.setModifyTime();

    }

    @FXML
    private void readonly() {
        context.setEditable(false);
        isWritable = false;
        fcb.setModifyTime();
    }

    @FXML
    private void writable() {
        context.setEditable(true);
        isWritable = true;
        fcb.setModifyTime();
    }
}
