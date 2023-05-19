package Databases;

import Exceptions.DayDoesNotHaveTasksException;
import Exceptions.TaskDoesNotExistException;
import Models.Task.TaskSchema;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public interface TaskDatabase {
    public ArrayList<TaskSchema> findMany(LocalDate date) throws DayDoesNotHaveTasksException;
    public TaskSchema create(TaskSchema taskSchema);
    public TaskSchema read(int id, LocalDate date) throws TaskDoesNotExistException;
    public TaskSchema find(LocalDate date, String name) throws TaskDoesNotExistException;
    void initialize(LocalDate date);
    public void update(TaskSchema taskSchema) throws TaskDoesNotExistException;
    public void delete(TaskSchema taskSchema) throws TaskDoesNotExistException;
    public TreeMap<LocalDate, ArrayList<TaskSchema>> findMany(LocalDate startDate, LocalDate endDate);
}
