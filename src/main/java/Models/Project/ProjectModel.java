package Models.Project;

import Databases.ProjectDataBase;
import Models.Task.TaskSchema;

import java.util.ArrayList;

public class ProjectModel {
    private ProjectDataBase projectDataBase;
    // The CRUD operations
    public ProjectSchema create(ProjectSchema projectSchema){//Create directly
        return projectDataBase.create(projectSchema);
    }
    public ProjectSchema initialize(String name, String description){//Just for initialization
        return projectDataBase.initialize(name, description);
    }
    public ProjectSchema create(String name, String description, ArrayList<TaskSchema> tasksList){//Create from input
        return projectDataBase.create(name, description, tasksList);
    }
    public ProjectSchema find(int id){
        return projectDataBase.find(id);
    }
    public ProjectSchema update(ProjectSchema projectSchema){//normally the id must not be changed
        return projectDataBase.update(projectSchema);
    }
    public ProjectSchema delete(ProjectSchema projectSchema){
        return projectDataBase.delete(projectSchema);
    }
}
