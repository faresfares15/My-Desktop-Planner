package esi.tp_poo_final;

import Controllers.TaskControllers.PlanTaskController;
import Databases.*;
import Models.Day.DayModel;
import Models.FreeSlot.FreeSlotModel;
import Models.Project.ProjectModel;
import Models.Task.Priority;
import Models.Task.SimpleTaskSchema;
import Models.Task.TaskModel;
import Models.Task.TaskStatus;
import Models.User.UserModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class HelloApplication extends Application {
    //TODO: assign those only when the user logs in
    public static final TaskModel taskModel = new TaskModel(new TaskFileDatabase());
    public static final UserModel userModel = new UserModel(new UserFileDataBase());
    public static final DayModel dayModel = new DayModel(new DayFileDataBase());
    public static final FreeSlotModel freeSlotModel = new FreeSlotModel(new FreeSlotsFileDatabase());
    public static final ProjectModel projectsModel = new ProjectModel(new ProjectFileDataBase());

    //TODO:The usernames file is a constant here, think about keeping it or changing this later
//    public static final String usernamesFileName = "usernames.txt";
    public static final String usersDirectoryName = "users_Directoy";

    //File names to keep consistency between classes
    public static final String taskDbFileName = "taskFileDatabase.dat";
    public static final String freeSlotDbFileName = "freeSlotFileDatabase.dat";
    public static final String dayDbFileName = "dayFileDatabase.dat";
    public static final String projectDbFileName = "projectFileDatabase.dat";
    public static final String usersDbFileName = "usersFileDatabase.dat";
    public static String currentUserName = null;

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

        System.setProperty("javafx.sg.warn", "true");

        primaryStage.setTitle("Login");

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 840, 400);
        primaryStage.setTitle("Login");


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

    @Override
    public void stop() throws Exception {
        //Save the files before the application closes (calling the stop method)
        if (HelloApplication.currentUserName != null){
            //save the task model
            taskModel.save();

            //save the free slot model
            freeSlotModel.save();

            //save the day model
            dayModel.save();


            //save the project model;
            projectsModel.save();

            //save the user model
            userModel.save();

            //TODO: create the calendar model to save it here
            System.out.println("Files saved successfully");
        } //else the user didn't login so we don't need to save anything
        super.stop();
    }

    @Override
    public void init() throws Exception {
        //Load the usersModel so the login and signup controllers can use it
        try {
            File usersDBFile = new File(usersDbFileName);
            if (usersDBFile.exists()){
            } else {
                usersDBFile.createNewFile();
            }
            userModel.load();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        super.init();
    }

    public static void main(String[] args) {
        launch();
    }
}