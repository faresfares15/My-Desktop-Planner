package Controllers.UserControllers;

import Exceptions.EmptyRequiredFieldException;
import Models.Calendar.Settings;
import esi.tp_poo_final.HelloApplication;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.Duration;

public class ChangeSettingsController  implements EventHandler<ActionEvent> {

    @FXML
    Spinner<Integer> minDurationHoursSpinner;
    @FXML
    Spinner<Integer> minDurationMinutesSpinner;
    @FXML
    Spinner<Integer> minTasksPerDaySpinner;
    final Settings currentUserSettings = HelloApplication.currentUserSettings;
    @FXML
    ComboBox<DayOfWeek> firstDayOfWeekComboBox;

    @FXML
    public void initialize(){
        //Get the current settings of the current user and put them in the view ao that if the user doesn't change them they'll remain the same
        minDurationHoursSpinner.getValueFactory().setValue((int) currentUserSettings.getMinimalDuration().toHours());
        minDurationMinutesSpinner.getValueFactory().setValue((int) currentUserSettings.getMinimalDuration().toMinutes() % 60);
        minTasksPerDaySpinner.getValueFactory().setValue(currentUserSettings.getMinimalNumberOfTasksPerDay());
//        firstDayOfWeekComboBox.getItems().;
    }
    @Override
    public void handle(ActionEvent event) {
        //First start by getting the values from the view with the ViewInfo class
        ViewInfo viewInfo = new ViewInfo();
        try {
            //Then set the values in the model
            currentUserSettings.setMinimalDuration(viewInfo.getMinDuration());
            currentUserSettings.setMinimalNumberOfTasksPerDay(viewInfo.getMinTasksPerDay());
            currentUserSettings.setStartOfTheWeek(viewInfo.getFirstDayOfWeek());
            //Since it's a reference so changing it here will also change it in the model
            moveToCalendarView();
        } catch (EmptyRequiredFieldException e) {
            //If there is an error, show it to the user
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Couldn't load the calendar view");
        }
    }

    private  class ViewInfo{
        public Duration getMinDuration() throws EmptyRequiredFieldException {
            if (minDurationHoursSpinner.getValue() == null || minDurationMinutesSpinner.getValue() == null)
                throw new EmptyRequiredFieldException("Duration is required");
            if (minDurationHoursSpinner.getValue() == 0 && minDurationMinutesSpinner.getValue() == 0)
                throw new EmptyRequiredFieldException("Duration is required");
            return Duration.ofHours(minDurationHoursSpinner.getValue()).plusMinutes(minDurationMinutesSpinner.getValue());
        }
        public DayOfWeek getFirstDayOfWeek() throws EmptyRequiredFieldException {
            if (firstDayOfWeekComboBox.getValue() == null)
                throw new EmptyRequiredFieldException("First day of the week is required");
            return firstDayOfWeekComboBox.getValue();
        }
        public int getMinTasksPerDay() throws EmptyRequiredFieldException {
            if (minTasksPerDaySpinner.getValue() == null)
                throw new EmptyRequiredFieldException("Minimal number of tasks per day is required");
            return minTasksPerDaySpinner.getValue();
        }
    }
    public void moveToCalendarView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("calendar-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 840, 400);
        Stage stage = (Stage) minDurationHoursSpinner.getScene().getWindow();
        stage.setTitle("Calendar");

        //center the view on the user's screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - scene.getHeight()) / 2);

        stage.setScene(scene);
    }
}
