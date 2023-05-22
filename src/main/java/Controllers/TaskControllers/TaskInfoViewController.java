package Controllers.TaskControllers;

import Controllers.CategoryControllers.AddTaskToCategoryController;
import Controllers.ProjectControllers.AddTaskToProjectController;
import Databases.UserDoesNotExistException;
import Exceptions.TaskDoesNotExistException;
import Models.Day.DayModel;
import Models.Day.DaySchema;
import Models.Project.ProjectSchema;
import Models.Task.*;
import Models.User.UserModel;
import Models.User.UserSchema;
import esi.tp_poo_final.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

public class TaskInfoViewController {
    final UserModel userModel = HelloApplication.userModel;
    final DayModel dayModel = HelloApplication.dayModel;
    final TaskModel taskModel = HelloApplication.taskModel;
    @FXML
    Label taskName;
    @FXML
    Text startTime;
    @FXML
    Text duration;
    @FXML
    Text priority;
    @FXML
    Text deadline;
    @FXML
    Text category;
    @FXML
    Text projectName;
    @FXML
    Text status;
    @FXML
    Text progress;
    @FXML
    Button setTaskAsCompletedButton;
    boolean isSubtask;
    TaskSchema task;

    public TaskInfoViewController(TaskSchema task) {
        this.task = task;
    }

    public void initData() {
        //set the task name
        taskName.setText(task.getName());

        //set the task start time
        startTime.setText(task.getStartTime().toString());

        //set the task duration
        duration.setText(task.getDuration().toString());

        //set the task priority
        priority.setText(task.getPriority().toString());

        try {
            //set the task deadline
            deadline.setText(task.getDeadline().toString());
        } catch (Exception e) {
            deadline.setText("No deadline");
        }

        //set the task category
        if(Objects.equals(task.getCategory().getName(), "")){
            category.setText("No category");
        }else {
            category.setText(task.getCategory().getName());
        }

        //set the task project name

        try {
            if (task.getProjectId() == -1) projectName.setText("No project");
            else {
                ProjectSchema project = HelloApplication.projectsModel.find(task.getProjectId());
                projectName.setText(project.getName());
            }
        } catch (Exception e) {
            task.setProjectId(-1);
            projectName.setText("No project");
            e.printStackTrace();
        }
//        status.setText(task.getStatus().toString());
//        progress.setText(task.getProgress().toString());
    }

    public void goToCalendar() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("calendar-view.fxml"));
//            fxmlLoader.setControllerFactory(c -> new ...());
        Scene scene = new Scene(fxmlLoader.load(), 840, 500);
        Stage stage = (Stage) taskName.getScene().getWindow();
        stage.setTitle("Calendar");

        //center the view on the user's screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - scene.getHeight()) / 2);

        stage.setScene(scene);
    }

    public void addTaskToProject() throws IOException {
        //show a new window to select a project
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("add-task-to-project-view.fxml"));
        fxmlLoader.setControllerFactory(c -> new AddTaskToProjectController(task, projectName.getText()));
        Scene addTaskToProjectScene = new Scene(fxmlLoader.load());
//        Scene addTaskToProjectScene = new Scene(fxmlLoader.load(), 600, 400);
        Stage addTaskToProjectStage = new Stage();
        addTaskToProjectStage.setTitle("Add task to project");
        addTaskToProjectStage.initModality(Modality.APPLICATION_MODAL);
        addTaskToProjectStage.initOwner(taskName.getScene().getWindow());
        addTaskToProjectStage.setScene(addTaskToProjectScene);

        //init the data in the new view
        AddTaskToProjectController addTaskToProjectController = fxmlLoader.getController();
        addTaskToProjectController.initData();

        //show the new view
        addTaskToProjectStage.showAndWait();


        projectName.setText(addTaskToProjectController.getCurrentProjectName());
    }

    public void setTaskAsCompleted() {
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
                //increment the number of congrats received to show them in the user stats
                currentUser.setTotalCongratsReceived(currentUser.getTotalCongratsReceived() + 1);
                //Check if he was congrated yesterday
                //if yes  then increment the counter of the congrats in a row
                //if he gets more than 5 in a row give him a badge
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
        }


        //Go back to the calendar view
        try {
            this.goToCalendar();
        } catch (IOException e) {
            showErrorMessage("A problem has occurred, can't go back to the calendar view");
        }


    }

    public void addTaskToCategory() throws IOException {

        //show a new window to select a category
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("add-task-to-category-view.fxml"));
        fxmlLoader.setControllerFactory(c -> new AddTaskToCategoryController(task));
        Scene addTaskToCategoryScene = new Scene(fxmlLoader.load());
        Stage addTaskToCategoryStage = new Stage();
        addTaskToCategoryStage.setTitle("Add task to categoryadd-task-to-project-view.fxml");
        addTaskToCategoryStage.initModality(Modality.APPLICATION_MODAL);
        addTaskToCategoryStage.initOwner(taskName.getScene().getWindow());
        addTaskToCategoryStage.setScene(addTaskToCategoryScene);

        //init the data in the new view
        AddTaskToCategoryController addTaskToCategoryController = fxmlLoader.getController();
        addTaskToCategoryController.initData();

        //show the new view
        addTaskToCategoryStage.showAndWait();

        String newCategoryName = addTaskToCategoryController.getCurrentCategoryName();
        if (Objects.equals(newCategoryName, "")) {
            category.setText("No category");
        } else {
            category.setText(newCategoryName);
        }
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

    public void changeTaskName() {
//        HelloApplication.taskModel.update(task.getDate(), task.getId(), taskName.getText());
        TextInputDialog dialog = new TextInputDialog("Enter a new name for the task");
        dialog.setTitle("Rename Task");
        dialog.showAndWait();

        task.setName(dialog.getEditor().getText());
        taskName.setText(task.getName());
    }
}
