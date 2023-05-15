package Controllers.CalendarControllers;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ShowCalendarController{

    private final int NUM_HOURS = 24;
    private final int NUM_DAYS = 7;
    private final Color taskColor = Color.RED;
    private final Color freeSlotColor = Color.GREEN;
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
                Rectangle rectangle = new Rectangle();
                rectangle.setWidth(50);
                rectangle.setHeight(30);
                rectangle.setFill(Color.BLUE); // Change color as needed
                GridPane.setColumnIndex(rectangle, day + 1);
                GridPane.setRowIndex(rectangle, hour + 1);
                GridPane.setValignment(rectangle, VPos.TOP);
                calendarGrid.getChildren().add(rectangle);
            }
        }

        // Add rectangles for tasks
        // Replace this with your own logic to determine task positions and colors
        Rectangle taskRectangle = new Rectangle(50, 30, taskColor); //width, height, fill color

        Text text = new Text("Hello");
        text.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        // Create a nested pane to hold the rectangle and text
        StackPane taskBlock = new StackPane(taskRectangle, text);

        GridPane.setColumnIndex(taskBlock, 1);
        GridPane.setRowIndex(taskBlock, 3);
        GridPane.setRowSpan(taskBlock, 2);
        calendarGrid.getChildren().add(taskBlock);


        Rectangle task2Rectangle = new Rectangle(50, 30, freeSlotColor); //width, height, fill color

        Text text2 = new Text("Hi");
        text.setFont(Font.font("Arial", FontWeight.BOLD, 14));

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
                }

                if(blockColor.equals(freeSlotColor)){
                    //the clicked block is a free slot block
                    System.out.println("Free slot clicked: " + blockTitle);
                    System.out.println("Free slot color: " + blockColor);
                }
            }
        });
    }

    public void handle() {

    }
}
