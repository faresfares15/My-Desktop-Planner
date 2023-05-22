package Databases;

import Exceptions.DayDoesNotHaveTasksException;
import Exceptions.TaskDoesNotExistException;
import Models.Task.DecomposableTaskSchema;
import Models.Task.TaskSchema;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
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
    public TaskSchema find(int id) throws TaskDoesNotExistException{
        for (LocalDate date: tasksMap.keySet()) {
            for (TaskSchema taskSchema: tasksMap.get(date)) {
                if (taskSchema.getId() == id) return taskSchema;
            }
        }
        throw new TaskDoesNotExistException();
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
    public ArrayList<TaskSchema> findAll(String taskType){
        ArrayList<TaskSchema> tasksList = new ArrayList<>();

        if(Objects.equals(taskType, "DecomposableTaskSchema")) {
            for (LocalDate date: tasksMap.keySet()) {
                for (TaskSchema taskSchema: tasksMap.get(date)) {
                    if (taskSchema instanceof DecomposableTaskSchema) tasksList.add(taskSchema);
                }
            }
        }

        return tasksList;
    }

    @Override
    public void initialize(LocalDate date) {
        if (!tasksMap.containsKey(date))tasksMap.put(date, new ArrayList<>());
        //just an empty list to init the day when creating it
    }

    @Override
    public void update(LocalDate date, int id, String newName) throws TaskDoesNotExistException {
        if(tasksMap.containsKey(date)){
            TaskSchema task = this.find(date, id);
            task.setName(newName);
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

        for(LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)){
            if(tasksMap.containsKey(date)){
                resultMap.put(date, tasksMap.get(date));
            }
        }

        return resultMap;
    }

    @Override
    public TreeMap<LocalDate, ArrayList<TaskSchema>> findAll() {
        return tasksMap;
    }
}
