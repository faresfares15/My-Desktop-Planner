package Controllers.ProjectControllers;

import Exceptions.ProjectDoesNotExistException;
import Utils.Popups;
import esi.tp_poo_final.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class CreateNewProjectController {
    @FXML
    TextField projectName;
    @FXML
    TextArea projectDescription;
    public void createProject() {
        try {
            //get the project name and description
            ViewInfos viewInfos = new ViewInfos();
            String name = viewInfos.getProjectName();
            String description = viewInfos.getProjectDescription();

            //check if the project name already exists
            try{
                if(HelloApplication.projectsModel.find(name) != null) {
                    throw new Exception("Project already exists");
                }
            }catch (ProjectDoesNotExistException e){
                //do nothing
            }
            catch (Exception e){
                Popups.showErrorMessage("Error", e.getMessage());
                return;
            }

            //create a new project
            HelloApplication.projectsModel.create(name, description, new ArrayList<>());

            //show success message
            Popups.showSuccessMessage("Success", "Project created successfully");

            //close the window
            quit();
        } catch (Exception e) {
            //show an error message
            Popups.showErrorMessage("Error", e.getMessage());
            e.printStackTrace();
        }
    }
    public void cancel() {
        //close the window
        quit();
    }
    private class ViewInfos{
        String getProjectName() throws Exception {
            if(projectName.getText().isEmpty()) {
                throw new Exception("Project name is empty");
            }
            return projectName.getText();
        }
        String getProjectDescription() throws Exception {
            if(projectDescription.getText().isEmpty()) {
                throw new Exception("Project description is empty");
            }
            return projectDescription.getText();
        }
    }

    private void quit(){
        projectName.getScene().getWindow().hide();
    }
}
