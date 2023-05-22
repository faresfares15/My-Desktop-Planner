package Controllers.UserControllers;

import Exceptions.EmptyRequiredFieldException;
import Models.Calendar.Settings;
import esi.tp_poo_final.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Duration;

public class ChangeSettingsController{
    @FXML
    Spinner<Integer> minDurationHoursSpinner;
    @FXML
    Spinner<Integer> minDurationMinutesSpinner;
    @FXML
    Spinner<Integer> minTasksPerDaySpinner;
    private final Settings currentUserSettings = HelloApplication.currentUserSettings;

    @FXML
    public void initialize(){
        //Get the current settings of the current user and put them in the view ao that if the user doesn't change them they'll remain the same

        //set the minDurationHoursSpinner
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
        valueFactory.setValue((int) currentUserSettings.getMinimalDuration().toHours());
        minDurationHoursSpinner.setValueFactory(valueFactory);

        //set the minDurationMinutesSpinner
        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        valueFactory.setValue((int) currentUserSettings.getMinimalDuration().toMinutes() % 60);
        minDurationMinutesSpinner.setValueFactory(valueFactory);

        //set the minTasksPerDaySpinner
        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        valueFactory.setValue(currentUserSettings.getMinimalNumberOfTasksPerDay());
        minTasksPerDaySpinner.setValueFactory(valueFactory);
    }
    public void submit() {
        //First start by getting the values from the view with the ViewInfo class
        ViewInfo viewInfo = new ViewInfo();
        try {
            //Then set the values in the model
            currentUserSettings.setMinimalDuration(viewInfo.getMinDuration());
            currentUserSettings.setMinimalNumberOfTasksPerDay(viewInfo.getMinTasksPerDay());

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
        public int getMinTasksPerDay() throws EmptyRequiredFieldException {
            if (minTasksPerDaySpinner.getValue() == null)
                throw new EmptyRequiredFieldException("Minimal number of tasks per day is required");
            return minTasksPerDaySpinner.getValue();
        }
    }
    public void moveToCalendarView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("calendar-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 840, 500);
        Stage stage = (Stage) minDurationHoursSpinner.getScene().getWindow();
        stage.setTitle("Calendar");

        //center the view on the user's screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - scene.getHeight()) / 2);

        stage.setScene(scene);
    }
}
