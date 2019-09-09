package sample;

import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class ListItem {
    private StringProperty name;
    private StringProperty size;
    private StringProperty time;
//    private boolean choice;

    private FCB fcb;
    private ObjectProperty<ImageView> imageView;
    private Button open = new Button("打开");
    private Button delete = new Button("删除");
    private Button detail = new Button("详情");
    private ObjectProperty<Button> openButton = new SimpleObjectProperty<Button>(open);
    private ObjectProperty<Button> deleteButton = new SimpleObjectProperty<Button>(delete);
    private ObjectProperty<Button> detailButton = new SimpleObjectProperty<Button>(detail);


    public ListItem(FCB fcb) {
        this.fcb = fcb;
        this.name = new SimpleStringProperty(fcb.getName());
        this.size = new SimpleStringProperty(fcb.getSize());
        this.time = new SimpleStringProperty(fcb.getModifyTime());
        if (fcb.getType() == FCB.Type.document) {
            imageView = new SimpleObjectProperty<ImageView>(
                    new ImageView(new Image(getClass().getResourceAsStream("/images/file.png")))
            );
        } else {
            imageView = new SimpleObjectProperty<ImageView>(
                    new ImageView(new Image(getClass().getResourceAsStream("/images/004-folder-5.png")))
            );
        }
//        this.choice = false;
        open.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                Controller.currentFCB = fcb;
                if (fcb.getType() == FCB.Type.document) {
                    try {
                        if (!DiskManager.openFileTable.contains(fcb)) {
                            Parent root = FXMLLoader.load(getClass().getResource("file.fxml"));
                            Stage stage = new Stage();
                            Main.stages.put(fcb, stage);
                            stage.setTitle("File");
                            stage.setScene(new Scene(root, 800, 500));
                            stage.show();

                            DiskManager.openFileTable.add(fcb);

                            stage.setOnCloseRequest(event1 -> {

                                Main.stages.remove(fcb);

                                DiskManager.openFileTable.remove(fcb);

                                Controller.updateFileList();
                            });
                        }

                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else {
                    Controller.currentDirectory = fcb;
                    Controller.updateCurrentPath();
                    Controller.updateFileList();
                }
            }
        });

        delete.setOnAction(event -> {
            if (fcb.getType() == FCB.Type.document) {
                Controller.directoryTree.deleteFile(fcb);
                Controller.diskManager.delete(fcb);
                Controller.updateFileList();

            } else {
                Controller.directoryTree.deleteFolder(fcb);
                Controller.diskManager.delete(fcb);
                Controller.updateFileList();
            }
        });
        detail.setOnAction(event -> {
            Controller.updateDetail(fcb.displayDetails(fcb));
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

    public Button getDetailButton() {
        return deleteButton.get();
    }

    public ObjectProperty<Button> detailButtonProperty() {
        return detailButton;
    }

    public void setDetailButton(Button detailButton) {
        this.detailButton.set(detailButton);
    }

    public FCB getFcb() {
        return fcb;
    }
}
