package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.RadioButton;

/**
 * Created by keke on 2017/6/16.
 */
public class FCBProperty {
    private StringProperty name;
    private StringProperty size;
    private StringProperty time;
    private boolean choice;
    public MyCheckBox checkbox = new MyCheckBox();
    private FCB fcb;

    public FCBProperty(FCB fcb) {
        this.fcb = fcb;
        this.name = new SimpleStringProperty(fcb.getName());
        this.size = new SimpleStringProperty(Integer.toString(fcb.getSize()));
        this.time = new SimpleStringProperty(fcb.getModifyTime());
        this.choice = false;
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

    public boolean isChoice() {
        return choice;
    }

    public void setChoice(boolean choice) {
        this.choice = choice;
    }

    public FCB getFcb() {
        return fcb;
    }
}
