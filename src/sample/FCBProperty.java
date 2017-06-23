package sample;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 * Created by keke on 2017/6/16.
 */
public class FCBProperty {
    private StringProperty name;
    private StringProperty size;
    private StringProperty time;
//    private boolean choice;

    private FCB fcb;
    private ObjectProperty<ImageView> imageView;
    private Button open = new Button("open");
    private Button delete = new Button("delete");
    private ObjectProperty<Button> openButton = new SimpleObjectProperty<Button>(open);
    private ObjectProperty<Button> deleteButton = new SimpleObjectProperty<Button>(delete);


    public FCBProperty(FCB fcb) {
        this.fcb = fcb;
        this.name = new SimpleStringProperty(fcb.getName());
        this.size = new SimpleStringProperty(fcb.getSize());
        this.time = new SimpleStringProperty(fcb.getModifyTime());
        if(fcb.getType() == FCB.Type.document) {
            imageView = new SimpleObjectProperty<ImageView>(
                    new ImageView(new Image(getClass().getResourceAsStream("/images/file.png")))
            );
        } else {
            imageView = new SimpleObjectProperty<ImageView>(
                    new ImageView(new Image(getClass().getResourceAsStream("/images/004-folder-5.png")))
            );
        }
//        this.choice = false;
        open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                System.out.println(fcb.getName());
                SystemController.currentFCB = fcb;
//                System.out.println(SystemController.currentFCB.getName());
//                System.out.println(fcb.getName());
                if(fcb.getType() == FCB.Type.document) {
//                    System.out.println("doc");
                    try {
                        if(! DiskManager.openFileTable.contains(fcb)) {
//                            System.out.println("!contain");
                            Parent root = FXMLLoader.load(getClass().getResource("file.fxml"));
                            Stage stage = new Stage();
                            Main.stages.put(fcb,stage);
                            stage.setTitle("File");
                            stage.setScene(new Scene(root, 800, 500));
                            stage.show();

                            DiskManager.openFileTable.add(fcb);

                            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                @Override
                                public void handle(WindowEvent event) {


//                                    Main.stages.get(fcb).close();

                                    Main.stages.remove(fcb);

                                    DiskManager.openFileTable.remove(fcb);

                                    SystemController.updateFileList();
                                }
                            });
                        }

                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else {
                    SystemController.currentDirectory = fcb;
                    SystemController.updateCurrentPath();
                    SystemController.updateFileList();
                }
            }
        });

        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(fcb.getType() == FCB.Type.document) {

                    SystemController.directoryTree.deleteFile(fcb);
                    SystemController.diskManager.delete(fcb);

//                    System.out.println("aftrer delete:");
//                    for (int i = 0; i < SystemController.directoryTree.getDirectoryTree().size(); i++) {
//                        System.out.println(SystemController.directoryTree.getDirectoryTree().get(i).getName());
//                    }
                    SystemController.updateFileList();

                } else {
                    SystemController.currentDirectory = fcb.getParent();
                    SystemController.directoryTree.deleteFolder(fcb);
                    SystemController.diskManager.delete(fcb);
                    SystemController.updateFileList();
                }

            }

        });

    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSize() {
        return size.get();
    }

    public StringProperty sizeProperty() {
        return size;
    }

    public void setSize(String size) {
        this.size.set(size);
    }

    public String getTime() {
        return time.get();
    }

    public StringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public ImageView getImageView() {
        return imageView.get();
    }

    public ObjectProperty<ImageView> imageViewProperty() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView.set(imageView);
    }

    public Button getOpenButton() {
        return openButton.get();
    }

    public ObjectProperty<Button> openButtonProperty() {
        return openButton;
    }

    public void setOpenButton(Button openButton) {
        this.openButton.set(openButton);
    }

    public Button getDeleteButton() {
        return deleteButton.get();
    }

    public ObjectProperty<Button> deleteButtonProperty() {
        return deleteButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton.set(deleteButton);
    }

    public FCB getFcb() {
        return fcb;
    }
}
