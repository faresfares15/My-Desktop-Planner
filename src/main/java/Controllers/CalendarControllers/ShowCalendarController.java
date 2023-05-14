package Controllers.CalendarControllers;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ShowCalendarController implements EventHandler {

    private final int NUM_HOURS = 24;
    private final int NUM_DAYS = 7;
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
                rectangle.setHeight(20);
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
    }
    @Override
    public void handle(Event event) {

    }
}
