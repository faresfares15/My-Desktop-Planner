package Controllers.TaskControllers;

//my imports

import Exceptions.DayDoesNotHaveFreeSlotsException;
import Exceptions.SimpleTaskDoesNotFitException;
import Models.Day.DaySchema;
import Models.FreeSlot.FreeSlotModel;
import Models.FreeSlot.FreeSlotSchema;
import Models.Task.TaskModel;
import Models.Task.TaskSchema;

import java.util.ArrayList;


//your imports
import Models.Task.*;
import Views.PlanTaskView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class PlanTaskController implements EventHandler<ActionEvent> {
    //If the user wants to create a new task, this controller will be called.
    FreeSlotModel freeSlotModel;
    TaskModel taskModel;
    PlanTaskView planTaskView;
    public PlanTaskController(FreeSlotModel freeSlotModel, TaskModel taskModel, PlanTaskView planTaskView){
        this.freeSlotModel = freeSlotModel;
        this.taskModel = taskModel;
        this.planTaskView = planTaskView;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        //This method will be called when the user clicks on the "create task" button

        //Get the data from the view
        //TODO: implement the view to get these inputs
        String name = "task-name";
        Duration duration = Duration.ofHours(1);
        String priority = "High";
        LocalDate deadline = LocalDate.of(2020, 12, 31);
        String category = "category";
        String status = "status";

        try{
            System.out.println("hora");
            //validate the inputs
            if(name == null) return;
            if(duration == null) return;
            if(priority == null || !TaskSchema.validatePriority(priority)) return;
            if(deadline == null) return;
            if(category == null) return;
            if(status == null) return;

            //create the task
            //boolean isSimpleTask = checkbox.value();
            boolean isSimpleTask = true;
            if(isSimpleTask){
                planSimpleTaskManually(new DaySchema(LocalDate.now()), name, duration,
                        priority, deadline, category, status);

            } else {

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    //These are helper methods that will be called by the controller. They are here to separate logics.
    private void planSimpleTaskManually(DaySchema day, String name, Duration duration,
                                        String priority, LocalDate deadline, String category, String status) throws Exception {
        //This method will create a simple task.

        //Get the necessary data for verification
        ArrayList<FreeSlotSchema> freeslots = freeSlotModel.findMany(day.getDate()); //throws DayDoesNotHaveFreeSlotsException
        ArrayList<TaskSchema> tasks = taskModel.findMany(day.getDate()); //throws DayDoesNotHaveTasksException

        //check if a free slot is available for this task
        FreeSlotSchema availableFreeSlot = null;
        if ((availableFreeSlot = getAvailableFreeSlot(duration, freeslots)) == null) {
            throw new SimpleTaskDoesNotFitException();
        }

        if(duration.compareTo(Duration.ofMinutes(30)) < 0){
            //TODO: decide what to do for tasks with duration < 30 minutes (minimal duration)
        }

        //set the start time of the task
        LocalTime startTime = availableFreeSlot.getStartTime();

        //check the minimal duration condition
        Duration minimalDuration = Duration.ofMinutes(30); //TODO: get the minimal duration from the settings of calendar
        if (availableFreeSlot.getDuration().compareTo((duration.plus(minimalDuration))) < 0) {
            //the new task will take the whole free slot:
            duration = availableFreeSlot.getDuration(); //now the task's duration = the free slot's duration
            this.freeSlotModel.delete(day.getDate(), availableFreeSlot.getStartTime()); //remove the free slot
            this.taskModel.create(new SimpleTaskSchema(name, startTime, duration,
                    Priority.valueOf(priority), deadline, category, TaskStatus.valueOf(status)));
        } else {
            //the free slot will be reduced (to the bottom) by the task's duration, which remains the same
            this.freeSlotModel.update(day.getDate(), availableFreeSlot.getStartTime(), availableFreeSlot.getDuration().minus(duration));
            this.taskModel.create(new SimpleTaskSchema(name, startTime, duration,
                    Priority.valueOf(priority), deadline, category, TaskStatus.valueOf(status)));
        }
    }

    private void planDecomposableTaskManually(DaySchema day, String name, LocalTime startTime, Duration duration,
                                              Priority priority, LocalDate deadline, String category, TaskStatus status)
            throws DayDoesNotHaveFreeSlotsException {
        //This method will create a decomposable task.


        ArrayList<FreeSlotSchema> freeslots = freeSlotModel.findMany(day.getDate()); //throws DayDoesNotHaveFreeSlotsException
        Duration avaibleTimeForTheDay = Duration.ofMinutes(0);
        FreeSlotSchema availableFreeSlot = null;

        for (FreeSlotSchema freeSlot : freeslots) {
            avaibleTimeForTheDay = avaibleTimeForTheDay.plus(freeSlot.getDuration());
            //We're sure from the other algorithms that every FreeSLot have at least a minDuration duration
            if (freeSlot.getDuration().compareTo(duration) >= 0){
                availableFreeSlot = freeSlot;
                //and we'll create the task in this free slot
                SimpleTaskSchema simpleTask = new SimpleTaskSchema(name, startTime, duration,
                        priority, deadline, category, status);
                DecomposableTaskSchema decomposableTask = new DecomposableTaskSchema(simpleTask);
                taskModel.create(decomposableTask);
                //TODO: think about deleting the freeSlot or nah
                return;
            }
        }
        if (avaibleTimeForTheDay.compareTo(duration) < 0) {
            throw new DayDoesNotHaveFreeSlotsException();
        } else {
            //the task will be either decomposed or affected to the freeSlot directly
        }




        SimpleTaskSchema simpleTask = new SimpleTaskSchema(name, startTime, duration,
                priority, deadline, category, status);
        DecomposableTaskSchema decomposableTaskSchema = new DecomposableTaskSchema(simpleTask);
        taskModel.create(decomposableTaskSchema);
    }

    //Utility methods
    private FreeSlotSchema getAvailableFreeSlot(LocalTime startTime, LocalTime endTime, ArrayList<FreeSlotSchema> freeSlotsList) {
        //This method will check if a free slot is available for the given task.
        for (FreeSlotSchema freeSlot : freeSlotsList) {
            if (freeSlot.getStartTime().isBefore(startTime) && freeSlot.getEndTime().isAfter(endTime)) {
                return freeSlot;
            }
        }
        return null;
    }

    private FreeSlotSchema getAvailableFreeSlot(Duration duration, ArrayList<FreeSlotSchema> freeSlotsList) {
        //This method will check if a free slot is available for the given task.
        for (FreeSlotSchema freeSlot : freeSlotsList) {
            if (freeSlot.getDuration().compareTo(duration) >= 0) {
                return freeSlot;
            }
        }
        return null;
    }

    //Input validation methods
}
