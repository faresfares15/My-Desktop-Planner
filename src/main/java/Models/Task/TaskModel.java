package Models.Task;

import Databases.TaskDatabase;
import Exceptions.DayDoesNotHaveTasksException;

import java.time.LocalDate;
import java.util.ArrayList;

public class TaskModel {
    private TaskDatabase taskDatabase;
    public TaskModel(TaskDatabase taskDatabase){
        this.taskDatabase = taskDatabase;
    }
    public ArrayList<TaskSchema> findMany(LocalDate date) throws DayDoesNotHaveTasksException {
        return this.taskDatabase.findMany(date);
    }
}
