package Controllers.CalendarControllers;

import Controllers.TaskControllers.TaskInfoViewController;
import Exceptions.TaskDoesNotExistException;
import Models.FreeSlot.FreeSlotSchema;
import Models.Task.TaskSchema;
import esi.tp_poo_final.HelloApplication;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.TreeMap;

public class ShowCalendarController{
    private static LocalDate currentWeekStartDate = LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().getValue() -1);
    private final int NUM_HOURS = 24;
    private final int NUM_DAYS = 7;
    private final Color taskColor = Color.RED;
    private final Color freeSlotColor = Color.GREEN;
    private final double hourBlockHeight = 30;
    private final double minuteBlockHeight = hourBlockHeight / 60;
    @FXML
    GridPane calendarGrid;
    @FXML
    ScrollPane scrollPane;
    @FXML
    Text todayDate;
    @FXML
    Text startDateOfCurrentWeek;
    @FXML
    public void initialize(){

        //set the date text
        todayDate.setText(LocalDate.now().getMonth().toString()+" "+LocalDate.now().getDayOfMonth()+", "+LocalDate.now().getYear());

        //set the date text of the beginning ot current the week
        startDateOfCurrentWeek.setText(currentWeekStartDate.getMonth().toString()+" "+currentWeekStartDate.getYear());

        loadCalendar();

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
                    int columnIndex = 0;
                    int taskId = 0;
                    //TODO: the id property of the stack pane is set, now change the search code to use the id

                    //get the color and the title of the clicked block (if it is a block)
                    if(clickedItem instanceof StackPane){
                        //get the stack pane object
                        StackPane taskBlock = (StackPane) clickedItem;
                        taskId = Integer.parseInt(taskBlock.getId());
                        columnIndex = GridPane.getColumnIndex(taskBlock);

                        //get the color of its rectangle
                        Rectangle rectangle = (Rectangle) taskBlock.getChildren().get(0);
                        blockColor = (Color) rectangle.getFill();

                        //get the content of the text
                        blockTitle = ((Text) taskBlock.getChildren().get(1)).getText();
                    }
                    else if(clickedItem instanceof Rectangle){
                        //get the rectangle's color
                        Rectangle rectangle = (Rectangle) clickedItem;
                        blockColor = (Color) rectangle.getFill();

                        //get the stack pane object to access the text
                        StackPane taskBlock = (StackPane) rectangle.getParent();
                        taskId = Integer.parseInt(taskBlock.getId());
                        columnIndex = GridPane.getColumnIndex(taskBlock);

                        //get the text form the stack pane container
                        Text text = (Text) taskBlock.getChildren().get(1); //the text is at index 1
                        blockTitle = text.getText();
                    }
                    else if(clickedItem instanceof Text text){
                        //get the stack pane object to access the rectangle
                        StackPane taskBlock = (StackPane) text.getParent();
                        taskId = Integer.parseInt(taskBlock.getId());
                        columnIndex = GridPane.getColumnIndex(taskBlock);

                        //get the rectangle's color
                        Rectangle rectangle = (Rectangle) taskBlock.getChildren().get(0); //the rectangle is at index 0
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
                        TaskSchema task = HelloApplication.taskModel.find(currentWeekStartDate.plusDays(columnIndex -1), taskId);
                        //TODO: change it to get the id
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

                }catch (TaskDoesNotExistException e) {
                    System.out.println("Error loading task info view: " + e.getMessage());
                }catch (Exception e){
                    e.printStackTrace();
                    System.out.println("Error while loading the task info view: "+e.getMessage());
                }
            }
        });
    }

    public void goToCurrentWeek(){
        //clear the calendar view
        calendarGrid.getChildren().retainAll(calendarGrid.getChildren().get(0));

        //set the new start date of the week to the current week
        setCurrentWeekStartDate(LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().getValue() -1));

        //load the calendar
        loadCalendar();

        calendarGrid.setGridLinesVisible(true);
    }
    public void goToNextWeek(){
        //clear the calendar view, to construct it again
        calendarGrid.getChildren().retainAll(calendarGrid.getChildren().get(0));

        //set the new start date of the week to the next week
        setCurrentWeekStartDate(currentWeekStartDate.plusDays(7));

        //load the calendar
        loadCalendar();

        calendarGrid.setGridLinesVisible(true);

    }
    public void goToPreviousWeek(){
        //clear the calendar view, to construct it again
        calendarGrid.getChildren().retainAll(calendarGrid.getChildren().get(0));

        //set the new start date of the week to the previous week
        setCurrentWeekStartDate(currentWeekStartDate.minusDays(7));

        //load the calendar
        loadCalendar();

        calendarGrid.setGridLinesVisible(true);

    }

    public void moveToScheduleFreeSlotView() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("plan-free-slot-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = (Stage) calendarGrid.getScene().getWindow();
        stage.setTitle("Plan a free slot");

        //center the view on the user's screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - scene.getHeight()) / 2);

        stage.setScene(scene);
    }
    public void moveToScheduleTaskView()  throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("plan-task-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 676, 486);
        Stage stage = (Stage) calendarGrid.getScene().getWindow();
        stage.setTitle("Plan a task");

        //center the view on the user's screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - scene.getHeight()) / 2);

        stage.setScene(scene);
    }
    public void moveToScheduleSetOfTaskView() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("plan-set-of-tasks-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        Stage stage = (Stage) calendarGrid.getScene().getWindow();
        stage.setTitle("Plan set of tasks");

        //center the view on the user's screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - scene.getHeight()) / 2);

        stage.setScene(scene);
    }

    //helper methods
    private StackPane createBlock(double width, Color color, String text, DayOfWeek day, LocalTime startTime, Duration duration){
        //create a rectangular background
        double blockHeight = duration.toMinutes() * minuteBlockHeight;
        Rectangle background = new Rectangle(70, blockHeight, color); //width, height, fill color

        //create a text label for the task name
        Text blockText = new Text(text);
        blockText.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        // Create a nested pane to hold the rectangle and text
        StackPane block = new StackPane(background, blockText);

        block.setMaxHeight(blockHeight);
        block.setStyle("-fx-cursor: hand;");

        // Translate the task block to the bottom, according the
        double minutesOffset = startTime.getMinute() * minuteBlockHeight;
        block.setTranslateY(minutesOffset);

        //set the position of the task block in the view
        GridPane.setColumnIndex(block, day.getValue()); //corresponds to the day
        GridPane.setRowIndex(block, startTime.getHour()+1); //corresponds to the time

        //set the row span of the task block
        int blockRowSpan = startTime.plus(duration).getHour() - startTime.getHour() +1;
        GridPane.setRowSpan(block, blockRowSpan); //corresponds to the duration

        return block;
    }
    private void loadCalendar(){

        //set the date text of the beginning ot current the week
        startDateOfCurrentWeek.setText(currentWeekStartDate.getMonth().toString()+" "+currentWeekStartDate.getYear());

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
            Label dayLabel = new Label(DayOfWeek.of(day+1).toString());
            Label dayNumber = new Label(String.valueOf(currentWeekStartDate.plusDays(day).getDayOfMonth()));
            VBox dayBlock = new VBox(dayLabel, dayNumber);
            dayBlock.setAlignment(Pos.CENTER);
            GridPane.setColumnIndex(dayBlock, day + 1);
            calendarGrid.getChildren().add(dayBlock); //add the label to the grid
        }

        //Add rectangles for time slots
        for (int day = 0; day < NUM_DAYS; day++) {
            for (int hour = 0; hour < NUM_HOURS; hour++) {
                Rectangle rectangle = new Rectangle(70, hourBlockHeight, Color.BLUE); //width, height, fill color
                GridPane.setColumnIndex(rectangle, day + 1);
                GridPane.setRowIndex(rectangle, hour + 1);
                GridPane.setValignment(rectangle, VPos.TOP);
                calendarGrid.getChildren().add(rectangle);
            }
        }

        try{

            //================ show the free slots of the current week ===========================//
            TreeMap<LocalDate, ArrayList<FreeSlotSchema>> weekFreeSlots = HelloApplication.freeSlotModel.findMany(currentWeekStartDate, currentWeekStartDate.plusDays(6));
            for(LocalDate day: weekFreeSlots.keySet()){
                ArrayList<FreeSlotSchema> dayFreeSlotsList = weekFreeSlots.get(day);
                for(FreeSlotSchema freeSlot: dayFreeSlotsList){
                    StackPane freeSlotBlock = createBlock(70, freeSlotColor, "Free Slot", freeSlot.getDate().getDayOfWeek(), freeSlot.getStartTime(), freeSlot.getDuration());
                    calendarGrid.getChildren().add(freeSlotBlock);
                }
            }

            //================ show the tasks of the current week ================================//

            //get tasks from the current week
            TreeMap<LocalDate, ArrayList<TaskSchema>> weekTasks = HelloApplication.taskModel.findMany(currentWeekStartDate, currentWeekStartDate.plusDays(6));

            //iterate over the days of the week
            for(LocalDate day: weekTasks.keySet()){

                //get the tasks of "day"
                ArrayList<TaskSchema> dayTasksList = weekTasks.get(day);

                // create blocks for the tasks
                for(TaskSchema task: dayTasksList){

                    StackPane taskBlock = createBlock(70, taskColor, task.getName(), task.getDate().getDayOfWeek(), task.getStartTime(), task.getDuration());
                    taskBlock.setId(String.valueOf(task.getId()));
                    calendarGrid.getChildren().add(taskBlock);

                }
            }


        }catch (Exception e){

        }
    }

    private static void setCurrentWeekStartDate(LocalDate currentWeekStartDate) {
        ShowCalendarController.currentWeekStartDate = currentWeekStartDate;
    }
}
