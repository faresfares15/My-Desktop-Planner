package Databases;

import Exceptions.DayDoesNotHaveTasksException;
import Models.Task.TaskSchema;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TreeMap;

public class TaskFileDatabase implements TaskDatabase{
    TreeMap<LocalDate, ArrayList<TaskSchema>> tasksMap;
    public ArrayList<TaskSchema> findMany(LocalDate date) throws DayDoesNotHaveTasksException {
        //get the list of tasks for the given date
        ArrayList<TaskSchema> tasksList = tasksMap.get(date);

        //check if the list is empty
        if(tasksList == null) throw new DayDoesNotHaveTasksException();

        //return the list
        return tasksList;
    }
}
