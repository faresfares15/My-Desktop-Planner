package Models.Project;

import Models.Task.TaskSchema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class ProjectSchema implements Serializable {
    private String name;
    private String description;
    private int id;
    ArrayList<TaskSchema> tasksList = new ArrayList<>();
    //TODO: update this list and the corresponding tasks in the dataBases everytime we do a modification on the tasks

    public ProjectSchema(String name, String description) {
        Random random = new Random();
        this.name = name;
        this.description = description;
        this.id = name.hashCode() + random.nextInt(1000);
        this.tasksList = new ArrayList<>();
    }

    public ProjectSchema(String name, String description, ArrayList<TaskSchema> tasksList) {
        Random random = new Random();
        this.name = name;
        this.description = description;
        this.tasksList = tasksList;
        this.id = name.hashCode() + random.nextInt(1000);
    }

    public void addTask(TaskSchema taskSchema){
        //to ensure that this task corresponds to this project
        taskSchema.setProjectId(this.getId());
        tasksList.add(taskSchema);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public double getProgressPercentage(){
        double total = 0;
        for (TaskSchema task: tasksList) {
            total += task.getProgressPercentage();
        }
        return tasksList.size() == 0 ? 0 :  ( total/tasksList.size() ) * 100;
    }
    public int getTotalTasks(){
        return tasksList.size();
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof ProjectSchema)) return false;
        return this.id == ((ProjectSchema) obj).getId();
    }
}
