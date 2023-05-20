package Controllers.ProjectControllers;

import Exceptions.ProjectDoesNotExistException;
import Exceptions.TaskDoesNotExistException;
import Models.Project.ProjectModel;
import Models.Project.ProjectSchema;
import Models.Task.TaskModel;
import Models.Task.TaskSchema;
import esi.tp_poo_final.HelloApplication;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.time.LocalDate;

public class ProjectController implements EventHandler<ActionEvent> {
    private LocalDate taskDate;
    final ProjectModel projectModel = HelloApplication.projectsModel;
    final TaskModel taskModel = HelloApplication.taskModel;

    private int projectId;
    private int taskId;
    @Override
    public void handle(ActionEvent event) {

        //TODO: use a view info inner class to traitement of data before using it
        try {
            ProjectSchema project = projectModel.find(projectId);
            TaskSchema task = taskModel.find(taskDate, taskId);
            project.addTask(task);




        } catch (ProjectDoesNotExistException | TaskDoesNotExistException e) {
            System.out.println(e.getMessage());
            //TODO: check if it's going to add a pop up or when he'll choose the project he'll have a limited set of choices
        }

    }
}
