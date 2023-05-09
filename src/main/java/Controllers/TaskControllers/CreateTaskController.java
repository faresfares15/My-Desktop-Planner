package Controllers.TaskControllers;

import Exceptions.DayDoesNotHaveFreeSlotsException;
import Exceptions.DayDoesNotHaveTasksException;
import Models.Day.DaySchema;
import Models.FreeSlot.FreeSlotModel;
import Models.FreeSlot.FreeSlotSchema;
import Models.Task.TaskModel;
import Models.Task.TaskSchema;

import java.util.ArrayList;

public class CreateTaskController {
    //If the user wants to create a new task, this controller will be called.
    FreeSlotModel freeSlotModel;
    TaskModel taskModel;
    public CreateTaskController(FreeSlotModel freeSlotModel, TaskModel taskModel){
        this.freeSlotModel = freeSlotModel;
        this.taskModel = taskModel;
    }


    //These are helper methods that will be called by the controller. They are here to separate logics
    private void createSimpleTask(DaySchema day, int startHour, int startMinute, int endHour, int endMinute){
        //This method will create a simple task.

        try {
            //Get the necessary data
            ArrayList<FreeSlotSchema> freeslots = freeSlotModel.findMany(day.getDate());
            ArrayList<TaskSchema> tasks = taskModel.findMany(day.getDate());

            //check if the tasks doesn't overlap with another existing task in the given day
            for(Task task : tasks.get(date)){
                if(task.getStartTime().isBefore(endTime) && task.getEndTime().isAfter(startTime)){
                    return true;
                }
            }
            return false;

        }catch (DayDoesNotHaveFreeSlotsException e){
            e.getMessage();
            e.printStackTrace();
        }catch (DayDoesNotHaveTasksException e){
            e.getMessage();
            e.printStackTrace();
        }


    };

    private void createDecomposableTask(){
        //This method will create a decomposable task.
    };

}
