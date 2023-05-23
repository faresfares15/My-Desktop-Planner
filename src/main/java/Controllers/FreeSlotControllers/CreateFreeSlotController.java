package Controllers.FreeSlotControllers;

import Exceptions.DayDoesNotHaveFreeSlotsException;
import Exceptions.EmptyRequiredFieldException;
import Exceptions.FreeSlotNotFoundException;
import Models.FreeSlot.FreeSlotSchema;
import esi.tp_poo_final.HelloApplication;
import javafx.css.Stylesheet;
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
        datePicker.setValue(LocalDate.now().plusDays(1));
    }

    public void createFreeSlot() {
        try {
            //get the inputs from the view
            ViewInfos infos = new ViewInfos();
            LocalDate date = infos.getDate();
            LocalTime startTime = infos.getStartTime();
            Duration duration = infos.getDuration();
            LocalTime endTime = startTime.plus(duration);
            boolean freeSlotCreated = false;

            if (date.isBefore(LocalDate.now())) throw new Exception("Date cannot be in the past");

            if (date.isEqual(LocalDate.now()) && startTime.isBefore(LocalTime.now()))
                throw new Exception("Start time cannot be in the past");

            if (duration.toMinutes() == 0) throw new Exception("Duration cannot be 0");


            try {
                for (FreeSlotSchema freeSlot : HelloApplication.freeSlotModel.findMany(date)) {
                    if (freeSlot.getStartTime().isBefore(startTime) && freeSlot.getEndTime().isAfter(endTime)
                            && freeSlot.getStartTime().isBefore(endTime) && freeSlot.getEndTime().isAfter(startTime)) {
                        //new start time is inside the free slot
//                        HelloApplication.freeSlotModel.update(date, freeSlot.getStartTime(),)
                        throw new RuntimeException("There is already a free slot in this time zone");
                    }
                    if (freeSlot.getStartTime().isBefore(endTime) && freeSlot.getEndTime().isAfter(endTime)) {
                        //new end time is inside the free slot
                        if (freeSlot.getStartTime().isBefore(startTime) && freeSlot.getEndTime().isAfter(startTime)) {
                            throw new RuntimeException("There is already a free slot in this time zone");
                        } else {
                            HelloApplication.freeSlotModel.update(date, freeSlot.getStartTime(), startTime);
                            freeSlotCreated = true;

                        }
                        //To extend the already existing one
                    }
                    if (freeSlot.getStartTime().isAfter(startTime) && freeSlot.getEndTime().isBefore(endTime) &&
                    freeSlot.getEndTime().isAfter(startTime) && freeSlot.getStartTime().isBefore(endTime)) {
                        // free slot is inside the new free slot
                        //a new larger free slot will be created
                        HelloApplication.freeSlotModel.delete(date, freeSlot.getStartTime());
                        HelloApplication.freeSlotModel.create(date, startTime, endTime);
                        freeSlotCreated = true;

                    }
                    if (freeSlot.getStartTime().isBefore(startTime) && freeSlot.getEndTime().isAfter(startTime)) {
                        if (freeSlot.getEndTime().isBefore(endTime) && freeSlot.getStartTime().isBefore(endTime) && freeSlot.getEndTime().isAfter(startTime)) {
                            //extend the already existing one
                            HelloApplication.freeSlotModel.delete(date, freeSlot.getStartTime());
                            HelloApplication.freeSlotModel.create(date, freeSlot.getStartTime(), endTime);
                            freeSlotCreated = true;

                        } else throw new RuntimeException("There is already a free slot in this time zone");
                    }

                }
                if (!freeSlotCreated) {
                    Duration minimalDuration = HelloApplication.currentUserSettings.getMinimalDuration();
                    if(Duration.between(startTime, endTime).compareTo(minimalDuration) < 0) endTime = startTime.plus(minimalDuration);

                    HelloApplication.freeSlotModel.create(date, startTime, endTime);
                    freeSlotCreated = true;
                }
            } catch (DayDoesNotHaveFreeSlotsException e) {
                //no free slots found to compare with so create it directly
                Duration minimalDuration = HelloApplication.currentUserSettings.getMinimalDuration();
                if(Duration.between(startTime, endTime).compareTo(minimalDuration) < 0) endTime = startTime.plus(minimalDuration);

                HelloApplication.freeSlotModel.create(date, startTime, endTime);
                freeSlotCreated = true;

            } catch (Exception e) {

            }

            if (freeSlotCreated) {
                if (!confirmSchedule("Free slot schedule confirmation", "Free slot can be scheduled in the date and time. Do you confirm the operation?")) {
                    System.out.println("Operation cancelled");
                    return;
                }
                showSuccessMessage("Free slot created successfully");
            } else {
                showErrorMessage("Free slot couldn't be created");
            }

//            //create the free slot
//            HelloApplication.freeSlotModel.create(infos.getDate(), infos.getStartTime(), infos.getStartTime().plus(infos.getDuration()));
//


        } catch (Exception e) {
            showErrorMessage(e.getMessage());
        }
    }

    public void moveToCalendarView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("calendar-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 840, 500);
        Stage stage = (Stage) viewTitle.getScene().getWindow();
        stage.setTitle("Calendar");

        //center the view on the user's screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - scene.getHeight()) / 2);

        stage.setScene(scene);
    }

    //helper methods
    private boolean confirmSchedule(String title, String message) {
        Alert confirmationMessage = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationMessage.setContentText(message);
        confirmationMessage.setHeaderText(title);
        confirmationMessage.setTitle(title);
        Optional<ButtonType> clickedButton = confirmationMessage.showAndWait();

        return clickedButton.get() == ButtonType.OK;
    }

    private void showErrorMessage(String message) {
        Alert errorMessage = new Alert(Alert.AlertType.ERROR);
        errorMessage.setContentText(message);
        errorMessage.setHeaderText("Error");
        errorMessage.setTitle("Error");
        errorMessage.showAndWait();
    }

    private void showSuccessMessage(String message) {
        Alert successMessage = new Alert(Alert.AlertType.INFORMATION);
        successMessage.setContentText(message);
        successMessage.setHeaderText("Success");
        successMessage.setTitle("Success");
        successMessage.showAndWait();
    }

    //internal class to abstract inputs from methods
    private class ViewInfos {
        LocalTime getStartTime() {
            return LocalTime.of(startTimeHoursSpinner.getValue(), startTimeMinutesSpinner.getValue());
        }

        public Duration getDuration() throws EmptyRequiredFieldException {
            //check if an input is not provided
            if (durationHoursSpinner.getValue() == null && durationMinutesSpinner.getValue() == null)
                throw new EmptyRequiredFieldException("Duration is required");
            return Duration.ofHours(durationHoursSpinner.getValue()).plusMinutes(durationMinutesSpinner.getValue());
        }

        public LocalDate getDate() throws EmptyRequiredFieldException {
            //check if an input is not provided
            if (datePicker.getValue() == null) throw new EmptyRequiredFieldException("Date is required");
            return datePicker.getValue();
        }
    }
}
