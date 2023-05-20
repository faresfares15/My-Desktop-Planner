package Controllers.TaskControllers;

//my imports

import Exceptions.*;
import Models.Day.DayModel;
import Models.Day.DaySchema;
import Models.FreeSlot.FreeSlotModel;
import Models.FreeSlot.FreeSlotSchema;
import Models.Task.*;
import Models.Task.Priority;
import esi.tp_poo_final.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class PlanSetOfTasksController implements EventHandler<ActionEvent> {
    final FreeSlotModel freeSlotModel = HelloApplication.freeSlotModel;
    final TaskModel taskModel = HelloApplication.taskModel;
    final DayModel dayModel = HelloApplication.dayModel;
    TreeMap<TaskSchema, ArrayList<FreeSlotSchema>> preValidationMap = new TreeMap<>();
    @FXML
    Label viewTitle;
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
    @FXML
    ComboBox<String> category;
    @FXML
    TextField newCategoryName;
    @FXML
    ColorPicker newCategoryColor;
    @FXML
    Spinner<Integer> periodicitySpinner;
    @FXML
    CheckBox decomposeTaskCheckBox;
    @FXML
    Button addTaskButton;
    @FXML
    VBox tasksPanel;

    public PlanSetOfTasksController() {


        //TODO: consider initilizing the free slots and tasks in the dataBase always when creating a new day
        //In general when creating an object we must initialize all the database that it uses !!!

        //This is temporary test code: create some free slots to test
        LocalDate date = LocalDate.now();
        ArrayList<FreeSlotSchema> freeSlots = new ArrayList<>();
        FreeSlotSchema freeSlot1 = new FreeSlotSchema(date, LocalTime.of(8, 0), LocalTime.of(10, 0));
        FreeSlotSchema freeSlot2 = new FreeSlotSchema(date, LocalTime.of(11, 0), LocalTime.of(13, 0));
        freeSlots.add(freeSlot1);
//        freeSlots.add(freeSlot2);
        this.dayModel.create(date);
        this.freeSlotModel.create(freeSlots);
        this.taskModel.initialize(date);


        date = date.plusDays(1);
        freeSlots = new ArrayList<>();
        freeSlot1 = new FreeSlotSchema(date, LocalTime.of(13, 0), LocalTime.of(15, 0));
        freeSlot2 = new FreeSlotSchema(date, LocalTime.of(18, 0), LocalTime.of(20, 0));
//        freeSlots.add(freeSlot1);
//        freeSlots.add(freeSlot2);
        this.dayModel.create(date);
        this.freeSlotModel.initialize(date);
        this.taskModel.initialize(date);


        date = date.plusDays(1);
        freeSlots = new ArrayList<>();
        freeSlot1 = new FreeSlotSchema(date, LocalTime.of(13, 0), LocalTime.of(15, 0));
        freeSlot2 = new FreeSlotSchema(date, LocalTime.of(18, 0), LocalTime.of(20, 0));
        freeSlots.add(freeSlot1);
        freeSlots.add(freeSlot2);

        this.dayModel.create(date);
        this.freeSlotModel.create(freeSlots);
        this.taskModel.initialize(date);


        date = date.plusDays(1);
        freeSlots = new ArrayList<>();
        freeSlot1 = new FreeSlotSchema(date, LocalTime.of(13, 0), LocalTime.of(15, 0));
        freeSlot2 = new FreeSlotSchema(date, LocalTime.of(18, 0), LocalTime.of(20, 0));
        freeSlots.add(freeSlot1);
        freeSlots.add(freeSlot2);

        this.dayModel.create(date);
        this.freeSlotModel.create(freeSlots);
        this.taskModel.initialize(date);


        date = date.plusDays(1);
        freeSlots = new ArrayList<>();
        freeSlot1 = new FreeSlotSchema(date, LocalTime.of(13, 0), LocalTime.of(15, 0));
        freeSlot2 = new FreeSlotSchema(date, LocalTime.of(18, 0), LocalTime.of(20, 0));
        freeSlots.add(freeSlot1);
        freeSlots.add(freeSlot2);
        this.dayModel.create(date);
        this.freeSlotModel.create(freeSlots);
        this.taskModel.initialize(date);



//        this.freeSlotModel.create(date, LocalTime.of(8, 0), LocalTime.of(13, 0));
    }

    @FXML
    public void initialize() {
        //This method will be called when the view is loaded

        //set the view title


        //setting the start time hours spinner
//        TODO: Set the start time to the actual moment
        taskNameField.setPromptText("Task Name");
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
        valueFactory.setValue(LocalTime.now().getHour());
        startTimeHoursSpinner.setValueFactory(valueFactory);

        //setting the start time minutes spinner
        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        valueFactory.setValue(LocalTime.now().getMinute());
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

        //setting the category combo box
        ObservableList<String> categories = FXCollections.observableArrayList("Work", "Study", "Personal", "New category");
        category.setValue("Work");
        category.getItems().clear();
        category.getItems().setAll(categories);
        category.setOnAction(event -> {
            // If the user has selected "New category," then make the category text field visible.
            if (category.getValue().equals("New category")) {
                newCategoryName.setVisible(true);
                newCategoryColor.setVisible(true);
            } else {
                newCategoryName.setVisible(false);
                newCategoryColor.setVisible(false);
            }
        });

        //set periodicity spinner
        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        valueFactory.setValue(0);
        periodicitySpinner.setValueFactory(valueFactory);

        //disable periodicity spinner if the checkbox is selected, and enable it if it is not
        decomposeTaskCheckBox.setOnAction(event -> {
            if (decomposeTaskCheckBox.isSelected()) {
                periodicitySpinner.setDisable(true);
            } else {
                periodicitySpinner.setDisable(false);
            }
        });

        //setting the deadline picker
        deadlinePicker.setValue(LocalDate.now());

        //initializing the tasks panel's border
        tasksPanel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        tasksPanel.setPadding(new Insets(5, 0,5, 0));

    }

//    PlanTaskView2 planTaskView;

    @Override
    public void handle(ActionEvent actionEvent) {
        //This method will be called when the user clicks on the "create task" button

        //Get the data from the view
        //TODO: implement the view to get these inputs
//        String name = "task-name";
//        Duration duration = Duration.ofHours(1);
//        String priority = "High";
//        LocalDate deadline = LocalDate.of(2020, 12, 31);
        String status = "UNSCHEDULED";
        //get the data from the view inputs

//        TODO: give default values in case the input was empty
        ViewInfos viewInfos = new ViewInfos();
        String name = viewInfos.getTaskName();
        LocalTime startTime = viewInfos.getStartTime();
        Duration duration = viewInfos.getDuration();
        String priority = viewInfos.getPriority();
        LocalDate deadline = viewInfos.getDeadline();
        String category = "";
        try{
             category = viewInfos.getCategory();
        }catch (Exception e){
            showErrorMessage(e.getMessage());
            return;
        }
//        String status = viewInfos.getStatus();

        boolean success = true;
        if(success){
            Label label = new Label(name);
            label.setFont(new Font("Arial", 15));
            label.setPadding(new Insets(10, 0, 10, 0));
//            HBox taskRow = new HBox(label);

            // Add the Label to the VBox.
            tasksPanel.getChildren().add(label);
            return;
        }

        //print the values of the inputs
        System.out.println("inputs: ");
        System.out.println("name: " + name);
        System.out.println("startTime: " + startTime);
        System.out.println("duration: " + duration);
        System.out.println("priority: " + priority);
        System.out.println("category: " + category);
        System.out.println("deadline: " + deadline);

        try {
            //validate the inputs
            //TODO: move these exceptions to the view info class
            if (name == "") throw new EmptyRequiredFieldException();
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
            DecomposableTaskSchema longTask = new DecomposableTaskSchema(new SimpleTaskSchema("longTask", Duration.ofHours(4).plus(Duration.ofMinutes(30)), Priority.HIGH, LocalDate.now().plusDays(4), "category", TaskStatus.UNSCHEDULED, 0));
            tasks.add(longTask);
            tasks.add(new SimpleTaskSchema("fares task1", Duration.ofHours(1), Priority.LOW, LocalDate.now().plusDays(4), "category", TaskStatus.UNSCHEDULED, 0));
            tasks.add(new SimpleTaskSchema("fares task2", Duration.ofHours(2), Priority.MEDIUM, LocalDate.now(), "category", TaskStatus.UNSCHEDULED, 0));
            tasks.add(new SimpleTaskSchema("fares task3", Duration.ofHours(3), Priority.HIGH, LocalDate.now(), "category", TaskStatus.UNSCHEDULED, 0));
            autoPlanSetOfTasks2(tasks, LocalDate.now(), LocalDate.now().plusDays(3));

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            showErrorMessage(e.getMessage());
        } finally {
            //print available slots in the day
            try {
//                ArrayList<FreeSlotSchema> freeslots = freeSlotModel.findMany(LocalDate.now());
                ArrayList<DaySchema> days = dayModel.findMany(LocalDate.now(), LocalDate.now().plusYears(1));
                System.out.println("Content of the days: ");
                for (DaySchema day : days) {
                    System.out.println(day.getDate());
                    System.out.println("available slots: ");
                    for (FreeSlotSchema freeSlot : freeSlotModel.findMany(day.getDate())) {
                        System.out.println(freeSlot.getStartTime() + " - " + freeSlot.getEndTime());
                    }
                    System.out.println("Tasks for this day are: ");
                    for (TaskSchema task : taskModel.findMany(day.getDate())) {
                        if (task instanceof DecomposableTaskSchema) {
                            ArrayList<SimpleTaskSchema> subTasks = ((DecomposableTaskSchema) task).getSubTasks();
                            for (int i = 1; i < subTasks.size(); i++) {
                                if (subTasks.get(i).getDate().equals(day.getDate())){
                                    TaskSchema subtask = subTasks.get(i);
                                    System.out.println(subtask.getName() + " - " + subtask.getDuration());
                                }
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
    public void moveToCalendarView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("calendar-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 840, 400);
        Stage stage = (Stage) viewTitle.getScene().getWindow();
        stage.setTitle("Calendar");

        //center the view on the user's screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - scene.getHeight()) / 2);

        stage.setScene(scene);
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
        SimpleTaskSchema simpleTask = null;

        //check the minimal duration condition
        Duration minimalDuration = Duration.ofMinutes(30); //TODO: get the minimal duration from the settings of calendar
        if (availableFreeSlot.getDuration().compareTo((duration.plus(minimalDuration))) <= 0) {
            //allocate the whole free slot for this task
            duration = availableFreeSlot.getDuration();
            this.freeSlotModel.delete(day.getDate(), availableFreeSlot.getStartTime());
//            return (SimpleTaskSchema) this.taskModel.create(new SimpleTaskSchema(day.getDate(), name, taskStartTime, duration,
//                    Priority.valueOf(priority), deadline, category, TaskStatus.valueOf(status), 0));
            ArrayList<FreeSlotSchema> freeSlotsList = new ArrayList<>();
            freeSlotsList.add(availableFreeSlot);
            simpleTask = new SimpleTaskSchema(day.getDate(), name, taskStartTime, duration,
                    Priority.valueOf(priority), deadline, category, TaskStatus.valueOf(status), 0);
            preValidationMap.put(simpleTask, freeSlotsList);
            return simpleTask;
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
                    Priority.valueOf(priority), deadline, category, TaskStatus.valueOf(status), 0));
            //TODO: tell ayyoub to apply the change since he's the one who made it
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
        DecomposableTaskSchema decomposableTask = new DecomposableTaskSchema();

        for (FreeSlotSchema freeSlot : freeslots) {
            avaibleTimeForTheDay = avaibleTimeForTheDay.plus(freeSlot.getDuration());
            //We're sure from the other algorithms that every FreeSLot have at least a minDuration duration
            if (freeSlot.getDuration().compareTo(duration) >= 0) {
                availableFreeSlot = freeSlot;
                //first we'll check the minimal duration condition

                if (availableFreeSlot.getDuration().compareTo(duration.plus(minimalDuration)) >= 0) {
                    //the free slot will be reduced (to the bottom) by the task's duration, which remains the same
                    LocalTime newStartTime = availableFreeSlot.getStartTime().plus(duration);
                    SimpleTaskSchema simpleTask = new SimpleTaskSchema(day.getDate(), name, availableFreeSlot.getStartTime(), duration,
                            priority, deadline, category, status, 0);
                    decomposableTask = new DecomposableTaskSchema(simpleTask);
                    this.freeSlotModel.update(day.getDate(), availableFreeSlot.getStartTime(),
                            newStartTime);
                } else {
                    //the new task will take the whole free slot:
                    duration = availableFreeSlot.getDuration(); //now the task's duration = the free slot's duration
                    SimpleTaskSchema simpleTask = new SimpleTaskSchema(day.getDate(), name, availableFreeSlot.getStartTime(), duration,
                            priority, deadline, category, status, 0);
                    decomposableTask = new DecomposableTaskSchema(simpleTask);
                    this.freeSlotModel.delete(day.getDate(), availableFreeSlot.getStartTime()); //remove the free slot
                }
//                SimpleTaskSchema simpleTask = new SimpleTaskSchema(day.getDate(), name, availableFreeSlot.getStartTime(), duration,
//                        priority, deadline, category, status, 0);
//                DecomposableTaskSchema decomposableTask = new DecomposableTaskSchema(simpleTask);
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
            decomposableTask = new DecomposableTaskSchema(simpleTask);
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
                        decomposableTask.addSubTask(new SimpleTaskSchema(day.getDate(), name + subTasksIndex,
                                freeSlot.getStartTime(), duration,
                                priority, deadline, category, status, 0));
                        freeSlotModel.update(day.getDate(), freeSlot.getStartTime(), freeSlot.getStartTime().plus(duration));
                    } else {
                        //it'll be deleted and the last subTask will take all the available time
                        duration = freeSlot.getDuration();
                        decomposableTask.addSubTask(new SimpleTaskSchema(day.getDate(), name + subTasksIndex,
                                freeSlot.getStartTime(), duration,
                                priority, deadline, category, status, 0));
//                        freeSlotModel.delete(day.getDate(), freeSlot.getStartTime());
                        it.remove();
                    }
//                    decomposableTask.addSubTask(new SimpleTaskSchema(day.getDate(), name + subTasksIndex,
//                            freeSlot.getStartTime(), duration,
//                            priority, deadline, category, status, 0));
                    //Since we decomposed it completely, end the loop
                    break;
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

                        try {
                            planDecomposableTaskAutomatically(day, task.getName(), task.getDuration(), task.getPriority(),
                                    task.getDeadline(), task.getCategory(), TaskStatus.SCHEDULED);
                            isScheduled = true;
                            break;
                        } catch (Exception e) {
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

                        try {
                            planDecomposableTaskAutomatically(day, task.getName(), task.getDuration(), task.getPriority(),
                                    task.getDeadline(), task.getCategory(), TaskStatus.SCHEDULED);
                            isScheduled = true;
                            break;
                        } catch (Exception e) {
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

    private void autoPlanSetOfTasks2(ArrayList<TaskSchema> tasksList, LocalDate startOfPeriod, LocalDate endOfPeriod) throws Exception {
        Comparator<TaskSchema> comparator = Comparator.comparing(TaskSchema::getDeadline).thenComparing(TaskSchema::getPriority).thenComparing(TaskSchema::getDuration);
        tasksList.sort(comparator);
        //TODO: Gotta put every task in the dataBase to track the statistics in the APP
        Iterator<TaskSchema> it = tasksList.iterator();
        ArrayList<DaySchema> days = dayModel.findMany(startOfPeriod, endOfPeriod);
        //The wrappers that we'll use to pass the values by reference
        LocalTimeWrapper startTimeWrapper = new LocalTimeWrapper();
        DurationWrapper avaibleTimeWrapper = new DurationWrapper();
        FreeSlotSchema availableFreeSlot = new FreeSlotSchema();
        Duration minimalDuration = Duration.ofMinutes(30);
        Duration taskDurationLeft = Duration.ZERO;
        //TODO: change it to get it from the settings or from the calendar

        TaskSchema task;
        boolean isScheduled = false;
        ArrayList<FreeSlotSchema> freeSlots = new ArrayList<>();
        int subTaskIndex = 1;
        while (it.hasNext()) {
            task = it.next();
            // all the exceptions will be handled in the handle method only
            isScheduled = false;
            if (task instanceof SimpleTaskSchema) {
                for (DaySchema day : days) {
                    if (day.getDate().isBefore(task.getDeadline()) || day.getDate().equals(task.getDeadline())) {
                        //Plan it with the automatic plan method
                        //the plan methods will take care of updating the freeSlotsDataBase and TaskDataBase

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
                    } else {
                        task.setStatus(TaskStatus.UNSCHEDULED);
                        break;
                    }
                }
            } else { //Decomposable task

                try {
                    //we'll send the deadline as the endOf period
                    if (calculateAvaibleTime(startOfPeriod, task.getDeadline(), availableFreeSlot, avaibleTimeWrapper, task.getDuration())) {
                        //this method returns true if there's a free slot that can fit the task completely
                        //so if that's the case it'll just be planned to that freeSlot.
                        task.setDate(availableFreeSlot.getDate()); //VERY IMPORTANT FOR NULL SAFETY !!!

                        // 1\There's a freeSLot that can contain it completely
                        if (availableFreeSlot.getDate().isAfter(task.getDeadline()))
                            throw new DayIsAfterDeadlineException();
                        //first we'll check the minimal duration condition

                        if (availableFreeSlot.getDuration().compareTo(avaibleTimeWrapper.getDuration().plus(minimalDuration)) >= 0) {
                            //the free slot will be reduced (to the bottom) by the task's duration, which remains the same

                            LocalTime newStartTime = availableFreeSlot.getStartTime().plus(task.getDuration());
                            this.freeSlotModel.update(availableFreeSlot.getDate(), availableFreeSlot.getStartTime(),
                                    newStartTime);
                        } else {
                            //the new task will take the whole free slot:
                            task.setDuration(availableFreeSlot.getDuration()); //now the task's duration = the free slot's duration
                            this.freeSlotModel.delete(availableFreeSlot.getDate(), availableFreeSlot.getStartTime()); //remove the free slot
                        }
                        taskModel.create(task);
                        System.out.println("decomposableTask" + " \"" + task.getName() + "\" " +
                                "created successfully on: " + availableFreeSlot.getDate() + " at: " +
                                availableFreeSlot.getStartTime() + " with duration: " + task.getDuration());
                    } else if (avaibleTimeWrapper.getDuration().compareTo(task.getDuration()) >= 0) {
                        // 2\Decompose the task over multiple days
                        //Loop over the days
                        //Check if there's available time BEFORE THE DEADLINE !!! already done by sending the deadline as the ednOf the period
                        subTaskIndex = 1;
                        taskDurationLeft = task.getDuration();
                        for (DaySchema day : days) {

                            freeSlots = freeSlotModel.findMany(day.getDate());
                            //We'll use an itertaor to be able to remove the free slots that we'll use
                            Iterator<FreeSlotSchema> freeSlotIterator = freeSlots.iterator();

                            while (freeSlotIterator.hasNext() && taskDurationLeft.compareTo(Duration.ZERO) >= 0){

                                availableFreeSlot = freeSlotIterator.next();
                                if(taskDurationLeft.compareTo(availableFreeSlot.getDuration()) > 0) {
                                    taskDurationLeft = taskDurationLeft.minus(availableFreeSlot.getDuration());
                                    ((DecomposableTaskSchema) task).addSubTask(new SimpleTaskSchema(day.getDate(),
                                            task.getName() + subTaskIndex, availableFreeSlot.getStartTime(),
                                            availableFreeSlot.getDuration(), task.getPriority(), task.getDeadline(),
                                            task.getCategory(), task.getStatus(), 0));
                                    subTaskIndex++;
                                    freeSlotIterator.remove();
                                } else {
                                    if (availableFreeSlot.getDuration().compareTo(taskDurationLeft.plus(minimalDuration)) >= 0){
                                        ((DecomposableTaskSchema) task).addSubTask(new SimpleTaskSchema(day.getDate(),
                                                task.getName() + subTaskIndex, availableFreeSlot.getStartTime(),
                                                taskDurationLeft, task.getPriority(), task.getDeadline(),
                                                task.getCategory(), task.getStatus(), 0));
                                        freeSlotModel.update(day.getDate(), availableFreeSlot.getStartTime(), availableFreeSlot.getStartTime().plus(taskDurationLeft));
                                    } else {
                                        taskDurationLeft = availableFreeSlot.getDuration();
                                        ((DecomposableTaskSchema) task).addSubTask(new SimpleTaskSchema(day.getDate(),
                                                task.getName() + subTaskIndex, availableFreeSlot.getStartTime(),
                                                taskDurationLeft, task.getPriority(), task.getDeadline(),
                                                task.getCategory(), task.getStatus(), 0));
                                        freeSlotIterator.remove();
                                    }
//                                    ((DecomposableTaskSchema) task).addSubTask(new SimpleTaskSchema(day.getDate(),
//                                            task.getName() + subTaskIndex, availableFreeSlot.getStartTime(),
//                                            taskDurationLeft, task.getPriority(), task.getDeadline(),
//                                            task.getCategory(), task.getStatus(), 0));
                                    taskDurationLeft = Duration.ZERO;

                                    break;
                                }
                            }
                            if (taskDurationLeft.compareTo(Duration.ZERO) <=0){
                                //The day of a decomposable task is the day of it's first sub task
                                task.setDate(((DecomposableTaskSchema)task).getSubTasks().get(1).getDate());
                                task.setStartTime(((DecomposableTaskSchema)task).getSubTasks().get(1).getStartTime());
                                taskModel.create(task);
                                System.out.println("decomposableTask" + " \"" + task.getName() + "\" " +
                                        "created successfully on: " + availableFreeSlot.getDate() + " at: " +
                                        availableFreeSlot.getStartTime() + " with duration: " + task.getDuration());
                                break;
                            }
                        }


                    } else {
                        // 3\The task is not scheduled
                        throw new TaskCanNotBeDecomposedException();
                    }
                    isScheduled = true;
                    task.setStatus(TaskStatus.SCHEDULED);
                    ((DecomposableTaskSchema) task).getSubTasks().get(0).setStatus(TaskStatus.SCHEDULED);
                } catch (Exception e) {
                    //pass to the nextIteration
                    System.out.println(e.getMessage());
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

    private Duration calculateAvaibleTime(LocalDate day) throws DayDoesNotHaveFreeSlotsException {
        Duration avaibleTime = Duration.ZERO;
        ArrayList<FreeSlotSchema> freeSoltsOfTheDay = freeSlotModel.findMany(day);
        for (FreeSlotSchema freeSlot : freeSoltsOfTheDay) {
            avaibleTime = avaibleTime.plus(freeSlot.getDuration());
        }
        return avaibleTime;
    }

    private Duration calculateAvaibleTime(LocalDate startOPeriod, LocalDate endofPeriod) throws DayDoesNotHaveFreeSlotsException {
        Duration duration = Duration.ZERO;
        ArrayList<DaySchema> days = dayModel.findMany(startOPeriod, endofPeriod);
        for (DaySchema day : days) {
            duration = duration.plus(calculateAvaibleTime(day.getDate()));
        }
        return duration;
    }

    private boolean calculateAvaibleTime(LocalDate day,
                                         FreeSlotSchema avaibleFreeSolt, DurationWrapper durationWrapper,
                                         Duration taskDuration) throws DayDoesNotHaveFreeSlotsException {
        //THis method will calculate the avaible time for a given day

        //The wrapper is used to pass the values by reference so they can be used changed by this method
//        durationWrapper.setDuration(Duration.ZERO);
        boolean isEnoughTimeForTask = false;
        //It'll also check if the day has a freeSlot that can completely contain a decomposable task
        ArrayList<FreeSlotSchema> freeSoltsOfTheDay = freeSlotModel.findMany(day);
        for (FreeSlotSchema freeSlot : freeSoltsOfTheDay) {
            if (freeSlot.getDuration().compareTo(taskDuration) >= 0 && !isEnoughTimeForTask) {
                //So that he'll enter her one time only to get the first freeSlot that can take it completly
                isEnoughTimeForTask = true;
                //change the properties of the object
                avaibleFreeSolt.setStartTime(freeSlot.getStartTime());
                avaibleFreeSolt.setEndTime(freeSlot.getEndTime());
                avaibleFreeSolt.setDayDate(freeSlot.getDate());
                //It'll also set the beginning of that freeSlot so the plan method can use it directly to plan a decomposable task
            }
            durationWrapper.setDuration(durationWrapper.getDuration().plus(freeSlot.getDuration()));

        }
        return isEnoughTimeForTask;
    }

    private boolean calculateAvaibleTime(LocalDate startOPeriod, LocalDate endofPeriod, FreeSlotSchema avaibleFreeSlot,
                                         DurationWrapper durationWrapper, Duration taskDuration) throws DayDoesNotHaveFreeSlotsException {

        //The wrapper is used to pass the values by reference, so they can be used changed by this method
        durationWrapper.setDuration(Duration.ZERO);
        boolean isEnoughTimeForTask = false;
        ArrayList<DaySchema> days = dayModel.findMany(startOPeriod, endofPeriod);
        for (DaySchema day : days) {
            if (calculateAvaibleTime(day.getDate(), avaibleFreeSlot, durationWrapper, taskDuration)) {
                isEnoughTimeForTask = true;
            }
        }
        return isEnoughTimeForTask;
    }

    //If the user wants to create a new task, this controller will be called.
    private class SubTaskInfo {
        //a container class useful to create an array of subtasks ( in planDcompmosableTaskManually)
        LocalTime startTime;
        Duration duration;

        SubTaskInfo(LocalTime startTime, Duration duration) {
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

    private class ViewInfos {
        String getTaskName() {
            if (taskNameField.getText().isBlank()) return "No title";
            return taskNameField.getText();
        }

        LocalTime getStartTime() {
            return LocalTime.of(startTimeHoursSpinner.getValue(), startTimeMinutesSpinner.getValue());
        }

        public Duration getDuration() {
            return Duration.ofHours(durationHoursSpinner.getValue()).plusMinutes(durationMinutesSpinner.getValue());
        }

        public String getPriority() {
            return priorityComboBox.getValue();
        }

        public LocalDate getDeadline() {
            return deadlinePicker.getValue();
        }
        public String getCategory() throws Exception{
            String categoryName = category.getValue();
            if (categoryName == null) throw new Exception("No category selected");
            if(categoryName.equals("New category")){
                categoryName = newCategoryName.getText();
                if(categoryName.isBlank()) throw new Exception("No name provided for new category");
                //check if the category already exists (from the list of the categories combobox)
                for(String category : category.getItems()){
                    if(category.equals(categoryName)) throw new Exception("Category already exists");
                }
            }

            return categoryName;

        }

    }

    private class LocalTimeWrapper {
        LocalTime startTime;

        public LocalTimeWrapper(LocalTime startTime) {
            this.startTime = startTime;
        }

        public LocalTimeWrapper() {
            this.startTime = LocalTime.MIN;
        }

        LocalTime getStartTime() {
            return startTime;
        }

        void setStartTime(LocalTime startTime) {
            this.startTime = startTime;
        }
    }

    private class DurationWrapper {
        Duration duration;

        public DurationWrapper(Duration duration) {
            this.duration = duration;
        }

        public DurationWrapper() {
            this.duration = Duration.ZERO;
        }

        Duration getDuration() {
            return duration;
        }

        void setDuration(Duration duration) {
            this.duration = duration;
        }
    }
    private boolean isConfirmed(){
        Alert confirmationMessage = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationMessage.setContentText("Free slot can be scheduled in the date and time. Do you confirm the operation?");
        confirmationMessage.setHeaderText("Free slot scheduling confirmation");
        confirmationMessage.setTitle("Free slot confirmation");
        Optional<ButtonType> clickedButton = confirmationMessage.showAndWait();

        return clickedButton.get() == ButtonType.OK;
    }
    private void showErrorMessage(String message){
        Alert errorMessage = new Alert(Alert.AlertType.ERROR);
        errorMessage.setContentText(message);
        errorMessage.setHeaderText("Error");
        errorMessage.setTitle("Error");
        errorMessage.showAndWait();
    }
    private void showSuccessMessage(String message){
        Alert successMessage = new Alert(Alert.AlertType.INFORMATION);
        successMessage.setContentText(message);
        successMessage.setHeaderText("Success");
        successMessage.setTitle("Success");
        successMessage.showAndWait();
    }
    public void planTasks(){
        showSuccessMessage("Tasks planned successfully");
        tasksPanel.getChildren().clear();
    }
}
