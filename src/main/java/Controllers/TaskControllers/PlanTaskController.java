package Controllers.TaskControllers;

//my imports

import Exceptions.*;
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
import java.util.Comparator;
import java.util.Iterator;

public class PlanTaskController implements EventHandler<ActionEvent> {
    //If the user wants to create a new task, this controller will be called.
    protected class SubTaskInfo{
        //a container class useful to create an array of subtasks ( in planDcompmosableTaskManually)
        LocalTime startTime;
        Duration duration;
        SubTaskInfo(LocalTime startTime, Duration duration){
            this.startTime = startTime;
            this.duration = duration;
        }

        public LocalTime getStartTime() {
            return startTime;
        }

        public Duration getDuration() {
            return duration;
        }
    }
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
//        this.freeSlotModel.create(date, LocalTime.of(8, 0), LocalTime.of(13, 0));

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
        //TODO: give default values incase the input was empty
        String name = planTaskView.getTaskName();
        LocalTime startTime = planTaskView.getStartTime();
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
            //validate the inputs
            if (name == null) throw new EmptyRequiredFieldException();
            if (duration == null) throw new EmptyRequiredFieldException();
            if (priority == null || !TaskSchema.validatePriority(priority)) throw new EmptyRequiredFieldException();
            if (deadline == null) throw new EmptyRequiredFieldException();
            if (category == null) throw new EmptyRequiredFieldException();
            if (status == null) throw new EmptyRequiredFieldException();
            //TODO: verify the day in the past exception

            //create the task
            //boolean isSimpleTask = checkbox.value();
            boolean isManual = true; //Change it to run tests
            boolean isSimpleTask = false; //Change it to run tests

            switch (Boolean.toString(isManual)){
                case "true":
                //planning a task manually
                    switch (Boolean.toString(isSimpleTask)){
                        case "true":
                        //planning a simple task manually
                            planSimpleTaskManually(new DaySchema(LocalDate.now()), name, startTime, duration,
                                    priority, deadline, category, status, 0);
                            break;
                        case "false":
                        //planning a decomposable task manually
//                            planDecomposableTaskManually(new DaySchema(LocalDate.now()), name, startTime, duration,
//                                    priority, deadline, category, status);
                            SubTaskInfo subtaskInfo1 = new SubTaskInfo(LocalTime.of(9, 0), Duration.ofHours(2));
                            SubTaskInfo subtaskInfo2 = new SubTaskInfo(LocalTime.of(12, 0), Duration.ofHours(1));
                            ArrayList<SubTaskInfo> subtasksInfos = new ArrayList<>(){{
                                add(subtaskInfo1);
                                add(subtaskInfo2);
                            }};
                            //trash
                                for(SubTaskInfo subtaskInfo : subtasksInfos){
                                    System.out.println(subtaskInfo.getStartTime());
                                    System.out.println(subtaskInfo.getDuration());
                                }
                            //
                            planDecomposableTaskManually(new DaySchema(LocalDate.now()), name, subtasksInfos, priority, deadline,  category, status);
                            break;
                    }
                    break;

                case "false":
                //planning a task automatically
                    switch (Boolean.toString(isSimpleTask)){
                        case "true":
                        //planning a simple task automatically
                            planSimpleTaskAutomatically(new DaySchema(LocalDate.now()), name, duration,
                                    priority, deadline, category, status);
                            break;
                        case "false":
                        //planning a decomposable task automatically
                            planDecomposableTaskAutomatically(new DaySchema(LocalDate.now()), name, duration,
                                    Priority.valueOf(priority), deadline, category, TaskStatus.valueOf(status));
                            break;
                    }
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
                System.out.println("Tasks for this day are: ");
                for (TaskSchema task : taskModel.findMany(LocalDate.now())) {
                    if (task instanceof DecomposableTaskSchema){
                        for (TaskSchema subtask : ((DecomposableTaskSchema)task).getSubTasks()){
                            System.out.println(subtask.getName() + " - " + subtask.getName() + " - " + subtask.getDuration());
                        }
                    } else {
                        System.out.println(task.getName() + " - " + task.getName() + " - " + task.getDuration());
                    }

                }
            } catch (Exception e) {

            }
        }

    }
    //These are helper methods that will be called by the controller. They are here to separate logics.
    private SimpleTaskSchema planSimpleTaskManually(DaySchema day, String name, LocalTime taskStartTime, Duration duration,
                                        String priority, LocalDate deadline, String category, String status, int periodicity) throws Exception {
        //This method will create a simple task.

        //Get the necessary data for verification
        ArrayList<FreeSlotSchema> freeslots = freeSlotModel.findMany(day.getDate()); //throws DayDoesNotHaveFreeSlotsException

        //check if a free slot is available for this task
        FreeSlotSchema availableFreeSlot = null;
        if ((availableFreeSlot = getAvailableFreeSlot(taskStartTime, duration, freeslots)) == null) {
            throw new SimpleTaskDoesNotFitException();
        }

        //check the minimal duration condition
        Duration minimalDuration = Duration.ofMinutes(30); //TODO: get the minimal duration from the settings of calendar
        if (availableFreeSlot.getDuration().compareTo((duration.plus(minimalDuration))) <= 0) {
            //allocate the whole free slot for this task
            duration = availableFreeSlot.getDuration();
            this.freeSlotModel.delete(day.getDate(), availableFreeSlot.getStartTime());
            return (SimpleTaskSchema) this.taskModel.create(new SimpleTaskSchema(day.getDate(), name, taskStartTime, duration,
                    Priority.valueOf(priority), deadline, category, TaskStatus.valueOf(status), 1));
        }else {
            //allocate a part of the free slot for this task and split the task into two parts)
                //delete the current free slot
                //prepare to create two free slots after removing the time allocated for the task, but:
                //if the size of the first free slot is less than the minimal duration: don't create it but merge its duration to the task
                //if the size of the second free slot is less than the minimal duration: don't create it but merge its duration to the task
            this.freeSlotModel.delete(day.getDate(), availableFreeSlot.getStartTime());

            //start time of the first free slot is the start time of the original slot
            //end time of the first free slot is the start time of the task
            //start time of the second free slot is the end time of the task
            //end time of the second free slot is the end time of the original task

            if(Duration.between(availableFreeSlot.getStartTime(), taskStartTime).compareTo(minimalDuration) <= 0){
                //don't create the new sub free slot, and add its duration to the task
                duration = duration.plus(Duration.between(availableFreeSlot.getStartTime(), taskStartTime));
                taskStartTime = availableFreeSlot.getStartTime();
            }else{
                //create the free slot
                this.freeSlotModel.create(day.getDate(), availableFreeSlot.getStartTime(), taskStartTime);
            }

            LocalTime taskEndTime = taskStartTime.plus(duration);
            if(Duration.between(taskEndTime, availableFreeSlot.getEndTime()).compareTo(minimalDuration) <= 0){
                //don't create the new sub free slot, and add its duration to the task
                duration = duration.plus(Duration.between(taskEndTime, availableFreeSlot.getEndTime()));
            }else {
                //create the free slot
                this.freeSlotModel.create(day.getDate(), taskEndTime, availableFreeSlot.getEndTime());
            }

            return (SimpleTaskSchema) this.taskModel.create(new SimpleTaskSchema(day.getDate(), name, taskStartTime, duration,
                    Priority.valueOf(priority), deadline, category, TaskStatus.valueOf(status), 1));
        }
    }
    public void planDecomposableTaskManually(DaySchema day, String name, ArrayList<SubTaskInfo> subtasks, String priority, LocalDate deadline, String category, String status)
    throws Exception{
        //This method will create a decomposable task, with the decompositions manually given as an array

        //Get the free slots of the given day
        ArrayList<FreeSlotSchema> freeslots = freeSlotModel.findMany(day.getDate()); //throws DayDoesNotHaveFreeSlotsException

        //check that no subtask overlaps with another sub task (start time + duration of current task < start time of next task)
        for(int i = 0; i < subtasks.size() - 1; i++) {
            if (subtasks.get(i).getStartTime().plus(subtasks.get(i).getDuration()).isAfter(subtasks.get(i + 1).getStartTime())) {
                throw new TasksOverlapException();
            }
        }

        //if one of the sub tasks doesn't have a free slot in the freeslots array list, throw an exception
        for(SubTaskInfo subtask : subtasks){
            if(getAvailableFreeSlot(subtask.getStartTime(), subtask.getDuration(), freeslots) == null){
                throw new SimpleTaskDoesNotFitException();
            }
        }

        //create a DecomposableTaskSchema object
        DecomposableTaskSchema decomposableTask = new DecomposableTaskSchema(new SimpleTaskSchema(day.getDate(), name+"1", subtasks.get(0).getStartTime(), subtasks.get(0).getDuration(),
                Priority.valueOf(priority), deadline, category, TaskStatus.valueOf(status), 0));

        //add the subtasks to the DecomposableTaskSchema object
        for(SubTaskInfo subtask : subtasks){
            decomposableTask.addSubTask(planSimpleTaskManually(day, name+subtasks.indexOf(subtask), subtask.getStartTime(), subtask.getDuration(),
                    priority, deadline, category, status, 0));
        }


    };
    private void planSimpleTaskAutomatically(DaySchema day, String name, Duration duration,
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
            this.freeSlotModel.update(day.getDate(), availableFreeSlot.getStartTime(), availableFreeSlot.getStartTime().plus(duration));
            this.taskModel.create(new SimpleTaskSchema(day.getDate(), name, startTime, duration,
                    Priority.valueOf(priority), deadline, category, TaskStatus.valueOf(status), 1));
        }

        System.out.println("task"+ " \""+ name +"\" "+ "created successfully");
    }

    private void planDecomposableTaskAutomatically(DaySchema day, String name, Duration duration,
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
                System.out.println("task"+ " \""+ name +"\" "+ "created successfully");
                return;
            }
        }
        if (avaibleTimeForTheDay.compareTo(duration) < 0) {
            throw new DayDoesNotHaveFreeSlotsException();
        } else {
            //the task will be  decomposed to sub-tasks and the first one will start at the first free slot
            SimpleTaskSchema simpleTask = new SimpleTaskSchema(day.getDate(), name, freeslots.get(0).getStartTime(), duration,
                    priority, deadline, category, status, 0);
            DecomposableTaskSchema decomposableTask = new DecomposableTaskSchema(simpleTask);
            int subTasksIndex = 1;
            Iterator<FreeSlotSchema> it = freeslots.iterator();
            FreeSlotSchema freeSlot = null;
            while(it.hasNext()){
                freeSlot = it.next();
                if(duration.compareTo(freeSlot.getDuration()) > 0){
                    duration = duration.minus(freeSlot.getDuration());
                    decomposableTask.addSubTask(new SimpleTaskSchema(day.getDate(), name + String.valueOf(subTasksIndex),
                            freeSlot.getStartTime(), freeSlot.getDuration(),
                            priority, deadline, category, status, 0));
                    subTasksIndex++;
//                    freeSlotModel.delete(freeSlot.getDayDate(), freeSlot.getStartTime());
                    it.remove();
                } else { //The case where the compareTo gives 0 will be treated here because the loop will end
                    if (freeSlot.getDuration().compareTo(duration.plus(minimalDuration)) >=0 ){
                        //the freeSlot will only be updated
                        freeSlotModel.update(day.getDate(), freeSlot.getStartTime(), freeSlot.getStartTime().plus(duration));
                    } else {
                        //it'll be deleted and the last subTask will take all the available time
                        duration = freeSlot.getDuration();
//                        freeSlotModel.delete(day.getDate(), freeSlot.getStartTime());
                        it.remove();
                    }
                    decomposableTask.addSubTask(new SimpleTaskSchema(day.getDate(), name + String.valueOf(subTasksIndex),
                            freeSlot.getStartTime(), duration,
                            priority, deadline, category, status, 0));
                }
            }

            taskModel.create(decomposableTask);
            System.out.println("task"+ " \""+ name +"\" "+ "created successfully");
        }

    }
    private void autoPlanSetOfTasks(ArrayList<TaskSchema> tasksList){
        Comparator<TaskSchema> comparator = Comparator.comparing(TaskSchema::getDeadline).thenComparing(TaskSchema::getPriority);
        tasksList.sort(comparator);
        Iterator<TaskSchema> it = tasksList.iterator();
        TaskSchema task = null;
        while(it.hasNext()){
            task = it.next();
            // all the exceptions will be handled in the handle method only

        }

    }

    //Utility methods
    private FreeSlotSchema getAvailableFreeSlot(LocalTime taskStartTime, Duration taskDuration, ArrayList<FreeSlotSchema> freeSlotsList) {
        //This method will check if a free slot is available for the given task.
        LocalTime taskEndTime = taskStartTime.plus(taskDuration);
        for (FreeSlotSchema freeSlot : freeSlotsList) {
            //if the free slot ends before the start of the task => "continue"
            //if the free slot starts after the end of the task => "break"
            //otherwise: check if the free slot can contain the task
            if (freeSlot.getEndTime().isBefore(taskStartTime)) continue;

            if (freeSlot.getStartTime().isAfter(taskEndTime)) break;

            if (freeSlot.getStartTime().isBefore(taskStartTime.plusMinutes(1)) && freeSlot.getEndTime().isAfter(taskEndTime.minusMinutes(1))) return freeSlot;

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
