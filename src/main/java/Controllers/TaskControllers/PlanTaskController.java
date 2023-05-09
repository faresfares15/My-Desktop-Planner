package Controllers.TaskControllers;

//my imports
import Exceptions.SimpleTaskDoesNotFitException;
import Exceptions.TaskOverlapsException;
import Models.Day.DaySchema;
import Models.FreeSlot.FreeSlotModel;
import Models.FreeSlot.FreeSlotSchema;
import Models.Task.TaskModel;
import Models.Task.TaskSchema;

import java.util.ArrayList;


//your imports
import Models.Task.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class PlanTaskController implements EventHandler<ActionEvent> {
    //If the user wants to create a new task, this controller will be called.
    FreeSlotModel freeSlotModel;
    TaskModel taskModel;
    public PlanTaskController(FreeSlotModel freeSlotModel, TaskModel taskModel){
        this.freeSlotModel = freeSlotModel;
        this.taskModel = taskModel;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        //This method will be called when the user clicks on the "create task" button

        //Get the data from the view
        String name = "task-name";
        int startHour = 12;
        int startMinute = 0;
        Duration duration = Duration.ofHours(1);
        String priority = "High";
        LocalDate deadline = LocalDate.of(2020, 12, 31);
        String category = "category";
        String status = "status";

        try{
            //validate the inputs
            if(name == null) return;
            if(startHour < 0 || startHour > 23) return;
            if(startMinute < 0 || startMinute > 59) return;
            if(duration == null) return;
            if(priority == null || !TaskSchema.validatePriority(priority)) return;
            if(deadline == null) return;
            if(category == null) return;
            if(status == null) return;

            //create the task
            //boolean isSimpleTask = checkbox.value();
            boolean isSimpleTask = true;
            if(isSimpleTask){
                createSimpleTask(new DaySchema(LocalDate.now()), name, startHour, startMinute, duration,
                        priority, deadline, category, status);

            }else{

            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    //These are helper methods that will be called by the controller. They are here to separate logics
    private void createSimpleTask(DaySchema day, String name, int startHour, int startMinute, Duration duration,
                                  String priority, LocalDate deadline, String category, String status) throws Exception {
        //This method will create a simple task.

        //Get the necessary data for verification
        ArrayList<FreeSlotSchema> freeslots = freeSlotModel.findMany(day.getDate()); //throws DayDoesNotHaveFreeSlotsException
        ArrayList<TaskSchema> tasks = taskModel.findMany(day.getDate()); //throws DayDoesNotHaveTasksException

        //check if the tasks doesn't overlap with another existing task in the given day
        LocalTime startTime = LocalTime.of(startHour, startMinute);
        LocalTime endTime = startTime.plus(duration);

        if(isTaskOverlapping(startTime, endTime, tasks)){
            throw new TaskOverlapsException();
        }

        //check if a free slot is available for this task
        if(!isFreeSlotAvailable(startTime, endTime, freeslots)){
            throw new SimpleTaskDoesNotFitException();
        }

        //create the task
//        taskModel.create(name, startTime, duration, deadline, category, status);


    };
    private void createDecomposableTask(String name, LocalTime startTime, Duration duration,
                                        Priority priority, LocalDate deadline, String category, TaskStatus status) {
        //This method will create a decomposable task.
        //TODO: check if the it's the controller or the view that will create the task
        SimpleTaskSchema simpleTask = new SimpleTaskSchema(name, startTime, duration,
                priority, deadline, category, status);
        DecomposableTaskSchema decomposableTaskSchema = new DecomposableTaskSchema(simpleTask);
//        taskModel.create(decomposableTaskSchema);
    }

    //Utility methods
    private boolean isTaskOverlapping(LocalTime startTime, LocalTime endTime, ArrayList<TaskSchema> tasksList){
        //This method will check if the task is overlapping with another task in the given day.
        for(TaskSchema task : tasksList){
            if(task.getStartTime().isBefore(endTime) && task.getEndTime().isAfter(startTime)){
                return true;
            }
        }
        return false;
    }
    private boolean isFreeSlotAvailable(LocalTime startTime, LocalTime endTime, ArrayList<FreeSlotSchema> freeSlotsList){
        //This method will check if a free slot is available for the given task.
        for(FreeSlotSchema freeSlot : freeSlotsList){
            if(freeSlot.getStartTime().isBefore(startTime) && freeSlot.getEndTime().isAfter(endTime)){
                return true;
            }
        }
        return false;
    }

    //Input validation methods
}
