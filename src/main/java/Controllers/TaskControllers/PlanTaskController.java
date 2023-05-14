package Controllers.TaskControllers;

//my imports

import Exceptions.*;
import Models.Day.DayModel;
import Models.Day.DaySchema;
import Models.FreeSlot.FreeSlotModel;
import Models.FreeSlot.FreeSlotSchema;
import Models.Task.TaskModel;
import Models.Task.TaskSchema;

import java.net.URL;
import java.util.ArrayList;

//your imports
import Models.Task.*;
import Views.PlanTaskView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ResourceBundle;

public class PlanTaskController implements EventHandler<ActionEvent> {
    FreeSlotModel freeSlotModel;
    TaskModel taskModel;
    PlanTaskView planTaskView;
    DayModel dayModel;

    public PlanTaskController(FreeSlotModel freeSlotModel, TaskModel taskModel, DayModel dayModel, PlanTaskView planTaskView) {
        this.freeSlotModel = freeSlotModel;
        this.taskModel = taskModel;
        this.planTaskView = planTaskView;
        this.dayModel = dayModel;

        //TODO: consider initilizing the free slots and tasks in the dataBase always when creating a new day
        //In general when creating an object we must initialize all the database that it uses !!!

        //This is temporary test code: create some free slots to test
        LocalDate date = LocalDate.now();
        ArrayList<FreeSlotSchema> freeSlots = new ArrayList<>();
        FreeSlotSchema freeSlot1 = new FreeSlotSchema(date, LocalTime.of(8, 0), LocalTime.of(10, 0));
        FreeSlotSchema freeSlot2 = new FreeSlotSchema(date, LocalTime.of(11, 0), LocalTime.of(13, 0));
        freeSlots.add(freeSlot1);
//        freeSlots.add(freeSlot2);
        this.freeSlotModel.create(freeSlots);
        this.taskModel.initialize(date);
        this.dayModel.create(date);

        date = date.plusDays(1);
        freeSlots = new ArrayList<>();
        freeSlot1 = new FreeSlotSchema(date, LocalTime.of(13, 0), LocalTime.of(15, 0));
        freeSlot2 = new FreeSlotSchema(date, LocalTime.of(18, 0), LocalTime.of(20, 0));
//        freeSlots.add(freeSlot1);
//        freeSlots.add(freeSlot2);
        this.freeSlotModel.initialize(date);
        this.taskModel.initialize(date);
        this.dayModel.create(date);

        date = date.plusDays(1);
        freeSlots = new ArrayList<>();
        freeSlot1 = new FreeSlotSchema(date, LocalTime.of(13, 0), LocalTime.of(15, 0));
        freeSlot2 = new FreeSlotSchema(date, LocalTime.of(18, 0), LocalTime.of(20, 0));
//        freeSlots.add(freeSlot1);
//        freeSlots.add(freeSlot2);
        this.freeSlotModel.initialize(date);
        this.taskModel.initialize(date);
        this.dayModel.create(date);

        date = date.plusDays(1);
        freeSlots = new ArrayList<>();
        freeSlot1 = new FreeSlotSchema(date, LocalTime.of(13, 0), LocalTime.of(15, 0));
        freeSlot2 = new FreeSlotSchema(date, LocalTime.of(18, 0), LocalTime.of(20, 0));
//        freeSlots.add(freeSlot1);
//        freeSlots.add(freeSlot2);
        this.freeSlotModel.initialize(date);
        this.taskModel.initialize(date);
        this.dayModel.create(date);

        date = date.plusDays(1);
        freeSlots = new ArrayList<>();
        freeSlot1 = new FreeSlotSchema(date, LocalTime.of(13, 0), LocalTime.of(15, 0));
        freeSlot2 = new FreeSlotSchema(date, LocalTime.of(18, 0), LocalTime.of(20, 0));
        freeSlots.add(freeSlot1);
        freeSlots.add(freeSlot2);
        this.freeSlotModel.create(freeSlots);
        this.taskModel.initialize(date);
        this.dayModel.create(date);


//        this.freeSlotModel.create(date, LocalTime.of(8, 0), LocalTime.of(13, 0));
    }
    //If the user wants to create a new task, this controller will be called.
    private class SubTaskInfo{
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
    @FXML
    TextField taskNameField;
    @FXML
    Spinner<Integer> startTimeHoursSpinner;
    @FXML
    Spinner<Integer> startTimeMinutesSpinner;
    @FXML
    Spinner<Integer> durationHoursSpinner;
    @FXML
    Spinner<Integer> durationMinutesSpinner;
    @FXML
    ComboBox<String> priorityComboBox;
    @FXML
    DatePicker deadlinePicker;
    private class CreateTaskViewInfos{
        String getTaskName(){
            return taskNameField.getText();
        }
        LocalTime getStartTime(){
            return LocalTime.of(startTimeHoursSpinner.getValue(), startTimeMinutesSpinner.getValue());
        }
        public Duration getDuration(){
            return Duration.ofHours(durationHoursSpinner.getValue()).plusMinutes(durationMinutesSpinner.getValue());
        }
        public String getPriority() {
            return priorityComboBox.getValue();
        }
        public LocalDate getDeadline() {
            return deadlinePicker.getValue();
        }
    }

//    PlanTaskView2 planTaskView;

    public PlanTaskController(){
        //default constructor
    }

    public PlanTaskController(FreeSlotModel freeSlotModel, TaskModel taskModel) {
        this.freeSlotModel = freeSlotModel;
        this.taskModel = taskModel;
//        this.planTaskView = planTaskView;
    }

    @FXML
    public void initialize() {
        //This method will be called when the view is loaded

        //setting the start time hours spinner
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
        valueFactory.setValue(0);
        startTimeHoursSpinner.setValueFactory(valueFactory);

        //setting the start time minutes spinner
        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        valueFactory.setValue(0);
        startTimeMinutesSpinner.setValueFactory(valueFactory);

        //setting the duration hours spinner
        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
        valueFactory.setValue(0);
        durationHoursSpinner.setValueFactory(valueFactory);

        //setting the duration minutes spinner
        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        valueFactory.setValue(0);
        durationMinutesSpinner.setValueFactory(valueFactory);

        //setting the priority combo box
        ObservableList<String> priorities = FXCollections.observableArrayList("LOW", "MEDIUM", "HIGH");
        priorityComboBox.setValue("LOW");
        priorityComboBox.setItems(priorities);

        //setting the deadline picker
        deadlinePicker.setValue(LocalDate.now());
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

//        TODO: give default values incase the input was empty
        CreateTaskViewInfos viewInfos = new CreateTaskViewInfos();
        String name = viewInfos.getTaskName();
        LocalTime startTime = viewInfos.getStartTime();
        Duration duration = viewInfos.getDuration();
        String priority = viewInfos.getPriority();
        LocalDate deadline = viewInfos.getDeadline();
//        String category = viewInfos.getCategory();
//        String status = viewInfos.getStatus();

        //print the values of the inputs
        System.out.println("inputs: ");
        System.out.println("name: " + name);
        System.out.println("startTime: " + startTime);
        System.out.println("duration: " + duration);
        System.out.println("priority: " + priority);
        System.out.println("deadline: " + deadline);
//        System.out.println("category: " + category);
//        System.out.println("status: " + status);
//        System.out.println("trash break");
        if(true) return;

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

//            switch (Boolean.toString(isManual)) {
//                case "true":
//                    //planning a task manually
//                    switch (Boolean.toString(isSimpleTask)) {
//                        case "true":
//                            //planning a simple task manually
//                            planSimpleTaskManually(new DaySchema(LocalDate.now()), name, startTime, duration,
//                                    priority, deadline, category, status, 0);
//                            break;
//                        case "false":
//                            //planning a decomposable task manually
////                            planDecomposableTaskManually(new DaySchema(LocalDate.now()), name, startTime, duration,
////                                    priority, deadline, category, status);
//                            SubTaskInfo subtaskInfo1 = new SubTaskInfo(LocalTime.of(9, 0), Duration.ofHours(2));
//                            SubTaskInfo subtaskInfo2 = new SubTaskInfo(LocalTime.of(12, 0), Duration.ofHours(1));
//                            ArrayList<SubTaskInfo> subtasksInfos = new ArrayList<>() {{
//                                add(subtaskInfo1);
//                                add(subtaskInfo2);
//                            }};
//                            planDecomposableTaskManually(new DaySchema(LocalDate.now()), name, subtasksInfos, priority,
//                                    deadline, category, status);
//                            break;
//                    }
//                    break;
//
//                case "false":
//                    //planning a task automatically
//                    switch (Boolean.toString(isSimpleTask)) {
//                        case "true":
//                            //planning a simple task automatically
//                            planSimpleTaskAutomatically(new DaySchema(LocalDate.now()), name, duration,
//                                    priority, deadline, category, status);
//                            break;
//                        case "false":
//                            //planning a decomposable task automatically
//                            planDecomposableTaskAutomatically(new DaySchema(LocalDate.now()), name, duration,
//                                    Priority.valueOf(priority), deadline, category, TaskStatus.valueOf(status));
//                            break;
//                    }
//            }

            //Test the auto Plan set of tasks
            ArrayList<TaskSchema> tasks = new ArrayList<>();
            tasks.add(new SimpleTaskSchema("task1", Duration.ofHours(1), Priority.LOW, LocalDate.now().plusDays(4), "category", TaskStatus.UNSCHEDULED,0 ));
            tasks.add(new SimpleTaskSchema("task2", Duration.ofHours(2), Priority.MEDIUM, LocalDate.now(), "category", TaskStatus.UNSCHEDULED,0 ));
            tasks.add(new SimpleTaskSchema("task3", Duration.ofHours(3), Priority.HIGH, LocalDate.now(), "category", TaskStatus.UNSCHEDULED,0 ));
            autoPlanSetOfTasks(tasks, LocalDate.now(), LocalDate.now().plusDays(3));

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            //print available slots in the day
            try {
//                ArrayList<FreeSlotSchema> freeslots = freeSlotModel.findMany(LocalDate.now());
                ArrayList<DaySchema> days = dayModel.findMany(LocalDate.now(), LocalDate.now().plusYears(1));
                System.out.println("Content of the days: ");
                for (DaySchema day: days){
                    System.out.println(day.getDate());
                    System.out.println("available slots: ");
                    for (FreeSlotSchema freeSlot : freeSlotModel.findMany(day.getDate())) {
                        System.out.println(freeSlot.getStartTime() + " - " + freeSlot.getEndTime());
                    }
                    System.out.println("Tasks for this day are: ");
                    for (TaskSchema task : taskModel.findMany(day.getDate())) {
                        if (task instanceof DecomposableTaskSchema) {
                            for (TaskSchema subtask : ((DecomposableTaskSchema) task).getSubTasks()) {
                                System.out.println(subtask.getName() + " - " + subtask.getDuration());
                            }
                        } else {
                            System.out.println(task.getName() + " - " + task.getDuration());
                        }

                    }
                    System.out.println("-------------------------------------------------");
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());

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
        } else {
            //allocate a part of the free slot for this task and split the task into two parts
            //delete the current free slot
            //prepare to create two free slots after removing the time allocated for the task, but:
            //if the size of the first free slot is less than the minimal duration: don't create it but merge its duration to the task
            //if the size of the second free slot is less than the minimal duration: don't create it but merge its duration to the task
            this.freeSlotModel.delete(day.getDate(), availableFreeSlot.getStartTime());

            //start time of the first free slot is the start time of the original slot
            //end time of the first free slot is the start time of the task
            //start time of the second free slot is the end time of the task
            //end time of the second free slot is the end time of the original task

            if (Duration.between(availableFreeSlot.getStartTime(), taskStartTime).compareTo(minimalDuration) <= 0) {
                //don't create the new sub free slot, and add its duration to the task
                duration = duration.plus(Duration.between(availableFreeSlot.getStartTime(), taskStartTime));
                taskStartTime = availableFreeSlot.getStartTime();
            } else {
                //create the free slot
                this.freeSlotModel.create(day.getDate(), availableFreeSlot.getStartTime(), taskStartTime);
            }

            LocalTime taskEndTime = taskStartTime.plus(duration);
            if (Duration.between(taskEndTime, availableFreeSlot.getEndTime()).compareTo(minimalDuration) <= 0) {
                //don't create the new sub free slot, and add its duration to the task
                duration = duration.plus(Duration.between(taskEndTime, availableFreeSlot.getEndTime()));
            } else {
                //create the free slot
                this.freeSlotModel.create(day.getDate(), taskEndTime, availableFreeSlot.getEndTime());
            }

            return (SimpleTaskSchema) this.taskModel.create(new SimpleTaskSchema(day.getDate(), name, taskStartTime, duration,
                    Priority.valueOf(priority), deadline, category, TaskStatus.valueOf(status), 1));
        }
    }

    public void planDecomposableTaskManually(DaySchema day, String name, ArrayList<SubTaskInfo> subtasks, String priority, LocalDate deadline, String category, String status)
            throws Exception {
        //This method will create a decomposable task, with the decompositions manually given as an array

        //Get the free slots of the given day
        ArrayList<FreeSlotSchema> freeslots = freeSlotModel.findMany(day.getDate()); //throws DayDoesNotHaveFreeSlotsException

        //check that no subtask overlaps with another subtask (start time + duration of current task < start time of next task)
        for (int i = 0; i < subtasks.size() - 1; i++) {
            if (subtasks.get(i).getStartTime().plus(subtasks.get(i).getDuration()).isAfter(subtasks.get(i + 1).getStartTime())) {
                throw new TasksOverlapException();
            }
        }

        //if one of the subtasks doesn't have a free slot in the freeslots array list, throw an exception
        for (SubTaskInfo subtask : subtasks) {
            if (getAvailableFreeSlot(subtask.getStartTime(), subtask.getDuration(), freeslots) == null) {
                throw new SimpleTaskDoesNotFitException();
            }
        }

        //create a DecomposableTaskSchema object
        DecomposableTaskSchema decomposableTask = new DecomposableTaskSchema(new SimpleTaskSchema(day.getDate(), name, subtasks.get(0).getStartTime(), subtasks.get(0).getDuration(),
                Priority.valueOf(priority), deadline, category, TaskStatus.valueOf(status), 0));

        //add the subtasks to the DecomposableTaskSchema object
        for (SubTaskInfo subtask : subtasks) {
            decomposableTask.addSubTask(planSimpleTaskManually(day, name + (1 + subtasks.indexOf(subtask)), subtask.getStartTime(), subtask.getDuration(),
                    priority, deadline, category, status, 0));
        }


    }

    private void planSimpleTaskAutomatically(DaySchema day, String name, Duration duration,
                                                         String priority, LocalDate deadline, String category, String status) throws Exception {
        //This method will create a simple task.
        //TODO: check wether you're going to add periodicity or not

        //Get the necessary data for verification
        ArrayList<FreeSlotSchema> freeslots = freeSlotModel.findMany(day.getDate()); //throws DayDoesNotHaveFreeSlotsException

        //check if a free slot is available for this task
        FreeSlotSchema availableFreeSlot = null;
        if ((availableFreeSlot = getAvailableFreeSlot(duration, freeslots)) == null) {
            throw new SimpleTaskDoesNotFitException();
        }
        Duration minimalDuration = Duration.ofMinutes(30); //TODO: get the minimal duration from the settings of calendar
        if (duration.compareTo(minimalDuration) < 0) {
            //TODO: decide what to do for tasks with duration < 30 minutes (minimal duration)
            //TODO: make their duration = minDuration
        }

        //set the start time of the task
        LocalTime startTime = availableFreeSlot.getStartTime();

        //check the minimal duration condition
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
                    Priority.valueOf(priority), deadline, category, TaskStatus.valueOf(status), 0));
        }
        System.out.println("task" + " \"" + name + "\" " + "created successfully on: " + day.getDate() + " at: " + startTime + " with duration: " + duration);
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
                System.out.println("decomposablTask" + " \"" + name + "\" " + "created successfully on: " + day.getDate() + " at: " + freeSlot.getStartTime() + " with duration: " + duration);
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
            while (it.hasNext()) {
                freeSlot = it.next();
                if (duration.compareTo(freeSlot.getDuration()) > 0) {
                    duration = duration.minus(freeSlot.getDuration());
                    decomposableTask.addSubTask(new SimpleTaskSchema(day.getDate(), name + subTasksIndex,
                            freeSlot.getStartTime(), freeSlot.getDuration(),
                            priority, deadline, category, status, 0));
                    subTasksIndex++;
//                    freeSlotModel.delete(freeSlot.getDayDate(), freeSlot.getStartTime());
                    it.remove();
                } else { //The case where the compareTo gives 0 will be treated here because the loop will end
                    if (freeSlot.getDuration().compareTo(duration.plus(minimalDuration)) >= 0) {
                        //the freeSlot will only be updated
                        freeSlotModel.update(day.getDate(), freeSlot.getStartTime(), freeSlot.getStartTime().plus(duration));
                    } else {
                        //it'll be deleted and the last subTask will take all the available time
                        duration = freeSlot.getDuration();
//                        freeSlotModel.delete(day.getDate(), freeSlot.getStartTime());
                        it.remove();
                    }
                    decomposableTask.addSubTask(new SimpleTaskSchema(day.getDate(), name + subTasksIndex,
                            freeSlot.getStartTime(), duration,
                            priority, deadline, category, status, 0));
                }
            }

            taskModel.create(decomposableTask);
            System.out.println("decomposablTask" + " \"" + name + "\" " + "created successfully on: " + day.getDate() + " with duration: " + duration);
        }

    }

    private void autoPlanSetOfTasks(ArrayList<TaskSchema> tasksList) throws Exception {
        Comparator<TaskSchema> comparator = Comparator.comparing(TaskSchema::getDeadline).thenComparing(TaskSchema::getPriority);
        tasksList.sort(comparator);
        //TODO: Gotta put every task in the dataBase to track the statistics in the APP
        Iterator<TaskSchema> it = tasksList.iterator();
        ArrayList<DaySchema> days = dayModel.findMany(LocalDate.now(), LocalDate.now().plusYears(1));
        //The plus 1 year is temporary for now
        TaskSchema task;
        boolean isScheduled = false;
        while (it.hasNext()) {
            task = it.next();
            // all the exceptions will be handled in the handle method only
            for (DaySchema day : days) {
                isScheduled = false;
                if (day.getDate().isBefore(task.getDeadline()) || day.getDate().equals(task.getDeadline())) {
                    //Plan it with the automatic plan method
                    //the plan methods will take care of updating the freeSlotsDataBase and TaskDataBase
                    if (task instanceof SimpleTaskSchema) {

                        try {
                            planSimpleTaskAutomatically(day, task.getName(), task.getDuration(), task.getPriority().toString(),
                                    task.getDeadline(), task.getCategory(), TaskStatus.SCHEDULED.toString());
                            isScheduled = true;
                            break;
                            //Because we're sure that if the execution will reach this point that we have found an
                            // emptySlot otherwise it'll throw an exception


                        } catch (Exception e) {
                            //pass to the nextIteration

                        }

                    } else if (task instanceof DecomposableTaskSchema) {

                        try{
                            planDecomposableTaskAutomatically(day, task.getName(), task.getDuration(), task.getPriority(),
                                    task.getDeadline(), task.getCategory(), TaskStatus.SCHEDULED);
                            isScheduled = true;
                            break;
                        } catch (Exception e){
                            //pass to the nextIteration
                        }

                    }
                } else {
                    task.setStatus(TaskStatus.UNSCHEDULED);
                    break;
                    //put it here even though it looks redandent but it'll save a looooooooot of iterations
                }
            }
            if (!isScheduled) {
                task.setStatus(TaskStatus.UNSCHEDULED);
                System.out.println(task.getName() + " is not scheduled");

            } else {
                //Remove the task from the list, and it's in the DB now
                it.remove();
            }

        }

    }

    private void autoPlanSetOfTasks(ArrayList<TaskSchema> tasksList, LocalDate startOfPeriod, LocalDate endOfPeriod) throws Exception {
        Comparator<TaskSchema> comparator = Comparator.comparing(TaskSchema::getDeadline).thenComparing(TaskSchema::getPriority);
        tasksList.sort(comparator);
        //TODO: Gotta put every task in the dataBase to track the statistics in the APP
        Iterator<TaskSchema> it = tasksList.iterator();
        ArrayList<DaySchema> days = dayModel.findMany(startOfPeriod, endOfPeriod);
        //The plus 1 year is temporary for now
        TaskSchema task;
        boolean isScheduled = false;
        while (it.hasNext()) {
            task = it.next();
            // all the exceptions will be handled in the handle method only
            for (DaySchema day : days) {
                isScheduled = false;
                if (day.getDate().isBefore(task.getDeadline()) || day.getDate().equals(task.getDeadline())) {
                    //Plan it with the automatic plan method
                    //the plan methods will take care of updating the freeSlotsDataBase and TaskDataBase
                    if (task instanceof SimpleTaskSchema) {

                        try {
                            planSimpleTaskAutomatically(day, task.getName(), task.getDuration(), task.getPriority().toString(),
                                    task.getDeadline(), task.getCategory(), TaskStatus.SCHEDULED.toString());
                            isScheduled = true;
                            break;
                            //Because we're sure that if the execution will reach this point that we have found an
                            // emptySlot otherwise it'll throw an exception


                        } catch (Exception e) {
                            //pass to the nextIteration

                        }

                    } else if (task instanceof DecomposableTaskSchema) {

                        try{
                            planDecomposableTaskAutomatically(day, task.getName(), task.getDuration(), task.getPriority(),
                                    task.getDeadline(), task.getCategory(), TaskStatus.SCHEDULED);
                            isScheduled = true;
                            break;
                        } catch (Exception e){
                            //pass to the nextIteration
                        }

                    }
                } else {
                    task.setStatus(TaskStatus.UNSCHEDULED);
                    break;
                    //put it here even though it looks redandent but it'll save a looooooooot of iterations
                }
            }
            if (!isScheduled) {
                task.setStatus(TaskStatus.UNSCHEDULED);
                System.out.println(task.getName() + " is not scheduled");

            } else {
                //Remove the task from the list, and it's in the DB now
                it.remove();
            }

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

            if (freeSlot.getStartTime().isBefore(taskStartTime.plusMinutes(1)) && freeSlot.getEndTime().isAfter(taskEndTime.minusMinutes(1)))
                return freeSlot;

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


    //setters for models and views
    public void setFreeSlotModel(FreeSlotModel freeSlotModel) {
        this.freeSlotModel = freeSlotModel;


        //This is temporary test code: create some free slots to test
        LocalDate date = LocalDate.now();
        this.freeSlotModel.create(date, LocalTime.of(8, 0), LocalTime.of(10, 0));
        this.freeSlotModel.create(date, LocalTime.of(11, 0), LocalTime.of(13, 0));
        //end of test code
    }

    public void setTaskModel(TaskModel taskModel) {
        this.taskModel = taskModel;
    }

//    public void setPlanTaskView(PlanTaskView2 planTaskView) {
//        this.planTaskView = planTaskView;
//    }
}
