package Controllers.TaskControllers;

//my imports

import Exceptions.DayDoesNotHaveFreeSlotsException;
import Exceptions.FreeSlotNotFoundException;
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

    public PlanTaskController(FreeSlotModel freeSlotModel, TaskModel taskModel, PlanTaskView planTaskView) {
        this.freeSlotModel = freeSlotModel;
        this.taskModel = taskModel;
        this.planTaskView = planTaskView;


        //This is temporary test code: create some free slots to test
        LocalDate date = LocalDate.now();
        this.freeSlotModel.create(date, LocalTime.of(8, 0), LocalTime.of(10, 0));
        this.freeSlotModel.create(date, LocalTime.of(11, 0), LocalTime.of(13, 0));

        //end of temporary test code
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        //This method will be called when the user clicks on the "create task" button

        //Get the data from the view
        //TODO: implement the view to get these inputs
//        String name = "task-name";
//        Duration duration = Duration.ofHours(1);
//        String priority = "High";
//        LocalDate deadline = LocalDate.of(2020, 12, 31);
        String category = "category";
        String status = "UNSCHEDULED";
        //get the data from the view inputs
        String name = planTaskView.getTaskName();
        Duration duration = planTaskView.getDuration();
        String priority = planTaskView.getPriority();
        LocalDate deadline = planTaskView.getDeadline();
//        String category = planTaskView.getCategory();
//        String status = planTaskView.getStatus();

        //print the values of the inputs
        System.out.println("inputs: ");
        System.out.println("name: " + name);
        System.out.println("duration: " + duration);
        System.out.println("priority: " + priority);
        System.out.println("deadline: " + deadline);
        System.out.println("category: " + category);
        System.out.println("status: " + status);

        try {
            System.out.println("hora");
            //validate the inputs
            if (name == null) return;
            if (duration == null) return;
            if (priority == null || !TaskSchema.validatePriority(priority)) return;
            if (deadline == null) return;
            if (category == null) return;
            if (status == null) return;

            //create the task
            //boolean isSimpleTask = checkbox.value();
            boolean isSimpleTask = true;
            if (isSimpleTask) {
                planSimpleTaskManually(new DaySchema(LocalDate.now()), name, duration,
                        priority, deadline, category, status);

            } else {

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            //print available slots in the day
            try {
                ArrayList<FreeSlotSchema> freeslots = freeSlotModel.findMany(LocalDate.now());
                System.out.println("available slots: ");
                for (FreeSlotSchema freeSlot : freeslots) {
                    System.out.println(freeSlot.getStartTime() + " - " + freeSlot.getEndTime());
                }
            } catch (Exception e) {
            }
        }

    }

    //These are helper methods that will be called by the controller. They are here to separate logics.
    private void planSimpleTaskManually(DaySchema day, String name, Duration duration,
                                        String priority, LocalDate deadline, String category, String status) throws Exception {
        //This method will create a simple task.

        //Get the necessary data for verification
        ArrayList<FreeSlotSchema> freeslots = freeSlotModel.findMany(day.getDate()); //throws DayDoesNotHaveFreeSlotsException

        //check if a free slot is available for this task
        FreeSlotSchema availableFreeSlot = null;
        if ((availableFreeSlot = getAvailableFreeSlot(duration, freeslots)) == null) {
            throw new SimpleTaskDoesNotFitException();
        }

        if (duration.compareTo(Duration.ofMinutes(30)) < 0) {
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
            this.taskModel.create(new SimpleTaskSchema(day.getDate(), name, startTime, duration,
                    Priority.valueOf(priority), deadline, category, TaskStatus.valueOf(status), 1));
            //TODO: change the periodicity to it's actual value
        } else {
            //the free slot will be reduced (to the bottom) by the task's duration
            this.freeSlotModel.update(day.getDate(), availableFreeSlot.getStartTime(),
                    availableFreeSlot.getStartTime().plus(availableFreeSlot.getDuration().minus(duration)));
            this.taskModel.create(new SimpleTaskSchema(day.getDate(), name, startTime, duration,
                    Priority.valueOf(priority), deadline, category, TaskStatus.valueOf(status), 1));
        }

        System.out.println("task" + "\"" + name + "\"" + "created successfully");
    }

    private void planDecomposableTaskManually(DaySchema day, String name, Duration duration,
                                              Priority priority, LocalDate deadline, String category, TaskStatus status)
            throws DayDoesNotHaveFreeSlotsException, FreeSlotNotFoundException {
        //This method will create a decomposable task.
        // It's me who will choose the startTime of the Task according to the available free slots


        ArrayList<FreeSlotSchema> freeslots = freeSlotModel.findMany(day.getDate()); //throws DayDoesNotHaveFreeSlotsException
        Duration avaibleTimeForTheDay = Duration.ofMinutes(0);
        FreeSlotSchema availableFreeSlot = null;
        Duration minimalDuration = Duration.ofMinutes(30); //TODO: get the minimal duration from the settings of calendar

        for (FreeSlotSchema freeSlot : freeslots) {
            avaibleTimeForTheDay = avaibleTimeForTheDay.plus(freeSlot.getDuration());
            //We're sure from the other algorithms that every FreeSLot have at least a minDuration duration
            if (freeSlot.getDuration().compareTo(duration) >= 0) {
                availableFreeSlot = freeSlot;
                //first we'll check the minimal duration condition
                if (availableFreeSlot.getDuration().compareTo(duration.plus(minimalDuration)) >= 0) {
                    //the free slot will be reduced (to the bottom) by the task's duration, which remains the same
                    LocalTime newStartTime = availableFreeSlot.getStartTime().plus(duration);
                    this.freeSlotModel.update(day.getDate(), availableFreeSlot.getStartTime(),
                            newStartTime);
                } else {
                    //the new task will take the whole free slot:
                    duration = availableFreeSlot.getDuration(); //now the task's duration = the free slot's duration
                    this.freeSlotModel.delete(day.getDate(), availableFreeSlot.getStartTime()); //remove the free slot
                }
                SimpleTaskSchema simpleTask = new SimpleTaskSchema(day.getDate(), name, availableFreeSlot.getStartTime(), duration,
                        priority, deadline, category, status, 0);
                DecomposableTaskSchema decomposableTask = new DecomposableTaskSchema(simpleTask);
                taskModel.create(decomposableTask);
                return;
            }
        }
        if (avaibleTimeForTheDay.compareTo(duration) < 0) {
            throw new DayDoesNotHaveFreeSlotsException();
        } else {
            //the task will be  decomposed
            SimpleTaskSchema simpleTask = new SimpleTaskSchema(day.getDate(), name, availableFreeSlot.getStartTime(), duration,
                    priority, deadline, category, status, 0);
            DecomposableTaskSchema decomposableTask = new DecomposableTaskSchema(simpleTask);
            int subTasksIndex = 1;

            for (FreeSlotSchema freeSlot: freeslots){
                if(duration.compareTo(freeSlot.getDuration()) > 0){
                    duration = duration.minus(freeSlot.getDuration());
                    decomposableTask.addSubTask(new SimpleTaskSchema(day.getDate(), name += String.valueOf(subTasksIndex),
                            freeSlot.getStartTime(), freeSlot.getDuration(),
                            priority, deadline, category, status, 0));
                    subTasksIndex++;
                } else { //The case where the compareTo gives 0 will be treated here because the loop will end
                    if (freeSlot.getDuration().compareTo(duration.plus(minimalDuration)) >=0 ){
                        //the freeSlot will only be updated
                        freeSlotModel.update(day.getDate(), freeSlot.getStartTime(), freeSlot.getStartTime().plus(duration));
                    } else {
                        //it'll be deleted and the last subTask will take all the available time
                        duration = freeSlot.getDuration();
                        freeSlotModel.delete(day.getDate(), freeSlot.getStartTime());
                    }
                    decomposableTask.addSubTask(new SimpleTaskSchema(day.getDate(), name += String.valueOf(subTasksIndex),
                            freeSlot.getStartTime(), duration,
                            priority, deadline, category, status, 0));
                }
            }
            taskModel.create(decomposableTask);
        }


        //Check is the avaibleFreeSlot is not null
        SimpleTaskSchema simpleTask = new SimpleTaskSchema(day.getDate(), name, availableFreeSlot.getStartTime(), duration,
                priority, deadline, category, status, 1);
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
