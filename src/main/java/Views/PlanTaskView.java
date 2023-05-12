package Views;

import Controllers.TaskControllers.PlanTaskController;
import Models.Day.DayModel;
import Models.FreeSlot.FreeSlotModel;
import Models.Task.TaskModel;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class PlanTaskView extends Stage {
    private FreeSlotModel freeSlotModel;
    private TaskModel taskModel;
    private DayModel dayModel;
    private TextField taskNameField;
    private Spinner<Integer> startTimeHoursSpinner;
    private Spinner<Integer> startTimeMinutesSpinner;

    private Spinner<Integer> durationHoursSpinner;
    private Spinner<Integer> durationMinutesSpinner;
    private ComboBox<String> priorityComboBox;
    private DatePicker deadlinePicker;
    public PlanTaskView(FreeSlotModel freeSlotModel, TaskModel taskModel, DayModel dayModel) {
        this.freeSlotModel = freeSlotModel;
        this.taskModel = taskModel;
        this.dayModel = dayModel;
        this.setTitle("Plan Task View");
        //this.setScene(new Scene(new PlanTaskPane(/*... model, */)));

        // Create the task name field
        Label taskNameLabel = new Label("Task Name:");
        taskNameField = new TextField("Task1");

        Label startTimeLabel = new Label("Start Time:");
        startTimeHoursSpinner = new Spinner<>(0, 24, 0);
        startTimeHoursSpinner.setEditable(true);
        startTimeMinutesSpinner = new Spinner<>(0, 60, 0);
        startTimeMinutesSpinner.setEditable(true);

        Label durationLabel = new Label("Duration:");
        durationHoursSpinner = new Spinner<>(0, 24, 0);
        durationHoursSpinner.setEditable(true);
        durationMinutesSpinner = new Spinner<>(0, 60, 0);
        durationMinutesSpinner.setEditable(true);

        Label priorityLabel = new Label("Priority:");
        priorityComboBox = new ComboBox<>(FXCollections.observableArrayList("LOW", "MEDIUM", "HIGH"));

        Label deadlineLabel = new Label("Deadline:");
        deadlinePicker = new DatePicker(LocalDate.of(2023, 5, 11));

        // Create a button to submit the form
        Button submitButton = new Button("Create Task");
        submitButton.setOnAction(new PlanTaskController(this.freeSlotModel, this.taskModel, this.dayModel, this));

        //Create a grid pane to hold the form elements
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.addRow(0, taskNameLabel, taskNameField);
        gridPane.addRow(1, startTimeLabel, startTimeHoursSpinner, startTimeMinutesSpinner);
        gridPane.addRow(2, durationLabel, durationHoursSpinner, durationMinutesSpinner);
        gridPane.addRow(3, priorityLabel, priorityComboBox);
        gridPane.addRow(4, deadlineLabel, deadlinePicker);
        gridPane.addRow(5, submitButton);

        GridPane.setConstraints(taskNameLabel, 0, 0);
        GridPane.setConstraints(taskNameField, 1, 0);

        GridPane.setConstraints(startTimeLabel, 0, 1);
        GridPane.setConstraints(startTimeHoursSpinner, 1, 1);
        GridPane.setConstraints(startTimeMinutesSpinner, 2, 1);

        GridPane.setConstraints(durationLabel, 0, 2);
        GridPane.setConstraints(durationHoursSpinner, 1, 2);
        GridPane.setConstraints(durationMinutesSpinner, 2, 2);

        GridPane.setConstraints(priorityLabel, 0, 3);
        GridPane.setConstraints(priorityComboBox, 1, 3);

        GridPane.setConstraints(deadlineLabel, 0, 4);
        GridPane.setConstraints(deadlinePicker, 1, 4);

        GridPane.setConstraints(submitButton, 0, 5);
        GridPane.setColumnSpan(submitButton, 2);

        // Create a scene with the gridPane as the root node
        this.setScene(new javafx.scene.Scene(gridPane, 500, 500));
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
