package Databases;

import Exceptions.DayDoesNotHaveTasksException;
import Models.Task.TaskSchema;

import java.time.LocalDate;
import java.util.ArrayList;

public interface TaskDatabase {
    public ArrayList<TaskSchema> findMany(LocalDate date) throws DayDoesNotHaveTasksException;
}
