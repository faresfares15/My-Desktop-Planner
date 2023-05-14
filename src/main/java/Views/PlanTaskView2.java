package Views;

import Controllers.TaskControllers.PlanTaskController;
import Models.FreeSlot.FreeSlotModel;
import Models.Task.TaskModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class PlanTaskView2 extends Stage {
    private FreeSlotModel freeSlotModel;
    private TaskModel taskModel;
    @FXML
    private TextField taskNameField;
    @FXML
    private Spinner<Integer> startTimeHoursSpinner;
    @FXML
    private Spinner<Integer> startTimeMinutesSpinner;
    @FXML
    private Spinner<Integer> durationHoursSpinner;
    @FXML
    private Spinner<Integer> durationMinutesSpinner;
    @FXML
    private ComboBox<String> priorityComboBox;
    @FXML
    private DatePicker deadlinePicker;
    public PlanTaskView2(FreeSlotModel freeSlotModel, TaskModel taskModel){
        //setting the models
        this.freeSlotModel = freeSlotModel;
        this.taskModel = taskModel;

        //getting the view's fxml file
        System.setProperty("javafx.sg.warn", "true");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("plan-task-view.fxml"));
        Parent root;

        try{
            root = fxmlLoader.load();
            Scene scene = new Scene(root);
            this.setScene(scene);

            //getting the controller from the view
            PlanTaskController planTaskController = fxmlLoader.getController();

            //setting the models for the controller
            planTaskController.setFreeSlotModel(freeSlotModel);
            planTaskController.setTaskModel(taskModel);

            //setting the view for the controller
//            planTaskController.setPlanTaskView(this);

            //showing the view
            this.show();
        }catch (IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public String getTaskName() {
        return taskNameField.getText();
    }
    public LocalTime getStartTime(){
        return LocalTime.of(startTimeHoursSpinner.getValue(), startTimeMinutesSpinner.getValue());
    }
    public Duration getDuration(){
        return Duration.ofHours(durationHoursSpinner.getValue()).plusMinutes(durationMinutesSpinner.getValue());
    }
    public String getPriority() {
        return priorityComboBox.getValue();
    }
    public LocalDate getDeadline() {
        return deadlinePicker.getValue();
    }

}
