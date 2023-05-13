package Databases;

import Models.Project.ProjectSchema;
import Models.Task.TaskSchema;

import java.util.ArrayList;

public interface ProjectDataBase {
    // The CRUD operations
    ProjectSchema create(ProjectSchema projectSchema); //Create directly
    ProjectSchema initialize(String name, String description); //Just for initialization
    ProjectSchema create(String name, String description, ArrayList<TaskSchema> tasksList); //Create from input
    ProjectSchema find(int id);
    ProjectSchema update(ProjectSchema projectSchema);
}
