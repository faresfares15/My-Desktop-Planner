package esi.tp_poo_final;

import Databases.*;
import Models.Day.DayModel;
import Models.FreeSlot.FreeSlotModel;
import Models.Task.TaskModel;
import Views.PlanTaskView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        System.setProperty("javafx.sg.warn", "true");
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 400, 300);

        //create the databases
        FreeSlotsDatabase freeSlotsDatabase = new FreeSlotsFileDatabase(/*filename*/);
        TaskDatabase taskDatabase = new TaskFileDatabase(/*filename*/);
        DayDatabase dayDatabase = new DayFileDataBase(/*filename*/);

        //create the models
        FreeSlotModel freeSlotModel = new FreeSlotModel(freeSlotsDatabase);
        TaskModel taskModel = new TaskModel(taskDatabase);
        DayModel dayModel = new DayModel(dayDatabase);
        PlanTaskView planTaskView = new PlanTaskView(freeSlotModel, taskModel, dayModel);
        primaryStage.setScene(planTaskView.getScene());
        primaryStage.show();

        LocalTime t1 = LocalTime.of(8, 0);
        LocalTime t2 = LocalTime.of(8, 0);
        System.out.println(t1.isBefore(t2));
        System.out.println((double) 2/(4-1));

//        class A{
//            int a;
//            public void setA(int a){
//                this.a = a;
//            }
//        }
//        A a1 = new A();
//        A a2 = new A();
//        a1.setA(1);
//        a2.setA(2);
//        ArrayList<A> list = new ArrayList<>();
//        list.add(a1);
//        list.add(a2);
//        a1.setA(11);
//        System.out.println(list.get(0).a);


    }

    public static void main(String[] args) {
        launch();
    }
}