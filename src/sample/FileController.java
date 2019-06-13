package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;


public class FileController {

    public TextField nameText;
    public TextArea context;
    public Button save;
    private boolean isWritable = true;

    private FCB fcb = Controller.currentFCB;
    private DiskManager diskManager = Controller.diskManager;

    public static ArrayList<Stage> attentionStages = new ArrayList<>();



    public void initialize() {
        nameText.setText(fcb.getName());
        context.setText(diskManager.read(fcb));
        //context.setText(fcb.content);
        //自动换行
        context.setWrapText(true);
        context.setEditable(true);
        if(fcb.getAuthority() == FCB.Authority.readonly) {
            context.setEditable(false);
        } else {
            context.setEditable(true);
        }

        save.setOnAction(e->{
            fcb.setModifyTime();

            if(Controller.directoryTree.isSameName(fcb.getParent(),nameText.getText())) {

                try {
                    Parent root = FXMLLoader.load(getClass().getResource("alert.fxml"));
                    Stage attentionStage = new Stage();
                    attentionStage.setTitle("File");
                    attentionStage.setScene(new Scene(root, 300, 200));
                    attentionStages.add(attentionStage);
                    attentionStage.show();
                } catch (Exception e1) {
                    System.out.println(e1);
                }

            }
            fcb.setName(nameText.getText());
//        System.out.println(context.getText());

            diskManager.write(fcb,context.getText());

//        fcb.content = context.getText();
//        String str = context.getText();
//        if(str.length() <= 1024) {
//            fcb.setSize(str.length() + "B");
//        } else if (str.length() > 1024 && str.length() <= 1024 * 1024 ) {
//            fcb.setSize(str.length() / 1024 + "K");
//        }
            if(isWritable) {
                fcb.setAuthority(FCB.Authority.writable);
            } else {
                fcb.setAuthority(FCB.Authority.readonly);
            }
        });
    }



}
