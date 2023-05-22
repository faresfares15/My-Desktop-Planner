package Controllers.FreeSlotControllers;

import Controllers.CategoryControllers.AddTaskToCategoryController;
import Controllers.ProjectControllers.AddTaskToProjectController;
import Models.FreeSlot.FreeSlotSchema;
import Models.Project.ProjectSchema;
import Models.Task.TaskSchema;
import esi.tp_poo_final.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class FreeSlotInfoViewController {
    @FXML
    Text date;
    @FXML
    Text startTime;
    @FXML
    Text endTime;
    @FXML
    Text duration;
    FreeSlotSchema freeSlot;
    public FreeSlotInfoViewController(FreeSlotSchema freeSlot){
        this.freeSlot = freeSlot;
    }
    public void initData(){

        //set the free slot date
        date.setText(freeSlot.getDate().toString());

        //set the task start time
        startTime.setText(freeSlot.getStartTime().toString());

        //set the free slot end time
        endTime.setText(freeSlot.getEndTime().toString());

        //set the task duration
        duration.setText(freeSlot.getDuration().toHours() + "hours " + freeSlot.getDuration().toMinutes() % 60 + "minutes");

    }
    public void goToCalendar() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("calendar-view.fxml"));
//            fxmlLoader.setControllerFactory(c -> new ...());
        Scene scene = new Scene(fxmlLoader.load(), 840, 500);
        Stage stage = (Stage) date.getScene().getWindow();
        stage.setTitle("Calendar");

        //center the view on the user's screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - scene.getHeight()) / 2);

        stage.setScene(scene);
    }
}
