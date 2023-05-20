package esi.tp_poo_final;

import Controllers.TaskControllers.PlanTaskController;
import Databases.FreeSlotsFileDatabase;
import Databases.TaskFileDatabase;
import Models.Day.DayModel;
import Models.FreeSlot.FreeSlotModel;
import Models.Task.TaskModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;
    FreeSlotModel freeSlotModel;
    private DayModel dayModel;
    TaskModel taskModel;
    public HelloController(FreeSlotModel freeSlotModel, TaskModel taskModel, DayModel dayModel) {
        this.freeSlotModel = freeSlotModel;
        this.taskModel = taskModel;
        this.dayModel = dayModel;
    }

    @FXML
    protected void onHelloButtonClick() throws IOException {
        welcomeText.setText("Welcome to JavaFX Application!");
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("plan-task-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);

        Stage stage = (Stage) welcomeText.getScene().getWindow();

        //center the view on the user's screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - scene.getHeight()) / 2);

        stage.setScene(scene);


//        primaryStage.setTitle("Hello!");
//        primaryStage.setScene(scene);
//        primaryStage.show();
    }
}