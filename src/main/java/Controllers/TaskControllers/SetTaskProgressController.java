package Controllers.TaskControllers;

import Databases.UserDoesNotExistException;
import Models.Day.DayModel;
import Models.Day.DaySchema;
import Models.Task.DecomposableTaskSchema;
import Models.Task.Progress;
import Models.Task.TaskSchema;
import Models.User.UserModel;
import Models.User.UserSchema;
import esi.tp_poo_final.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

public class SetTaskProgressController {
    final DayModel dayModel= HelloApplication.dayModel;
    final UserModel userModel = HelloApplication.userModel;
    @FXML
    Text taskName;
    @FXML
    Text currentProgress;
    @FXML
    ComboBox<String> progressesList;
    String currentProgressName;
    private TaskSchema task;
    public SetTaskProgressController(TaskSchema task, String currentProgressName){
        this.task = task;
        this.currentProgressName = currentProgressName;
    }
    @FXML
    public void initialize(){
        //set the current status text
        currentProgress.setText(currentProgressName);

        //initialize the status list combobox
        progressesList.getItems().clear(); //clear the combobox

        //add the statuses to the combobox
        Progress[] progresses = Progress.values();
        for (Progress progress: progresses){
            progressesList.getItems().add(progress.toString());
        }

        //set the current status in the combobox
        progressesList.setValue(currentProgressName);
    }
    public void updateProgress(){
        //get the new status
        ViewInfos infos = new ViewInfos();
        Progress newProgress = infos.getProgress();
        currentProgressName = newProgress.toString();

        //update the task's progress
        switch (newProgress.toString()){
            case "COMPLETED":
                markTaskAsComplete();
                break;
        }
        task.setProgress(newProgress);

        //close the window
        progressesList.getScene().getWindow().hide();
    }

    private void markTaskAsComplete(){
        //Fetch the task from the database and set its progress to completed
        task.setProgress(Progress.COMPLETED);

        if (task instanceof DecomposableTaskSchema){
            //If the task is decomposable, then we need to set all of it's subtasks as completed
            for (TaskSchema subtask: ((DecomposableTaskSchema) task).getSubTasks()){
                subtask.setProgress(Progress.COMPLETED);
            }
        }

        //add the tasks the taskCompltedInADay count
        DaySchema day = dayModel.find(task.getDate());
        day.setNumberOfTasksCompletedOnThisDay(day.getNumberOfTasksCompletedOnThisDay()+1);
        //Compare with the bestDayOfTheUser
        try {
            UserSchema currentUser = userModel.find(HelloApplication.currentUserName);

            if(day.getNumberOfTasksCompletedOnThisDay() > currentUser.getMostTasksCompletedInADay()){
                currentUser.setMostTasksCompletedInADay(day.getNumberOfTasksCompletedOnThisDay());
                currentUser.setMostProductiveDate(day.getDate());
            }
            if (day.getNumberOfTasksCompletedOnThisDay() >= currentUser.getSettings().getMinimalNumberOfTasksPerDay() && !day.isWasCongratulatedToday()){
                day.setWasCongratulatedToday(true);
                currentUser.setMostCongratsReceivedInARow(currentUser.getMostCongratsReceivedInARow()+1);
                showSuccessMessage("Congratulations, you have completed "+day.getNumberOfTasksCompletedOnThisDay()+" tasks today, keep up the good work!");
            }

        } catch (UserDoesNotExistException e) {
            showErrorMessage("No user is logged in, the changes won't be saved");
        }

        //Evaluating the progress of the day

    }
    public void cancel(){
        quit();
    }
    private void quit(){
        taskName.getScene().getWindow().hide();
    }
    public void initData(){
        taskName.setText(task.getName());
    }
    private class ViewInfos{
        private Progress getProgress(){
            return Progress.valueOf(progressesList.getValue());
        }
    }

    public String getCurrentProgressName() {
        return currentProgressName;
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
