package Databases;

import Exceptions.ProjectDoesNotExistException;
import Models.Project.ProjectSchema;
import Models.Task.TaskSchema;

import java.io.Serializable;
import java.util.ArrayList;

public interface ProjectDataBase extends Serializable {
    // The CRUD operations
    ProjectSchema create(ProjectSchema projectSchema); //Create directly
    ProjectSchema initialize(String name, String description); //Just for initialization
    ProjectSchema create(String name, String description, ArrayList<TaskSchema> tasksList); //Create from input
    ProjectSchema find(int id) throws ProjectDoesNotExistException;
    ProjectSchema find(String name) throws ProjectDoesNotExistException;
    ArrayList<ProjectSchema> findAll();
    ProjectSchema update(ProjectSchema projectSchema) throws ProjectDoesNotExistException; //normally the id must not be changed
    ProjectSchema delete(ProjectSchema projectSchema) throws ProjectDoesNotExistException;
}
