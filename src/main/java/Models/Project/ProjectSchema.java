package Models.Project;

import Models.Task.TaskSchema;

import java.util.ArrayList;
import java.util.Random;

public class ProjectSchema {
    private String name;
    private String description;
    private int projectId;
    ArrayList<TaskSchema> tasksList;
    //TODO: update this list and the corresponding tasks in the dataBases everytime we do a modification on the tasks

    public ProjectSchema(String name, String description) {
        Random random = new Random();
        this.name = name;
        this.description = description;
        this.projectId = name.hashCode() + random.nextInt(1000);
        this.tasksList = new ArrayList<>();
    }

    public ProjectSchema(String name, String description, ArrayList<TaskSchema> tasksList) {
        Random random = new Random();
        this.name = name;
        this.description = description;
        this.tasksList = tasksList;
        this.projectId = name.hashCode() + random.nextInt(1000);
    }

    public void addTask(TaskSchema taskSchema){
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

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public ArrayList<TaskSchema> getTasksList() {
        return tasksList;
    }

    public void setTasksList(ArrayList<TaskSchema> tasksList) {
        this.tasksList = tasksList;
    }
}
