package Databases;

import Exceptions.DayDoesNotHaveTasksException;
import Exceptions.TaskDoesNotExistException;
import Models.Task.TaskSchema;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TreeMap;

public interface TaskDatabase extends Serializable {
    public ArrayList<TaskSchema> findMany(LocalDate date) throws DayDoesNotHaveTasksException;
    public TaskSchema create(TaskSchema taskSchema);
    public TaskSchema read(int id, LocalDate date) throws TaskDoesNotExistException;
    public TaskSchema find(LocalDate date, int id) throws TaskDoesNotExistException;
    public TaskSchema find(int id) throws TaskDoesNotExistException;
    public ArrayList<TaskSchema> findAll(String taskType);
    void initialize(LocalDate date);
    public void update(LocalDate date, int id, String newName) throws TaskDoesNotExistException;
    public void delete(TaskSchema taskSchema) throws TaskDoesNotExistException;
    public TreeMap<LocalDate, ArrayList<TaskSchema>> findMany(LocalDate startDate, LocalDate endDate);
    public TreeMap<LocalDate, ArrayList<TaskSchema>> findAll();
}
