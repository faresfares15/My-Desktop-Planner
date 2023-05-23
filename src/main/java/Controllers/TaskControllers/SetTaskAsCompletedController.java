package Controllers.TaskControllers;

import Databases.UserDoesNotExistException;
import Models.Day.DayModel;
import Models.Day.DaySchema;
import Models.Task.DecomposableTaskSchema;
import Models.Task.Progress;
import Models.Task.TaskModel;
import Models.Task.TaskSchema;
import Models.User.UserModel;
import Models.User.UserSchema;
import esi.tp_poo_final.HelloApplication;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class SetTaskAsCompletedController implements EventHandler<ActionEvent> {
    final TaskModel taskModel = HelloApplication.taskModel;
    final DayModel dayModel= HelloApplication.dayModel;
    final UserModel userModel = HelloApplication.userModel;
    @FXML
    Button setTaskAsCompletedButton;
    TaskSchema task;

    @Override
    public void handle(ActionEvent event) {
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
            showErrorMessage("No user is logged, the changes won't be saved");
        }

        //Evaluating the progress of the day
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
