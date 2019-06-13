package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;


public class FileController {

    @FXML private TextField nameText;
    @FXML private TextArea context;

    private boolean isWritable = true;

    private FCB fcb = SystemController.currentFCB;
    private DiskManager diskManager = SystemController.diskManager;

    public static ArrayList<Stage> attentionStages = new ArrayList<>();



    public void initialize() {
        nameText.setText(fcb.getName());
        context.setText(diskManager.read(fcb));
        //自动换行
        context.setWrapText(true);
        if(fcb.getAuthority() == FCB.Authority.readonly) {
            context.setEditable(false);
        } else {
            context.setEditable(true);
        }
    }

    @FXML
    private void save() {
        fcb.setModifyTime();

        if(SystemController.directoryTree.isSameName(fcb.getParent(),nameText.getText())) {

            try {
                Parent root = FXMLLoader.load(getClass().getResource("Attention.fxml"));
                Stage attentionStage = new Stage();
                attentionStage.setTitle("File");
                attentionStage.setScene(new Scene(root, 300, 200));
                attentionStages.add(attentionStage);
                attentionStage.show();
            } catch (Exception e) {
                System.out.println(e);
            }

        }
        fcb.setName(nameText.getText());
//        System.out.println(context.getText());
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
        Main.stages.get(fcb).close();

        Main.stages.remove(fcb);

        DiskManager.openFileTable.remove(fcb);

        SystemController.updateFileList();
    }

    @FXML
    private void detail() {

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
