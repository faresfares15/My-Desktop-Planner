package Controllers.CalendarControllers;

import Controllers.TaskControllers.TaskInfoViewController;
import Exceptions.TaskDoesNotExistException;
import Models.Task.TaskSchema;
import esi.tp_poo_final.HelloApplication;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ShowCalendarController{

    private final int NUM_HOURS = 24;
    private final int NUM_DAYS = 7;
    private final Color taskColor = Color.RED;
    private final Color freeSlotColor = Color.GREEN;
    private final double hourBlockHeight = 30;
    private final double minuteBlockHeight = hourBlockHeight / 60;
    @FXML
    GridPane calendarGrid;
    @FXML
    public void initialize(){

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

        // Add rectangles for time slots
        for (int day = 0; day < NUM_DAYS; day++) {
            for (int hour = 0; hour < NUM_HOURS; hour++) {
                Rectangle rectangle = new Rectangle(50, hourBlockHeight, Color.BLUE); //width, height, fill color
                GridPane.setColumnIndex(rectangle, day + 1);
                GridPane.setRowIndex(rectangle, hour + 1);
                GridPane.setValignment(rectangle, VPos.TOP);
                calendarGrid.getChildren().add(rectangle);
            }
        }


        try{
            // create blocks for the tasks
            ArrayList<TaskSchema> dayTasksList = HelloApplication.taskModel.findMany(LocalDate.now());
            for(TaskSchema task: dayTasksList){

                //create a rectangular background
                double taskBlocHeight = task.getDuration().toMinutes() * minuteBlockHeight;
                Rectangle taskRectangle = new Rectangle(50, taskBlocHeight, taskColor); //width, height, fill color

                //create a text label for the task name
                Text text = new Text(task.getName());
                text.setFont(Font.font("Arial", FontWeight.BOLD, 14));

                // Create a nested pane to hold the rectangle and text
                StackPane taskBlock = new StackPane(taskRectangle, text);

                taskBlock.setMaxHeight(taskBlocHeight);

                // Translate the task block to the bottom, according the
                double taskMinutesOffset = task.getStartTime().getMinute() * minuteBlockHeight;
                taskBlock.setTranslateY(taskMinutesOffset);

                //set the position of the task block in the view
                GridPane.setColumnIndex(taskBlock, 1); //corresponds to the day
                GridPane.setRowIndex(taskBlock, task.getStartTime().getHour()+1); //corresponds to the time

                int taskBlockRowSpan = task.getStartTime().plus(task.getDuration()).getHour() - task.getStartTime().getHour() +1;
                System.out.println("span: "+ taskBlockRowSpan);
                GridPane.setRowSpan(taskBlock, taskBlockRowSpan); //corresponds to the duration

                //add the task to the grid
                calendarGrid.getChildren().add(taskBlock);

            }
        }catch (Exception e){

        }

        Rectangle task2Rectangle = new Rectangle(50, hourBlockHeight, freeSlotColor); //width, height, fill color

        Text text2 = new Text("Hi");
        text2.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        // Create a nested pane to hold the rectangle and text
        StackPane taskBlock2 = new StackPane(task2Rectangle, text2);

        GridPane.setColumnIndex(taskBlock2, 1);
        GridPane.setRowIndex(taskBlock2, 6);
        GridPane.setRowSpan(taskBlock2, 2);
        calendarGrid.getChildren().add(taskBlock2);

        //set a click event handler on the calendar, for when a task box was clicked
        calendarGrid.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                try{
                    //get the clicked object
                    Object clickedItem = event.getTarget();

                    //declare necessary variables
                    Color blockColor = null;
                    String blockTitle = null;

                    //get the color and the title of the clicked block (if it is a block)
                    if(clickedItem instanceof StackPane){
                        //get the stack pane object
                        StackPane taskBox = (StackPane) clickedItem;

                        //get the color of its rectangle
                        Rectangle rectangle = (Rectangle) taskBox.getChildren().get(0);
                        blockColor = (Color) rectangle.getFill();

                        //get the content of the text
                        blockTitle = ((Text) taskBox.getChildren().get(1)).getText();
                    }
                    else if(clickedItem instanceof Rectangle){
                        //get the rectangle's color
                        Rectangle rectangle = (Rectangle) clickedItem;
                        blockColor = (Color) rectangle.getFill();

                        //get the stack pane object to access the text
                        StackPane stackPane = (StackPane) rectangle.getParent();

                        //get the text form the stack pane container
                        Text text = (Text) stackPane.getChildren().get(1); //the text is at index 1
                        blockTitle = text.getText();
                    }
                    else if(clickedItem instanceof Text text){
                        //get the stack pane object to access the rectangle
                        StackPane stackPane = (StackPane) text.getParent();

                        //get the rectangle's color
                        Rectangle rectangle = (Rectangle) stackPane.getChildren().get(0); //the rectangle is at index 0
                        blockColor = (Color) rectangle.getFill();

                        //get the text
                        blockTitle = ((Text) clickedItem).getText();
                    }
                    if(blockTitle == null){
                        //if there's no text, then the clicked item is not a regular block, so ignore it
                        System.out.println("No block clicked");
                        return;
                    }

                    if(blockColor.equals(taskColor)){
                        //the clicked block is a task block
                        System.out.println("Task clicked: " + blockTitle);
                        System.out.println("Task color: " + blockColor);

                        //search for the task object
                        TaskSchema task = HelloApplication.taskModel.find(LocalDate.now(), blockTitle);
                        System.out.println("Task found: " + task.getName());

                        //load the task info view fxml file
                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("task-info-view.fxml"));

                        //set a custom controller with arguments
                        fxmlLoader.setControllerFactory(c -> new TaskInfoViewController(task));

                        //initialize the data of the new view
                        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                        fxmlLoader.<TaskInfoViewController>getController().initData();

                        //get the current stage
                        Stage stage = (Stage) calendarGrid.getScene().getWindow();

                        //center the view on the user's screen
                        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                        stage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);
                        stage.setY((screenBounds.getHeight() - scene.getHeight()) / 2);

                        //set the new scene
                        stage.setScene(scene);

                    }

                    if(blockColor.equals(freeSlotColor)){
                        //the clicked block is a free slot block
                        System.out.println("Free slot clicked: " + blockTitle);
                        System.out.println("Free slot color: " + blockColor);
                    }

                    DayOfWeek day =  LocalDate.now().getDayOfWeek();
                    System.out.println(day.name());

                }catch (TaskDoesNotExistException e) {
                    System.out.println("Error loading task info view: " + e.getMessage());
                }catch (Exception e){
                    e.printStackTrace();
                    System.out.println("Error while loading the task info view: "+e.getMessage());
                }
            }
        });
    }

    public void handle() {

    }
}
