package esi.tp_poo_final;

import Databases.FreeSlotsDatabase;
import Databases.FreeSlotsFileDatabase;
import Databases.TaskDatabase;
import Databases.TaskFileDatabase;
import Models.FreeSlot.FreeSlotModel;
import Models.Task.TaskModel;
import Views.PlanTaskView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 400, 300);

        //create the databases
        FreeSlotsDatabase freeSlotsDatabase = new FreeSlotsFileDatabase(/*filename*/);
        TaskDatabase taskDatabase = new TaskFileDatabase(/*filename*/);

        //create the models
        FreeSlotModel freeSlotModel = new FreeSlotModel(freeSlotsDatabase);
        TaskModel taskModel = new TaskModel(taskDatabase);
        PlanTaskView planTaskView = new PlanTaskView(freeSlotModel, taskModel);
        primaryStage.setScene(planTaskView.getScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}