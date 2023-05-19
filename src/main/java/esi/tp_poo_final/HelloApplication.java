package esi.tp_poo_final;

import Controllers.TaskControllers.PlanTaskController;
import Databases.*;
import Models.Day.DayModel;
import Models.FreeSlot.FreeSlotModel;
import Models.Task.Priority;
import Models.Task.SimpleTaskSchema;
import Models.Task.TaskModel;
import Models.Task.TaskStatus;
import Models.User.UserModel;
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
    public static final UserModel userModel = new UserModel(new UserFileDataBase());
    public static final DayModel dayModel = new DayModel(new DayFileDataBase());
    public static final FreeSlotModel freeSlotModel = new FreeSlotModel(new FreeSlotsFileDatabase());
    @Override
    public void start(Stage primaryStage) throws IOException {
        //trash code
        taskModel.create(new SimpleTaskSchema(LocalDate.now().minusDays(8), "task000", LocalTime.of(8, 20), Duration.ofHours(4).plusMinutes(30), Priority.LOW, LocalDate.of(2023, 5, 17), "categ1", TaskStatus.UNSCHEDULED, 0));
        taskModel.create(new SimpleTaskSchema(LocalDate.now().minusDays(7), "task00", LocalTime.of(10, 20), Duration.ofHours(4).plusMinutes(30), Priority.LOW, LocalDate.of(2023, 5, 17), "categ1", TaskStatus.UNSCHEDULED, 0));
        taskModel.create(new SimpleTaskSchema(LocalDate.now().minusDays(3), "task0", LocalTime.of(13, 20), Duration.ofHours(4).plusMinutes(30), Priority.LOW, LocalDate.of(2023, 5, 17), "categ1", TaskStatus.UNSCHEDULED, 0));
        taskModel.create(new SimpleTaskSchema(LocalDate.now().minusDays(2), "task1", LocalTime.of(17, 20), Duration.ofHours(4).plusMinutes(30), Priority.LOW, LocalDate.of(2023, 5, 17), "categ1", TaskStatus.UNSCHEDULED, 0));
        taskModel.create(new SimpleTaskSchema(LocalDate.now(), "task2", LocalTime.of(13, 0), Duration.ofHours(1), Priority.LOW, LocalDate.of(2023, 5, 17), "categ1", TaskStatus.UNSCHEDULED, 0));
        taskModel.create(new SimpleTaskSchema(LocalDate.now(), "task3", LocalTime.of(22, 0), Duration.ofHours(1), Priority.LOW, LocalDate.of(2023, 5, 17), "categ1", TaskStatus.UNSCHEDULED, 0));
        taskModel.create(new SimpleTaskSchema(LocalDate.now().plusDays(4), "task4", LocalTime.of(13, 30), Duration.ofHours(1), Priority.LOW, LocalDate.of(2023, 5, 17), "categ1", TaskStatus.UNSCHEDULED, 0));
        //end of trash code

        //trash code

        //end of trash code
        System.setProperty("javafx.sg.warn", "true");
        primaryStage.setTitle("Signup Page");

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("calendar-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 840, 400);
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