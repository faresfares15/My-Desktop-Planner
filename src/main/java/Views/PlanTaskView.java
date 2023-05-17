package Views;

import Controllers.TaskControllers.PlanTaskController;
import Models.Day.DayModel;
import Controllers.VisualControllers.ToggleSimpleTaskController;
import Models.FreeSlot.FreeSlotModel;
import Models.Task.TaskModel;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
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
    public PlanTaskView(FreeSlotModel freeSlotModel, TaskModel taskModel, DayModel dayModel, Stage primaryStage) {
        this.freeSlotModel = freeSlotModel;
        this.dayModel = dayModel;
        this.taskModel = taskModel;
//        this.setTitle("Plan Task View");
//        //this.setScene(new Scene(new PlanTaskPane(/*... model, */)));
//
//        // Create the task name field
//        Label taskNameLabel = new Label("Task Name:");
//        taskNameField = new TextField("Task1");
//
//        Label startTimeLabel = new Label("Start Time:");
//        startTimeHoursSpinner = new Spinner<>(0, 24, 0);
//        startTimeHoursSpinner.setEditable(true);
//        startTimeMinutesSpinner = new Spinner<>(0, 60, 0);
//        startTimeMinutesSpinner.setEditable(true);
//
//        Label durationLabel = new Label("Duration:");
//        durationHoursSpinner = new Spinner<>(0, 24, 0);
//        durationHoursSpinner.setEditable(true);
//        durationMinutesSpinner = new Spinner<>(0, 60, 0);
//        durationMinutesSpinner.setEditable(true);
//
//        Label priorityLabel = new Label("Priority:");
//        priorityComboBox = new ComboBox<>(FXCollections.observableArrayList("LOW", "MEDIUM", "HIGH"));
//
//        Label deadlineLabel = new Label("Deadline:");
//        deadlinePicker = new DatePicker(LocalDate.of(2023, 5, 11));
//
//        // Create a button to submit the form
//        Button submitButton = new Button("Create Task");
////        submitButton.setOnAction(new PlanTaskController(this.freeSlotModel, this.taskModel, this));
//
//        //Create a grid pane to hold the form elements
//        GridPane gridPane = new GridPane();
//        submitButton.setOnAction(new ToggleSimpleTaskController(taskNameLabel, taskNameField));
//        gridPane.setPadding(new Insets(10));
//        gridPane.setHgap(10);
//        gridPane.setVgap(10);
//        gridPane.addRow(0, taskNameLabel, taskNameField);
//        gridPane.addRow(1, startTimeLabel, startTimeHoursSpinner, startTimeMinutesSpinner);
//        gridPane.addRow(2, durationLabel, durationHoursSpinner, durationMinutesSpinner);
//        gridPane.addRow(3, priorityLabel, priorityComboBox);
//        gridPane.addRow(4, deadlineLabel, deadlinePicker);
//        gridPane.addRow(5, submitButton);
//
//        GridPane.setConstraints(taskNameLabel, 0, 0);
//        GridPane.setConstraints(taskNameField, 1, 0);
//
//        GridPane.setConstraints(startTimeLabel, 0, 1);
//        GridPane.setConstraints(startTimeHoursSpinner, 1, 1);
//        GridPane.setConstraints(startTimeMinutesSpinner, 2, 1);
//
//        GridPane.setConstraints(durationLabel, 0, 2);
//        GridPane.setConstraints(durationHoursSpinner, 1, 2);
//        GridPane.setConstraints(durationMinutesSpinner, 2, 2);
//
//        GridPane.setConstraints(priorityLabel, 0, 3);
//        GridPane.setConstraints(priorityComboBox, 1, 3);
//
//        GridPane.setConstraints(deadlineLabel, 0, 4);
//        GridPane.setConstraints(deadlinePicker, 1, 4);
//
//        GridPane.setConstraints(submitButton, 0, 5);
//        GridPane.setColumnSpan(submitButton, 2);
//
//        // Create a scene with the gridPane as the root node
//        this.setScene(new javafx.scene.Scene(gridPane, 500, 500));

        //New code
        int NUM_HOURS = 24;
        int NUM_DAYS = 7;
        GridPane calendarGrid = new GridPane();
        calendarGrid.setGridLinesVisible(true);

        // Add time labels
        for (int hour = 0; hour < NUM_HOURS; hour++) {
            String time = String.format("%02d:00", hour);
            Label timeLabel = new Label(time);
            GridPane.setRowIndex(timeLabel, hour + 1);
            calendarGrid.getChildren().add(timeLabel);
        }

        // Add day labels
        for (int day = 0; day < NUM_DAYS; day++) {
            String dayName = "Day " + (day + 1);
            Label dayLabel = new Label(dayName);
            GridPane.setColumnIndex(dayLabel, day + 1);
            calendarGrid.getChildren().add(dayLabel);
        }

        // Create a button to submit the form
        Button submitButton = new Button("Create Task");
        submitButton.setOnAction(new PlanTaskController(this.freeSlotModel, this.taskModel, this.dayModel));

        // Add rectangles for time slots
        for (int day = 0; day < NUM_DAYS; day++) {
            for (int hour = 0; hour < NUM_HOURS; hour++) {
                Rectangle rectangle = new Rectangle();
                rectangle.setWidth(50);
                rectangle.setHeight(10);
                rectangle.setFill(Color.BLUE); // Change color as needed
                GridPane.setColumnIndex(rectangle, day + 1);
                GridPane.setRowIndex(rectangle, hour + 1);
                calendarGrid.getChildren().add(rectangle);
            }
        }

        // Add rectangles for tasks
        // Replace this with your own logic to determine task positions and colors
        Rectangle taskRectangle = new Rectangle();
        taskRectangle.setWidth(50);
        taskRectangle.setHeight(30);
        taskRectangle.setFill(Color.RED);
        GridPane.setColumnIndex(taskRectangle, 1);
        GridPane.setRowIndex(taskRectangle, 3);
        GridPane.setRowSpan(taskRectangle, 2);
        calendarGrid.getChildren().add(taskRectangle);

        VBox vBox = new VBox(calendarGrid);

        ScrollPane scrollPane = new ScrollPane(vBox);
        Scene scene = new Scene(scrollPane, 800, 200);
        centerSceneOnScreen(scene, primaryStage);
        this.setScene(scene);
    }
    private void centerSceneOnScreen(Scene scene, Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - scene.getHeight()) / 2);
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
