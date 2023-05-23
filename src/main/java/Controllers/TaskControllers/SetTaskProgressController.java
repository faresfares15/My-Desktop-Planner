package Controllers.TaskControllers;

import Databases.UserDoesNotExistException;
import Exceptions.TaskDoesNotExistException;
import Models.Day.DayModel;
import Models.Day.DaySchema;
import Models.Task.DecomposableTaskSchema;
import Models.Task.Progress;
import Models.Task.TaskModel;
import Models.Task.TaskSchema;
import Models.User.UserModel;
import Models.User.UserSchema;
import esi.tp_poo_final.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.LocalDate;

public class SetTaskProgressController {
    final DayModel dayModel= HelloApplication.dayModel;
    final UserModel userModel = HelloApplication.userModel;
    final TaskModel taskModel = HelloApplication.taskModel;
    @FXML
    Text taskName;
    @FXML
    Text currentProgress;
    @FXML
    ComboBox<String> progressesList;
    String currentProgressName;
    private TaskSchema task;
    private boolean isSubtask;
    public SetTaskProgressController(TaskSchema task, String currentProgressName, boolean isSubtask){
        this.task = task;
        this.currentProgressName = currentProgressName;
        this.isSubtask = isSubtask;
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
        //TODO: set the value of isSubtask from the viewInfo

        //add the tasks the taskCompletedInADay count
        DaySchema day = dayModel.find(task.getDate());
        DaySchema today = dayModel.find(LocalDate.now());
        DaySchema yesterday = dayModel.find(LocalDate.now().minusDays(1));

        day.setNumberOfTasksCompletedOnThisDay(day.getNumberOfTasksCompletedOnThisDay() + 1);

        //Compare with the bestDayOfTheUser
        try {
            UserSchema currentUser = userModel.find(HelloApplication.currentUserName);

            if (day.getNumberOfTasksCompletedOnThisDay() >= currentUser.getSettings().getMinimalNumberOfTasksPerDay() && !day.isWasCongratulatedToday()) {
                day.setWasCongratulatedToday(true);
                currentUser.setTotalCongratsReceived(currentUser.getTotalCongratsReceived() + 1);
                showSuccessMessage("Congratulations, you have completed the minimal number of tasks per day");
            }
            if (day.getNumberOfTasksCompletedOnThisDay() > currentUser.getMostTasksCompletedInADay()) {
                showSuccessMessage("Congrats you just surpassed your best day!, you have completed " + day.getNumberOfTasksCompletedOnThisDay() + " tasks today, keep up the good work!");
                currentUser.setMostTasksCompletedInADay(day.getNumberOfTasksCompletedOnThisDay());
                currentUser.setMostProductiveDate(day.getDate());
            }

            //Test if he'll get a badge today
            if (!yesterday.isWasCongratulatedToday()) currentUser.setCongratsInARowCounter(0);

            if (today.isWasCongratulatedToday()){
                currentUser.setCongratsInARowCounter(currentUser.getCongratsInARowCounter() + 1);
                if (currentUser.getCongratsInARowCounter() >= 5  && !today.isReceivedBadgeToday()){
                    if(currentUser.getCongratsInARowCounter() >= 15  && !today.isReceivedBadgeToday()){
                        if(currentUser.getCongratsInARowCounter() >= 45  && !today.isReceivedBadgeToday()){
                            showSuccessMessage("Congrats you have received 45 congrats in a row, you have been awarded the \"Excellent\" badge");
                            currentUser.setNumberOfExcellentBadges(currentUser.getNumberOfExcellentBadges() + 1);
                            today.setReceivedBadgeToday(true);
                        } else {
                            showSuccessMessage("Congrats you have received 15 congrats in a row, you have been awarded the \"Very Good\" badge");
                            currentUser.setNumberOfVeryGoodBadges(currentUser.getNumberOfVeryGoodBadges() + 1);
                            today.setReceivedBadgeToday(true);
                        }
                    } else {
                        showSuccessMessage("Congrats you have received 5 congrats in a row, you have been awarded the \"Good\" badge");
                        currentUser.setNumberOfGoodBadges(currentUser.getNumberOfGoodBadges() + 1);
                        today.setReceivedBadgeToday(true);
                    }
                }
            }

            if (currentUser.getCongratsInARowCounter() > currentUser.getMostCongratsReceivedInARow()) {
                currentUser.setMostCongratsReceivedInARow(currentUser.getCongratsInARowCounter());
            }

            //The progress of a decomposableTask
            if (isSubtask){
                DecomposableTaskSchema motherTask = (DecomposableTaskSchema) taskModel.find(task.getId());
                //If all  the other subtasks are completed than set the mother task to be completed
                if (motherTask.getProgressPercentage() == 1) motherTask.setProgress(Progress.COMPLETED);
            }

        } catch (UserDoesNotExistException e) {
            showErrorMessage("No user is logged, the changes won't be saved");
        } catch (TaskDoesNotExistException e) {
            showErrorMessage("A problem has occurred, this task can't be found");
        }catch (Exception e){
            e.printStackTrace();
        }

        //Go back to the calendar view
        quit();
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
