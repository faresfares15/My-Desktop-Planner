package esi.tp_poo_final;

import Databases.*;
import Models.Calendar.Settings;
import Models.Category.CategoryModel;
import Models.Category.CategorySchema;
import Models.Day.DayModel;
import Models.FreeSlot.FreeSlotModel;
import Models.FreeSlot.FreeSlotSchema;
import Models.Project.ProjectModel;
import Models.Task.*;
import Models.User.UserModel;
import Models.User.UserSchema;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class HelloApplication extends Application {
    public static final TaskModel taskModel = new TaskModel(new TaskFileDatabase());
    public static final UserModel userModel = new UserModel(new UserFileDataBase());
    public static final DayModel dayModel = new DayModel(new DayFileDataBase());
    public static final FreeSlotModel freeSlotModel = new FreeSlotModel(new FreeSlotsFileDatabase());
    public static final ProjectModel projectsModel = new ProjectModel(new ProjectFileDataBase());
    public static final CategoryModel categoryModel = new CategoryModel(new CategoryFileDataBase());


    public static final String usersDirectoryName = "users_Directoy";

    //File names to keep consistency between classes
    public static final String taskDbFileName = "taskFileDatabase.dat";
    public static final String freeSlotDbFileName = "freeSlotFileDatabase.dat";
    public static final String dayDbFileName = "dayFileDatabase.dat";
    public static final String projectDbFileName = "projectFileDatabase.dat";
    public static final String usersDbFileName = "usersFileDatabase.dat";
    public static String currentUserName = null;
    public static Settings currentUserSettings = new Settings();

    @Override
    public void start(Stage primaryStage) throws IOException {

        //trash code
        try{
            UserSchema newUser = userModel.create("user", "user");
            currentUserName = newUser.getUsername();
            currentUserSettings = newUser.getSettings();
            currentUserSettings.setMinimalNumberOfTasksPerDay(1);

            categoryModel.create("Work", Color.WHEAT);
            categoryModel.create("Personal", Color.LIGHTBLUE);
            categoryModel.create("School", Color.LIGHTGREEN);
            categoryModel.create("Sport", Color.LIGHTPINK);
            categoryModel.create("", Color.TRANSPARENT);

        }catch (Exception e){
            //ignore
            e.printStackTrace();
        }

        //create days from today to 30 days from now, and 10 days before
        for (int i = -10; i < 40; i++) {
            dayModel.create(LocalDate.now().plusDays(i));
        }

        //create random simple tasks
        taskModel.create(new SimpleTaskSchema(LocalDate.now().minusDays(1), "task000", LocalTime.of(8, 20), Duration.ofHours(4).plusMinutes(30), Priority.LOW, LocalDate.of(2023, 5, 17), TaskStatus.UNSCHEDULED, 0));
        taskModel.create(new SimpleTaskSchema(LocalDate.now().minusDays(2), "task21", LocalTime.of(1, 0), Duration.ofHours(3), Priority.LOW, LocalDate.of(2023, 5, 17), TaskStatus.UNSCHEDULED, 0));
        taskModel.create(new SimpleTaskSchema(LocalDate.now().minusDays(3), "task22", LocalTime.of(5, 0), Duration.ofHours(2), Priority.LOW, LocalDate.of(2023, 5, 17), TaskStatus.UNSCHEDULED, 0));
        taskModel.create(new SimpleTaskSchema(LocalDate.now().minusDays(4), "task23", LocalTime.of(8, 0), Duration.ofHours(1), Priority.LOW, LocalDate.of(2023, 5, 17), TaskStatus.UNSCHEDULED, 0));
        taskModel.create(new SimpleTaskSchema(LocalDate.now().minusDays(5), "task24", LocalTime.of(10, 0), Duration.ofHours(1), Priority.LOW, LocalDate.of(2023, 5, 17), TaskStatus.UNSCHEDULED, 0));
        taskModel.create(new SimpleTaskSchema(LocalDate.now().minusDays(6), "task25", LocalTime.of(12, 0), Duration.ofHours(1), Priority.LOW, LocalDate.of(2023, 5, 17), TaskStatus.UNSCHEDULED, 0));
        taskModel.create(new SimpleTaskSchema(LocalDate.now().minusDays(7), "task26", LocalTime.of(14, 0), Duration.ofHours(1), Priority.LOW, LocalDate.of(2023, 5, 17), TaskStatus.UNSCHEDULED, 0));
        taskModel.create(new SimpleTaskSchema(LocalDate.now(), "task3", LocalTime.of(22, 0), Duration.ofHours(1), Priority.LOW, LocalDate.of(2023, 5, 17), TaskStatus.UNSCHEDULED, 0));

        //create a decomposed task
        DecomposableTaskSchema decomposedTaskSchema = new DecomposableTaskSchema(new SimpleTaskSchema(LocalDate.now().plusDays(1), "sub", LocalTime.of(22, 0), Duration.ofHours(1), Priority.LOW, LocalDate.of(2023, 5, 17), TaskStatus.UNSCHEDULED, 0));
        decomposedTaskSchema.addSubTask(new SimpleTaskSchema(LocalDate.now().plusDays(3), "sub1", LocalTime.of(10, 20), Duration.ofHours(4).plusMinutes(30), Priority.LOW, LocalDate.of(2023, 5, 17), TaskStatus.UNSCHEDULED, 0));
        decomposedTaskSchema.addSubTask(new SimpleTaskSchema(LocalDate.now().plusDays(5), "sub2", LocalTime.of(13, 20), Duration.ofHours(4).plusMinutes(30), Priority.LOW, LocalDate.of(2023, 5, 17), TaskStatus.UNSCHEDULED, 0));
        decomposedTaskSchema.addSubTask(new SimpleTaskSchema(LocalDate.now().plusDays(6), "sub3", LocalTime.of(17, 20), Duration.ofHours(4).plusMinutes(30), Priority.LOW, LocalDate.of(2023, 5, 17), TaskStatus.UNSCHEDULED, 0));
        decomposedTaskSchema.addSubTask(new SimpleTaskSchema(LocalDate.now().plusDays(9), "sub4", LocalTime.of(17, 20), Duration.ofHours(4).plusMinutes(30), Priority.LOW, LocalDate.of(2023, 5, 17), TaskStatus.UNSCHEDULED, 0));
        taskModel.create(decomposedTaskSchema);

        //TODO: consider initilizing the free slots and tasks in the dataBase always when creating a new day
        //In general when creating an object we must initialize all the database that it uses !!!

        //This is temporary test code: create some free slots to test
        LocalDate date = LocalDate.now();
        ArrayList<FreeSlotSchema> freeSlots = new ArrayList<>();
        FreeSlotSchema freeSlot1 = new FreeSlotSchema(date, LocalTime.of(8, 0), LocalTime.of(10, 0));
        FreeSlotSchema freeSlot2 = new FreeSlotSchema(date, LocalTime.of(11, 0), LocalTime.of(13, 0));
        freeSlots.add(freeSlot1);
        freeSlots.add(freeSlot2);

        dayModel.create(date);
        freeSlotModel.create(freeSlots);
        taskModel.initialize(date);

        date = date.plusDays(1);
        dayModel.create(date);
        freeSlotModel.initialize(date);
        taskModel.initialize(date);

        date = date.plusDays(1);
        freeSlots = new ArrayList<>();
        freeSlot1 = new FreeSlotSchema(date, LocalTime.of(8, 0), LocalTime.of(10, 0));
        freeSlot2 = new FreeSlotSchema(date, LocalTime.of(13, 0), LocalTime.of(16, 0));
        freeSlots.add(freeSlot1);
        freeSlots.add(freeSlot2);

        dayModel.create(date);
        freeSlotModel.create(freeSlots);
        taskModel.initialize(date);

        date = date.plusDays(1);
        freeSlots = new ArrayList<>();
        freeSlot1 = new FreeSlotSchema(date, LocalTime.of(5, 0), LocalTime.of(7, 0));
        freeSlot2 = new FreeSlotSchema(date, LocalTime.of(14, 0), LocalTime.of(16, 0));
        freeSlots.add(freeSlot1);
        freeSlots.add(freeSlot2);

        dayModel.create(date);
        freeSlotModel.create(freeSlots);
        taskModel.initialize(date);

        date = date.plusDays(1);
        freeSlots = new ArrayList<>();
        freeSlot1 = new FreeSlotSchema(date, LocalTime.of(12, 0), LocalTime.of(15, 0));
        freeSlot2 = new FreeSlotSchema(date, LocalTime.of(17, 0), LocalTime.of(20, 0));
        freeSlots.add(freeSlot1);
        freeSlots.add(freeSlot2);
        dayModel.create(date);
        freeSlotModel.create(freeSlots);
        taskModel.initialize(date);
        //end of trash code


        System.setProperty("javafx.sg.warn", "true");

//        primaryStage.setTitle("Login");
//
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
//
//        Scene scene = new Scene(fxmlLoader.load(), 840, 400);
//        primaryStage.setTitle("Login");

//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signup-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 840, 500);
//        primaryStage.setTitle("Plan Task");

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


            //save the free slot model
            freeSlotModel.save();

            //save the day model
            dayModel.save();

            //save the task model
            taskModel.save();

            //save the project model;
            projectsModel.save();

            //save the user model
            userModel.save();

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
                userModel.load();
            } else {
                usersDBFile.createNewFile();
            }


        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        super.init();
    }

    public static void main(String[] args) {
        launch();
    }
}