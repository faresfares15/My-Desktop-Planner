package Controllers.FreeSlotControllers;

import esi.tp_poo_final.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class CreateFreeSlotController {
    //If the user wants to create a new free slot, this controller will be called.
    @FXML
    Label viewTitle;
    @FXML
    Spinner<Integer> startTimeHoursSpinner;
    @FXML
    Spinner<Integer> startTimeMinutesSpinner;
    @FXML
    Spinner<Integer> durationHoursSpinner;
    @FXML
    Spinner<Integer> durationMinutesSpinner;
    @FXML
    DatePicker datePicker;
    @FXML
    public void initialize() {
        //This method will be called when the view is loaded

        //set the view title


        //setting the start time hours spinner
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
        valueFactory.setValue(LocalTime.now().getHour());
        startTimeHoursSpinner.setValueFactory(valueFactory);

        //setting the start time minutes spinner
        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        valueFactory.setValue(LocalTime.now().getMinute());
        startTimeMinutesSpinner.setValueFactory(valueFactory);

        //setting the duration hours spinner
        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
        valueFactory.setValue(0);
        durationHoursSpinner.setValueFactory(valueFactory);

        //setting the duration minutes spinner
        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        valueFactory.setValue(0);
        durationMinutesSpinner.setValueFactory(valueFactory);

        //setting the date picker
        datePicker.setValue(LocalDate.now());
    }

    //internal class to abstract inputs from methods
    private class ViewInfos {
        LocalTime getStartTime() {
            return LocalTime.of(startTimeHoursSpinner.getValue(), startTimeMinutesSpinner.getValue());
        }

        public Duration getDuration() {
            return Duration.ofHours(durationHoursSpinner.getValue()).plusMinutes(durationMinutesSpinner.getValue());
        }
        public LocalDate getDate() {
            return datePicker.getValue();
        }
    }
    public void createFreeSlot(){
        //get the inputs from the view
        ViewInfos infos = new ViewInfos();

        //TODO validate the inputs (e.g time in the past) and check if free slot can be created
        boolean success = true;
        if(!success){
            showErrorMessage("Free slot cannot be created");
            return;
        }

        if(!isConfirmed()){
            System.out.println("Operation cancelled");
            return;
        }

        //create the free slot
        HelloApplication.freeSlotModel.create(infos.getDate(), infos.getStartTime(), infos.getStartTime().plus(infos.getDuration()));

        showSuccessMessage("Free slot created successfully");
    }
    public void moveToCalendarView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("calendar-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 840, 400);
        Stage stage = (Stage) viewTitle.getScene().getWindow();
        stage.setTitle("Calendar");

        //center the view on the user's screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - scene.getHeight()) / 2);

        stage.setScene(scene);
    }

    //helper methods
    private boolean isConfirmed(){
        Alert confirmationMessage = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationMessage.setContentText("Free slot can be scheduled in the date and time. Do you confirm the operation?");
        confirmationMessage.setHeaderText("Free slot scheduling confirmation");
        confirmationMessage.setTitle("Free slot confirmation");
        Optional<ButtonType> clickedButton = confirmationMessage.showAndWait();

        return clickedButton.get() == ButtonType.OK;
    }
    private void showErrorMessage(String message){
        Alert errorMessage = new Alert(Alert.AlertType.ERROR);
        errorMessage.setContentText(message);
        errorMessage.setHeaderText("Error");
        errorMessage.setTitle("Error");
        errorMessage.showAndWait();
    }

    private void showSuccessMessage(String message){
        Alert successMessage = new Alert(Alert.AlertType.INFORMATION);
        successMessage.setContentText(message);
        successMessage.setHeaderText("Success");
        successMessage.setTitle("Success");
        successMessage.showAndWait();
    }
}
