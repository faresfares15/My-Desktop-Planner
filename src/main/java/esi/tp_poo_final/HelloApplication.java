package esi.tp_poo_final;

import Controllers.TaskControllers.PlanTaskController;
import Databases.*;
import Models.Day.DayModel;
import Models.FreeSlot.FreeSlotModel;
import Models.Task.TaskModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        System.setProperty("javafx.sg.warn", "true");
        primaryStage.setTitle("Signup Page");

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("calendar-view.fxml"));
//
////        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("plan-task-view.fxml"));
////        Parent root = fxmlLoader.load();
//
//        //create the databases
        FreeSlotsDatabase freeSlotsDatabase = new FreeSlotsFileDatabase(/*filename*/);
        TaskDatabase taskDatabase = new TaskFileDatabase(/*filename*/);
        DayDatabase dayDatabase = new DayFileDataBase(/*filename*/);
//
//        //create the models
        FreeSlotModel freeSlotModel = new FreeSlotModel(freeSlotsDatabase);
        TaskModel taskModel = new TaskModel(taskDatabase);
        DayModel dayModel = new DayModel(dayDatabase);
//
//        fxmlLoader.setControllerFactory(c ->
//                new HelloController(freeSlotModel, taskModel)
//        );
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        primaryStage.setTitle("Calendar");
        primaryStage.setScene(scene);
        primaryStage.show();

        //get the controller from the view
//        PlanTaskController planTaskController = fxmlLoader.getController();
//        planTaskController.setFreeSlotModel(freeSlotModel);
//        planTaskController.setTaskModel(taskModel);
//        planTaskController.setPlanTaskView(this);

//        primaryStage.setScene(root.getScene());
//        primaryStage.show();

//        PlanTaskView planTaskView = new PlanTaskView(freeSlotModel, taskModel, dayModel, primaryStage);
//        primaryStage.setScene(planTaskView.getScene());
//        primaryStage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}