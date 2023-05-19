package Databases;

import Models.Project.ProjectSchema;
import Models.Task.TaskSchema;

import java.io.Serializable;
import java.util.ArrayList;

public interface ProjectDataBase extends Serializable {
    // The CRUD operations
    ProjectSchema create(ProjectSchema projectSchema); //Create directly
    ProjectSchema initialize(String name, String description); //Just for initialization
    ProjectSchema create(String name, String description, ArrayList<TaskSchema> tasksList); //Create from input
    ProjectSchema find(int id);
    ProjectSchema update(ProjectSchema projectSchema); //normally the id must not be changed
    ProjectSchema delete(ProjectSchema projectSchema);
}
