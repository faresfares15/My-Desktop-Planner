package Views;

import Controllers.TaskControllers.PlanTaskController;
import Models.FreeSlot.FreeSlotModel;
import Models.Task.TaskModel;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.Duration;
import java.time.LocalDate;

public class PlanTaskView extends Stage {
    private FreeSlotModel freeSlotModel;
    private TaskModel taskModel;
    private TextField taskNameField;
    private Spinner<Integer> hoursSpinner;
    private Spinner<Integer> minutesSpinner;
    private ComboBox<String> priorityComboBox;
    private DatePicker deadlinePicker;
    public PlanTaskView(FreeSlotModel freeSlotModel, TaskModel taskModel) {
        this.freeSlotModel = freeSlotModel;
        this.taskModel = taskModel;
        this.setTitle("Plan Task View");
        //this.setScene(new Scene(new PlanTaskPane(/*... model, */)));

        // Create the task name field
        Label taskNameLabel = new Label("Task Name:");
        taskNameField = new TextField();

        Label durationLabel = new Label("Duration:");
        hoursSpinner = new Spinner<>(0, 24, 0);
        hoursSpinner.setEditable(true);
        minutesSpinner = new Spinner<>(0, 60, 0);
        minutesSpinner.setEditable(true);

        Label priorityLabel = new Label("Priority:");
        priorityComboBox = new ComboBox<>(FXCollections.observableArrayList("LOW", "MEDIUM", "HIGH"));

        Label deadlineLabel = new Label("Deadline:");
        deadlinePicker = new DatePicker();

        // Create a button to submit the form
        Button submitButton = new Button("Create Task");
        submitButton.setOnAction(new PlanTaskController(this.freeSlotModel, this.taskModel, this));

        //Create a grid pane to hold the form elements
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.addRow(0, taskNameLabel, taskNameField);
        gridPane.addRow(1, durationLabel, hoursSpinner, minutesSpinner);
        gridPane.addRow(2, priorityLabel, priorityComboBox);
        gridPane.addRow(3, deadlineLabel, deadlinePicker);
        gridPane.addRow(4, submitButton);

        GridPane.setConstraints(taskNameLabel, 0, 0);
        GridPane.setConstraints(taskNameField, 1, 0);

        GridPane.setConstraints(durationLabel, 0, 1);
        GridPane.setConstraints(hoursSpinner, 1, 1);
        GridPane.setConstraints(minutesSpinner, 2, 1);

        GridPane.setConstraints(priorityLabel, 0, 2);
        GridPane.setConstraints(priorityComboBox, 1, 2);

        GridPane.setConstraints(deadlineLabel, 0, 3);
        GridPane.setConstraints(deadlinePicker, 1, 3);

        GridPane.setConstraints(submitButton, 0, 4);
        GridPane.setColumnSpan(submitButton, 2);

        // Create a scene with the gridPane as the root node
        this.setScene(new javafx.scene.Scene(gridPane, 500, 500));
    }

    public String getTaskName() {
        return taskNameField.getText();
    }

    public String getPriority() {
        return priorityComboBox.getValue();
    }
    public Duration getDuration(){
        return Duration.ofHours(hoursSpinner.getValue()).plusMinutes(minutesSpinner.getValue());
    }
    public LocalDate getDeadline() {
        return deadlinePicker.getValue();
    }
}
