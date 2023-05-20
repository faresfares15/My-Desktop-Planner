package Controllers.TaskControllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

import Controllers.TaskControllers.PlanTaskController.SubTaskBlock;

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
    private ArrayList<SubTaskBlock> subTaskBlocks = new ArrayList<>();

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


            //create the corresponding task block object
            SubTaskBlock subTaskBlock = new SubTaskBlock(date, startTime, endTime);
            subTaskBlock.setStartTime(startTime);
            subTaskBlock.setEndTime(endTime);
            subTaskBlock.setDate(date);

            //verify that the new block doesn't overlap with a previous block
            for(SubTaskBlock otherBlock : subTaskBlocks){
                if(otherBlock.getDate().isEqual(subTaskBlock.getDate())){
                    if(otherBlock.getStartTime().isBefore(subTaskBlock.getStartTime()) && otherBlock.getEndTime().isAfter(subTaskBlock.getStartTime())) throw new Exception("Block overlaps with another block");
                    if(otherBlock.getStartTime().isBefore(subTaskBlock.getEndTime()) && otherBlock.getEndTime().isAfter(subTaskBlock.getEndTime())) throw new Exception("Block overlaps with another block");
                    if(otherBlock.getStartTime().isAfter(subTaskBlock.getStartTime()) && otherBlock.getEndTime().isBefore(subTaskBlock.getEndTime())) throw new Exception("Block overlaps with another block");
                }
            }

            //add the block to the view
            blocksContainer.getChildren().add(createTaskBlockView(startTime, endTime, date));

            //add the task block to the array list
            subTaskBlocks.add(subTaskBlock);

        }catch (Exception e){
            showErrorMessage(e.getMessage());
            e.printStackTrace();
        }
    }
    public void submitDecomposition() {
        //sort the tasks blocks by date
        Collections.sort(subTaskBlocks);

        submitButton.getScene().getWindow().hide();
    }

    public ArrayList<SubTaskBlock> getTaskBlocks() {
        return subTaskBlocks;
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

        //give it an id (to be able to track it later)
        taskBlockView.setId(String.valueOf(subTaskBlocks.size())); //the id is the index of the block in the array list

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
