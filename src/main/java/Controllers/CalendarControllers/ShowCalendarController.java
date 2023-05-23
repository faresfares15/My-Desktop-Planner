package Controllers.CalendarControllers;

import Controllers.FreeSlotControllers.FreeSlotInfoViewController;
import Controllers.ProjectControllers.ViewProjectsController;
import Controllers.TaskControllers.TaskInfoViewController;
import Models.FreeSlot.FreeSlotSchema;
import Models.Task.DecomposableTaskSchema;
import Models.Task.SimpleTaskSchema;
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
import java.util.Objects;
import java.util.TreeMap;

public class ShowCalendarController{
    private static LocalDate currentWeekStartDate = LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().getValue() -1);
    private final int NUM_HOURS = 24;
    private final int NUM_DAYS = 7;
    private final Color defaultTaskColor = Color.RED;
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
                    LocalTime blockStartTime = LocalTime.of(0,0);
                    double blockId = 0;

                    //get the color and the title of the clicked block (if it is a block)
                    if(clickedItem instanceof StackPane){
                        //get the stack pane object
                        StackPane taskBlock = (StackPane) clickedItem;

                        //get the start time of the block
                        double startMinutes = taskBlock.getTranslateY() / minuteBlockHeight;
                        double startHours = GridPane.getRowIndex(taskBlock) - 1;
                        blockStartTime = LocalTime.of((int) startHours, (int) startMinutes);

                        if(Objects.equals(taskBlock.getId(), "subtask")){
                            blockId = 0.1; //arbitrary id value for a subtask
                        }else{
                            blockId = Integer.parseInt(taskBlock.getId());
                        }

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

                        //get the start time of the block
                        double startMinutes = taskBlock.getTranslateY() / minuteBlockHeight;
                        double startHours = GridPane.getRowIndex(taskBlock) - 1;
                        blockStartTime = LocalTime.of((int) startHours, (int) startMinutes);

                        if(Objects.equals(taskBlock.getId(), "subtask")){
                            blockId = 0.1; //arbitrary value
                        }else{
                            blockId = Integer.parseInt(taskBlock.getId());
                        }
                        columnIndex = GridPane.getColumnIndex(taskBlock);

                        //get the text form the stack pane container
                        Text text = (Text) taskBlock.getChildren().get(1); //the text is at index 1
                        blockTitle = text.getText();
                    }
                    else if(clickedItem instanceof Text text){
                        //get the stack pane object to access the rectangle
                        StackPane taskBlock = (StackPane) text.getParent();


                        //get the start time of the block
                        double startMinutes = taskBlock.getTranslateY() / minuteBlockHeight;
                        double startHours = GridPane.getRowIndex(taskBlock) - 1;
                        blockStartTime = LocalTime.of((int) startHours, (int) startMinutes);


                        if(Objects.equals(taskBlock.getId(), "subtask")){
                            blockId = 0.1; //arbitrary value
                        }else{
                            blockId = Integer.parseInt(taskBlock.getId());
                        }
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

                    if(blockColor.equals(freeSlotColor)){
                        //the clicked block is a free slot block
                        System.out.println("Free slot clicked: " + blockTitle);
                        System.out.println("Free slot color: " + blockColor);

                        //get the date of the clicked block
                        LocalDate clickedDate = currentWeekStartDate.plusDays(columnIndex-1);

                        //get the start time of the clicked block

                        //find the free slot
                        FreeSlotSchema freeSlot = HelloApplication.freeSlotModel.find(clickedDate, blockStartTime);

                        showFreeSlotInfo(freeSlot);

                    }else{
                        //the clicked block is a task block

                        TaskSchema clickedTask = null;
                        boolean isSubtask = false;

                        //search for the task object

                        //if the task is a subtask, search in the subtasks list of all decomposable tasks
                        if(blockId == 0.1){
                            //the task is a subtask ==> search in the subtasks list of all decomposable tasks
                            isSubtask = true;
                            ArrayList<TaskSchema> decomposedTasks = HelloApplication.taskModel.findAll("DecomposableTaskSchema");

                            for(TaskSchema task: decomposedTasks){
                                DecomposableTaskSchema decomposableTask = (DecomposableTaskSchema) task;
                                for(TaskSchema subTask: decomposableTask.getSubTasks()){
                                    //check if the subtask's name is the same as the clicked block's title
                                    if(subTask.getName().equals(blockTitle)){
                                        clickedTask = subTask;
                                        break;
                                    }
                                }
                            }
                        }else{
                            //else, search in the tasks list of all simple tasks
                            clickedTask = HelloApplication.taskModel.find(currentWeekStartDate.plusDays(columnIndex -1), (int) blockId);
                        }

                        //load the task info view
                        showTaskInfo(clickedTask, isSubtask);

                    }

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
    public void showTaskInfo(TaskSchema task, boolean isSubtask) throws IOException {
        //load the task info view fxml file
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("task-info-view.fxml"));

        //set a custom controller with arguments
        fxmlLoader.setControllerFactory(c -> new TaskInfoViewController(task, isSubtask));

        //initialize the data of the new view
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        fxmlLoader.<TaskInfoViewController>getController().initData();

        //get the current stage
        Stage stage = (Stage) calendarGrid.getScene().getWindow();
        stage.setTitle("Task info");

        //center the view on the user's screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - scene.getHeight()) / 2);

        //set the new scene
        stage.setScene(scene);
    }
    public void showFreeSlotInfo(FreeSlotSchema freeslot) throws IOException{
        //load the free slot info view fxml file
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("free-slot-info-view.fxml"));

        //set a custom controller with arguments
        fxmlLoader.setControllerFactory(c -> new FreeSlotInfoViewController(freeslot));

        //initialize the data of the new view
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        fxmlLoader.<FreeSlotInfoViewController>getController().initData();

        //get the current stage
        Stage stage = (Stage) calendarGrid.getScene().getWindow();
        stage.setTitle("Free slot info");

        //center the view on the user's screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - scene.getHeight()) / 2);

        //set the new scene
        stage.setScene(scene);
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
    public void viewProjects() throws IOException{

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view-projects-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        Stage stage = (Stage) calendarGrid.getScene().getWindow();
        stage.setTitle("Projects");

        ViewProjectsController viewProjectsController = fxmlLoader.getController();
        viewProjectsController.initData();

        //center the view on the user's screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - scene.getHeight()) / 2);

        stage.setScene(scene);
    }
    public void updateSettings() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("change-settings-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 450);
        Stage stage = (Stage) calendarGrid.getScene().getWindow();
        stage.setTitle("Change settings");


        //center the view on the user's screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - scene.getHeight()) / 2);

        stage.setScene(scene);

    }

    //=========================================== HELPERS =============================================================//
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
                    freeSlotBlock.setId("subtask"); //just to say it's not a task
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
                    if(task instanceof DecomposableTaskSchema){
                        //task is decomposable, skip it because it will be displayed later
                        continue;
                    }

                    //task is not decomposable, so it's simple
                    displayTask(task, false); //false because it's not a subtask

                }
            }

            //================ search for decomposed tasks that have sub tasks in the current week ================================//
            ArrayList<TaskSchema> decomposedTasks = HelloApplication.taskModel.findAll("DecomposableTaskSchema");

            for(TaskSchema task: decomposedTasks){
                DecomposableTaskSchema decomposableTask = (DecomposableTaskSchema) task;
                ArrayList<SimpleTaskSchema> subTasks = decomposableTask.getSubTasks();
                for(int i = 1; i < subTasks.size(); i++){
                    //task is decomposable, so it has subtasks

                    //check if the subtask's date is in the current week
                    if(subTasks.get(i).getDate().isBefore(currentWeekStartDate) || subTasks.get(i).getDate().isAfter(currentWeekStartDate.plusDays(6))){
                        continue;
                    }

                    displayTask(subTasks.get(i), true); //true because it's a subtask
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void setCurrentWeekStartDate(LocalDate currentWeekStartDate) {
        ShowCalendarController.currentWeekStartDate = currentWeekStartDate;
    }
    private void displayTask(TaskSchema task, boolean isSubTask){
        //task is not decomposable, so it's simple
        Color taskColor = null;

        //check if the task has a category
        if(Objects.equals(task.getCategory().getName(), "")){
            taskColor = this.defaultTaskColor;
        }else{
            try{
                taskColor = HelloApplication.categoryModel.find(task.getCategory().getName()).getColor();
            }catch (Exception e){
                taskColor = this.defaultTaskColor;
            }
        }

        StackPane taskBlock = createBlock(70, taskColor, task.getName(), task.getDate().getDayOfWeek(), task.getStartTime(), task.getDuration());

        //if the task is a subtask, set the id of the task block to "subtask"
        if(isSubTask){
            taskBlock.setId("subtask");
        }else{
            //set the id of the task block to the id of the task
            taskBlock.setId(String.valueOf(task.getId()));
        }

        calendarGrid.getChildren().add(taskBlock);
    }
}
