package Databases;

import Exceptions.DayDoesNotHaveTasksException;
import Exceptions.TaskDoesNotExistException;
import Models.Task.TaskSchema;

import java.time.LocalDate;
import java.util.ArrayList;

public interface TaskDatabase {
    public ArrayList<TaskSchema> findMany(LocalDate date) throws DayDoesNotHaveTasksException;
    public TaskSchema create(TaskSchema taskSchema);
    public TaskSchema read(int id, LocalDate date) throws TaskDoesNotExistException;
    public void update(TaskSchema taskSchema) throws TaskDoesNotExistException;
    public void delete(TaskSchema taskSchema) throws TaskDoesNotExistException;
}
