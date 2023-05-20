package Controllers.TaskControllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class TaskDecompositionController {
    @FXML
    private VBox blocksContainer;
    @FXML
    private Button submitButton;
    @FXML
    Spinner<Integer> startTimeHoursSpinner;
    @FXML
    Spinner<Integer> startTimeMinutesSpinner;
    @FXML
    Spinner<Integer> endTimeHoursSpinner;
    @FXML
    Spinner<Integer> endTimeMinutesSpinner;
    @FXML
    DatePicker datePicker;
    @FXML
    Button addBlockButton;
    @FXML
    public void initialize(){
        //setting the start time hours spinner
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
        valueFactory.setValue(LocalTime.now().getHour());
        startTimeHoursSpinner.setValueFactory(valueFactory);

        //setting the start time minutes spinner
        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        valueFactory.setValue(LocalTime.now().getMinute());
        startTimeMinutesSpinner.setValueFactory(valueFactory);

        //setting the end time hours spinner
        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
        valueFactory.setValue(LocalTime.now().getHour());
        endTimeHoursSpinner.setValueFactory(valueFactory);

        //setting the end time minutes spinner
        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        valueFactory.setValue(LocalTime.now().getMinute());
        endTimeMinutesSpinner.setValueFactory(valueFactory);

        //setting the date picker
        datePicker.setValue(LocalDate.now());
    }
    protected class ViewInfos{
        LocalTime getStartTime(){
            //check if input is provided
            if(startTimeHoursSpinner.getValue() == null || startTimeMinutesSpinner.getValue() == null) throw new NullPointerException("Start time not provided");
            return LocalTime.of(startTimeHoursSpinner.getValue(), startTimeMinutesSpinner.getValue());
        }
        LocalTime getEndTime(){
            //check if input is provided
            if(endTimeHoursSpinner.getValue() == null || endTimeMinutesSpinner.getValue() == null) throw new NullPointerException("End time not provided");
            return LocalTime.of(endTimeHoursSpinner.getValue(), endTimeMinutesSpinner.getValue());
        }
        LocalDate getDate(){
            //check if input is provided
            if(datePicker.getValue() == null) throw new NullPointerException("Date not provided");
            return datePicker.getValue();
        }
    }
    private ArrayList<TaskBlock> taskBlocks = new ArrayList<>();
    protected class TaskBlock{
        private LocalTime startTime;
        private LocalTime endTime;
        private LocalDate date;

        public void setStartTime(LocalTime startTime) {
            this.startTime = startTime;
        }

        public void setEndTime(LocalTime endTime) {
            this.endTime = endTime;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public LocalTime getStartTime() {
            return startTime;
        }

        public LocalTime getEndTime() {
            return endTime;
        }

        public LocalDate getDate() {
            return date;
        }
    }

    public void addBlock() {
        try{

            //get the inputs
            ViewInfos viewInfos = new ViewInfos();
            LocalTime startTime = viewInfos.getStartTime();
            LocalTime endTime = viewInfos.getEndTime();
            LocalDate date = viewInfos.getDate();

            //validate the inputs
            if(startTime.isAfter(endTime)) throw new Exception("Start time is after end time");
            if(date.isBefore(LocalDate.now())) throw new Exception("Date is before today");
            if(date.isEqual(LocalDate.now()) && startTime.isBefore(LocalTime.now())) throw new Exception("Start time is before current time");

            //add the block to the view
            blocksContainer.getChildren().add(createTaskBlockView(startTime, endTime, date));

        }catch (Exception e){
            showErrorMessage(e.getMessage());
            e.printStackTrace();
        }
    }
    public void submitDecomposition() {
        //get the texts from the blocks container
        blocksContainer.getChildren().forEach((block) -> {
            //get the texts from the block
            Text startTimeText = (Text) ((HBox) block).getChildren().get(0);
            Text endTimeText = (Text) ((HBox) block).getChildren().get(1);
            Text dateText = (Text) ((HBox) block).getChildren().get(2);

            //create the task block object
            TaskBlock taskBlock = new TaskBlock();
            taskBlock.setStartTime(LocalTime.parse(startTimeText.getText()));
            taskBlock.setEndTime(LocalTime.parse(endTimeText.getText()));
            taskBlock.setDate(LocalDate.parse(dateText.getText()));

            //add the task block to the array list
            taskBlocks.add(taskBlock);
        });

        submitButton.getScene().getWindow().hide();
    }

    public ArrayList<TaskBlock> getTaskBlocks() {
        return taskBlocks;
    }

    private HBox createTaskBlockView(LocalTime startTime, LocalTime endTime, LocalDate date){
        //create the horizontal box instance
        HBox taskBlockView = new HBox();

        //add the texts to the horizontal box
        taskBlockView.getChildren().add(new Text(startTime.toString()));
        taskBlockView.getChildren().add(new Text(endTime.toString()));
        taskBlockView.getChildren().add(new Text(date.toString()));

        //add spacing
        taskBlockView.setSpacing(10);

        //return the horizontal box
        return taskBlockView;
    }
    private void showErrorMessage(String message){
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
}
