package esi.tp_poo_final;

import Controllers.TaskControllers.PlanTaskController;
import Databases.*;
import Models.Day.DayModel;
import Models.FreeSlot.FreeSlotModel;
import Models.Task.Priority;
import Models.Task.SimpleTaskSchema;
import Models.Task.TaskModel;
import Models.Task.TaskStatus;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class HelloApplication extends Application {
    public static final TaskModel taskModel = new TaskModel(new TaskFileDatabase());
    @Override
    public void start(Stage primaryStage) throws IOException {
        //trash code
        taskModel.create(new SimpleTaskSchema(LocalDate.now(), "task1", LocalTime.of(10, 0), Duration.ofHours(1).plusMinutes(30), Priority.LOW, LocalDate.of(2023, 5, 17), "categ1", TaskStatus.UNSCHEDULED, 0));
        taskModel.create(new SimpleTaskSchema(LocalDate.now(), "task2", LocalTime.of(13, 0), Duration.ofHours(1), Priority.LOW, LocalDate.of(2023, 5, 17), "categ1", TaskStatus.UNSCHEDULED, 0));
        //end of trash code
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