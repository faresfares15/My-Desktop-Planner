package Controllers.ProjectControllers;

import Models.Project.ProjectSchema;
import Models.Task.TaskSchema;
import esi.tp_poo_final.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class AddTaskToProjectController {
    @FXML
    Text taskName;
    @FXML
    Text currentProject;
    @FXML
    ComboBox<String> projectsList;
    @FXML
    TextField newProjectName;
    @FXML
    TextArea descriptionBox;
    String currentProjectName;
    private TaskSchema task;
    public AddTaskToProjectController(TaskSchema task, String projectName){
        this.task = task;
        this.currentProjectName = projectName;
    }
    @FXML
    public void initialize(){
        //initialize the projects list combobox

        //set the project name text
        currentProject.setText(currentProjectName);

        //setting the category combo box
        ArrayList<ProjectSchema> projects = HelloApplication.projectsModel.findAll();

        //check if the task belongs to a project, and find it if yes
        if(task.getProjectId() != -1){
            for (ProjectSchema project: projects){
                if (project.getId() == task.getProjectId()){
                    currentProjectName = project.getName();
                    break;
                }
            }
        }

        //set the projects in the combobox
        ObservableList<String> projectsNames = FXCollections.observableArrayList();
        projects.forEach(project -> projectsNames.add(project.getName()));
        projectsNames.add("New project");
        projectsList.setValue(currentProjectName);
        projectsList.getItems().clear();
        projectsList.getItems().setAll(projectsNames);

        projectsList.setOnAction(event -> {
            // If the user has selected "New category," then make the category text field visible.
            if (projectsList.getValue().equals("New project")) {
                newProjectName.setDisable(false);
                descriptionBox.setDisable(false);

            } else {
                newProjectName.setDisable(true);
                descriptionBox.setDisable(true);
            }
        });
    }
    public void initData(){
        taskName.setText(task.getName());
    }

    public void addTaskToProject(){

        try{
            ViewInfos viewInfos = new ViewInfos();
            String projectName = viewInfos.getProjectName();

            if(projectsList.getValue().equals("New project")){
                //get the new project description
                String description = viewInfos.getDescription();
                ArrayList<TaskSchema> tasks = new ArrayList<>();
                tasks.add(task);


                //create a new project instance
                ProjectSchema currentProject = HelloApplication.projectsModel.create(projectName, description, tasks);

                //update the project id in the task
                task.setProjectId(currentProject.getId());

                setCurrentProjectName(projectName);
            }
            else{
                //find the project in the database
                ProjectSchema currentProject = HelloApplication.projectsModel.find(projectName);

                //add the task to the project
                currentProject.addTask(task);

                //update the project id in the task
                task.setProjectId(currentProject.getId());

                setCurrentProjectName(projectName);
            }

            //show success message
            showSuccessMessage("Project added successfully", "Task added to project successfully");

            //close the window
            quit();

        }catch (Exception e){
            e.printStackTrace();
            showErrorMessage("Error", e.getMessage());
            setCurrentProjectName("No project");
        }
    }
    public void cancel(){
        quit();
    }
    private void quit(){
        taskName.getScene().getWindow().hide();
    }
    public String getCurrentProjectName() {
        return currentProjectName;
    }

    public void setCurrentProjectName(String currentProjectName) {
        this.currentProjectName = currentProjectName;
    }

    private class ViewInfos{
        private String getProjectName() throws Exception {
            String projectName = projectsList.getValue();
            if (projectName == null) throw new Exception("No project selected");
            if(projectName.equals("New project")){
                projectName = newProjectName.getText();
                if(projectName.isBlank()) throw new Exception("No name provided for the new project");

                //check if the project already exists (just from the list of projects)
                for(String project : projectsList.getItems()){
                    if(project.equals(projectName)) throw new Exception("Project already exists");
                }
            }

            return projectName;
        }
        private String getDescription() throws Exception {
            String description = descriptionBox.getText();
            if(description.isBlank()) throw new Exception("No description provided");
            return description;
        }

    }
    private void showSuccessMessage(String title, String message) {
        Alert successMessage = new Alert(Alert.AlertType.INFORMATION);
        successMessage.setContentText(message);
        successMessage.setHeaderText(title);
        successMessage.setTitle(title);
        successMessage.showAndWait();
    }
    private void showErrorMessage(String title, String message){
        Alert errorMessage = new Alert(Alert.AlertType.ERROR);
        errorMessage.setContentText(message);
        errorMessage.setHeaderText(title);
        errorMessage.setTitle(title);
        errorMessage.showAndWait();
    }
}
