package Controllers.TaskControllers;

import Models.Task.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class CreateTaskController {
    //If the user wants to create a new task, this controller will be called.


    //These are helper methods that will be called by the controller. They are here to separate logics
    private void createSimpleTask() {
        //This method will create a simple task.
    }

    private void createDecomposableTask(TaskModel taskModel, String name, LocalTime startTime, Duration duration,
                                        Priority priority, LocalDate deadline, String category, TaskStatus status) {
        //This method will create a decomposable task.
        //TODO: check if the it's the controller or the view that will create the task
        SimpleTaskSchema simpleTask = new SimpleTaskSchema(name, startTime, duration,
                priority, deadline, category, status);
        DecomposableTaskSchema decomposableTaskSchema = new DecomposableTaskSchema(simpleTask);
        taskModel.create(decomposableTaskSchema);
    }

}
