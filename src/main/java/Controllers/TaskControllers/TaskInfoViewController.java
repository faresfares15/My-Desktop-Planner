package Controllers.TaskControllers;

import Controllers.CategoryControllers.AddTaskToCategoryController;
import Controllers.ProjectControllers.AddTaskToProjectController;
import Models.Project.ProjectSchema;
import Models.Task.TaskSchema;
import esi.tp_poo_final.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class TaskInfoViewController {
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
    TaskSchema task;
    public TaskInfoViewController(TaskSchema task){
        this.task = task;
    }
    public void initData(){
        //set the task name
        taskName.setText(task.getName());

        //set the task start time
        startTime.setText(task.getStartTime().toString());

        //set the task duration
        duration.setText(task.getDuration().toString());

        //set the task priority
        priority.setText(task.getPriority().toString());

        try{
            //set the task deadline
            deadline.setText(task.getDeadline().toString());
        }catch (Exception e){
            deadline.setText("No deadline");
        }

        //set the task category
        if(Objects.equals(task.getCategory(), "")){
            category.setText("No category");
        }else{
            category.setText(task.getCategory());
        }

        //set the task project name

        try{
            if(task.getProjectId() == -1) projectName.setText("No project");
            else {
                ProjectSchema project = HelloApplication.projectsModel.find(task.getProjectId());
                projectName.setText(project.getName());
            }
        }catch (Exception e){
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
        if(Objects.equals(newCategoryName, "")){
            category.setText("No category");
        }else {
            category.setText(newCategoryName);
        }
    }
    public void changeTaskName(){
//        HelloApplication.taskModel.update(task.getDate(), task.getId(), taskName.getText());
        TextInputDialog dialog = new TextInputDialog("Enter a new name for the task");
        dialog.setTitle("Rename Task");
        dialog.showAndWait();

        task.setName(dialog.getEditor().getText());
        taskName.setText(task.getName());
    }
}
