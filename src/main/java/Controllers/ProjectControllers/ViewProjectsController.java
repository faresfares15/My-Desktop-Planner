package Controllers.ProjectControllers;

import Models.Project.ProjectSchema;
import esi.tp_poo_final.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ViewProjectsController {
    @FXML
    VBox projectsContainer;
    public void initData() {
        //clear the content of the container
        projectsContainer.getChildren().clear();

        //get the projects from the database
        ArrayList<ProjectSchema> projects = HelloApplication.projectsModel.findAll();

        if(projects.size() == 0) {
            Text noProjectsText = new Text("No projects found");
            projectsContainer.setAlignment(Pos.TOP_CENTER);
            projectsContainer.getChildren().add(noProjectsText);
            return;
        }

        //iterate over the projects and add them to the scroll container
        for(ProjectSchema project : projects) {
            //create the texts objects
            Text projectName = new Text("Name: "+project.getName());
            Text projectDescription = new Text("Description: "+project.getDescription());
            projectDescription.setWrappingWidth(230);

            //create the container for the texts
            VBox projectContainer = new VBox(projectName, projectDescription);

            //add a bottom border for the container
            Border border = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1)));
            projectContainer.setBorder(border);

            //add the container to the scroll container
            projectsContainer.getChildren().add(projectContainer);
        }
    }

    public void createNewProject() throws IOException {
        //show a new window to create a new project
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("create-new-project-view.fxml"));
        Scene addTaskToProjectScene = new Scene(fxmlLoader.load());
//        Scene addTaskToProjectScene = new Scene(fxmlLoader.load(), 600, 400);
        Stage addTaskToProjectStage = new Stage();
        addTaskToProjectStage.setTitle("Validate Planning");
        addTaskToProjectStage.initModality(Modality.APPLICATION_MODAL);
        addTaskToProjectStage.initOwner(projectsContainer.getScene().getWindow());
        addTaskToProjectStage.setScene(addTaskToProjectScene);
        addTaskToProjectStage.showAndWait();

        initData();
    }
    public void goToCalendarPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("calendar-view.fxml"));
//            fxmlLoader.setControllerFactory(c -> new ...());
        Scene scene = new Scene(fxmlLoader.load(), 840, 500);
        Stage stage = (Stage) projectsContainer.getScene().getWindow();
        stage.setTitle("Calendar");

        //center the view on the user's screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - scene.getHeight()) / 2);

        stage.setScene(scene);
    }
}
