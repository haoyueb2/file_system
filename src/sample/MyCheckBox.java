package sample;

/**
 * Created by keke on 2017/6/17.
 */
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;


public class MyCheckBox {
    CheckBox checkbox=new CheckBox();

    public MyCheckBox() {
        checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                System.out.println("CheckBox:  " + " old value--" + t.toString() + "     new value--" + t1.toString());
                if(t==false && t1 == true) {
                    SystemController.selectCount ++;
                }
                if (t==true && t1 == false) {
                    SystemController.selectCount--;
                }
                System.out.println(SystemController.selectCount);
            }
        });
    }

    public ObservableValue<CheckBox> getCheckBox()
    {
        return new  ObservableValue<CheckBox>() {
            @Override
            public void addListener(ChangeListener<? super CheckBox> listener) {

            }

            @Override
            public void removeListener(ChangeListener<? super CheckBox> listener) {

            }

            @Override
            public CheckBox getValue() {
                return checkbox;
            }

            @Override
            public void addListener(InvalidationListener listener) {

            }

            @Override
            public void removeListener(InvalidationListener listener) {

            }
        };
    }
    public Boolean isSelected()
    {
        return checkbox.isSelected();
    }
}