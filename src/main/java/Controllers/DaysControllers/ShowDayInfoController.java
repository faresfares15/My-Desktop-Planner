package Controllers.DaysControllers;

import Models.Day.DayModel;
import Models.Day.DaySchema;
import Models.Task.*;
import Models.User.UserModel;
import esi.tp_poo_final.HelloApplication;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.time.LocalDate;

public class ShowDayInfoController implements EventHandler<ActionEvent> {
    final TaskModel taskModel = HelloApplication.taskModel;
    final DayModel dayModel = HelloApplication.dayModel;
    final UserModel userModel = HelloApplication.userModel;
    DaySchema today; //the day that we want to show info about

    @Override
    public void handle(ActionEvent event) {
        //TODO: search for a place where to put this algorithm
        int numberOfTasksScheduledToday = 0;
        //Evaluating the progress of the day
        for (LocalDate dayInTheMap : taskModel.findAll().keySet()) {
            for (TaskSchema taskInTheMap : taskModel.findAll().get(dayInTheMap)) {
                //check if it's decomposable or not
                if (taskInTheMap instanceof DecomposableTaskSchema) {
                    //check if it's start time is before today
                    if (taskInTheMap.getDate().isBefore(LocalDate.now()) || taskInTheMap.getDate().equals(LocalDate.now())) {
                        //loop through all of it subtasks
                        for (SimpleTaskSchema subtask : ((DecomposableTaskSchema) taskInTheMap).getSubTasks()) {
                            //check if the subtask is scheduled today
                            if (subtask.getDate().equals(LocalDate.now())) {
                                numberOfTasksScheduledToday++;
                            }
                        }
                    }
                } else { //Then if it's a simpleTask so, just check if it has the same date as today
                    if (taskInTheMap.getDate().equals(LocalDate.now())) {
                        numberOfTasksScheduledToday++;
                    }

                }
            }
        }
        double todaysPercentage = (double) today.getNumberOfTasksCompletedOnThisDay() / numberOfTasksScheduledToday;
    }
}
