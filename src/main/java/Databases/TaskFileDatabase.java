package Databases;

import Exceptions.DayDoesNotHaveTasksException;
import Exceptions.TaskDoesNotExistException;
import Models.Task.TaskSchema;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

public class TaskFileDatabase implements TaskDatabase{
    TreeMap<LocalDate, ArrayList<TaskSchema>> tasksMap = new TreeMap<>();

    public TaskFileDatabase() {
    }

    public TaskFileDatabase(TreeMap<LocalDate, ArrayList<TaskSchema>> tasksMap) {
        this.tasksMap = tasksMap;
    }

    public ArrayList<TaskSchema> findMany(LocalDate date) throws DayDoesNotHaveTasksException {
        //get the list of tasks for the given date
        ArrayList<TaskSchema> tasksList = tasksMap.get(date);

        //check if the list is empty
        if(tasksList == null) throw new DayDoesNotHaveTasksException();

        //return the list
        return tasksList;
    }

    @Override
    public TaskSchema create(TaskSchema taskSchema) {
        if (tasksMap.containsKey(taskSchema.getDate())){
            tasksMap.get(taskSchema.getDate()).add(taskSchema);
            Collections.sort(tasksMap.get(taskSchema.getDate()));
        } else {
            ArrayList<TaskSchema> tasksList = new ArrayList<>();
            tasksList.add(taskSchema);
            tasksMap.put(taskSchema.getDate(), tasksList);
        }

        return taskSchema;
    }

    @Override
    public TaskSchema read(int id, LocalDate date) throws TaskDoesNotExistException {
        if (tasksMap.containsKey(date)){
            for (TaskSchema taskSchema:
                    tasksMap.get(date)) {
                if (taskSchema.getId() == id) return taskSchema;
            }
        } else {
            throw new TaskDoesNotExistException();
        }
        return null;
    }
    @Override
    public TaskSchema find(LocalDate date, int id) throws TaskDoesNotExistException {
        if (tasksMap.containsKey(date)){
            for (TaskSchema taskSchema:
                    tasksMap.get(date)) {
                if (taskSchema.getId() == id) return taskSchema;
            }
        }

        throw new TaskDoesNotExistException();
    }

    @Override
    public void initialize(LocalDate date) {
        if (!tasksMap.containsKey(date))tasksMap.put(date, new ArrayList<>());
        //just an empty list to init the day when creating it
    }

    @Override
    public void update(TaskSchema taskSchema) throws TaskDoesNotExistException {
        if(tasksMap.containsKey(taskSchema.getDate())){
            tasksMap.get(taskSchema.getDate()).remove(taskSchema);
            tasksMap.get(taskSchema.getDate()).add(taskSchema);
            Collections.sort(tasksMap.get(taskSchema.getDate()));
        } else {
            throw new TaskDoesNotExistException();
        }

    }

    @Override
    public void delete(TaskSchema taskSchema) throws TaskDoesNotExistException {
        if(tasksMap.containsKey(taskSchema.getDate())){
            tasksMap.get(taskSchema.getDate()).remove(taskSchema);
            Collections.sort(tasksMap.get(taskSchema.getDate()));
        } else {
            throw new TaskDoesNotExistException();
        }
    }

    @Override
    public TreeMap<LocalDate, ArrayList<TaskSchema>> findMany(LocalDate startDate, LocalDate endDate) {
        TreeMap<LocalDate, ArrayList<TaskSchema>> resultMap = new TreeMap<>();
        if(startDate.until(endDate).getDays() < 0) return null;

//        int daysCounter = startDate.until(endDate).getDays();
//        for (int i = 0; i <= daysCounter; i++) {
//            if(tasksMap.containsKey(startDate)){
//                resultMap.put(startDate, tasksMap.get(startDate));
//            }
//
//            startDate = startDate.plusDays(1); //go to next day
//        }

        for(LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)){
            if(tasksMap.containsKey(date)){
                resultMap.put(date, tasksMap.get(date));
            }
        }

        return resultMap;
    }
}
