package Models.Project;

import Databases.*;
import Models.Task.TaskSchema;
import esi.tp_poo_final.HelloApplication;

import java.io.*;
import java.util.ArrayList;

public class ProjectModel {
    private ProjectDataBase projectDataBase;

    public ProjectModel() {
    }

    public ProjectModel(ProjectDataBase projectDataBase) {
        this.projectDataBase = projectDataBase;
    }

    public void save() throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(HelloApplication.usersDirectoryName
                + "/" + HelloApplication.currentUserName + "/" + HelloApplication.projectDbFileName));
        objectOutputStream.writeObject(projectDataBase);
    }
    public void load() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(HelloApplication.usersDirectoryName
                + "/" + HelloApplication.currentUserName + "/" + HelloApplication.projectDbFileName));
        projectDataBase = (ProjectDataBase) objectInputStream.readObject();
    }
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
