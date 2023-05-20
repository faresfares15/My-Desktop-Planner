package Databases;

import Exceptions.ProjectDoesNotExistException;
import Models.Project.ProjectSchema;
import Models.Task.TaskSchema;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;

public class ProjectFileDataBase implements ProjectDataBase{
    private TreeMap<Integer, ProjectSchema> projectsTreeSet = new TreeMap<>();

    public ProjectFileDataBase(TreeMap<Integer, ProjectSchema> projectsTreeSet) {
        this.projectsTreeSet = projectsTreeSet;
    }

    public ProjectFileDataBase() {
    }


    @Override
    public ProjectSchema create(ProjectSchema projectSchema) {
        return projectsTreeSet.put(projectSchema.getId(), projectSchema);


    }

    @Override
    public ProjectSchema initialize(String name, String description) {
        //initialzing the id manually so that we won't have inconsistent data
        Random random = new Random();
        int id = name.hashCode() + random.nextInt(1000);
        ProjectSchema projectSchema = new ProjectSchema(name, description);
        projectSchema.setId(id);
        return projectsTreeSet.put(id, projectSchema);
    }

    @Override
    public ProjectSchema create(String name, String description, ArrayList<TaskSchema> tasksList) {
        Random random = new Random();
        int id = name.hashCode() + random.nextInt(1000);
        ProjectSchema projectSchema = new ProjectSchema(name, description, tasksList);
        projectSchema.setId(id);
        projectsTreeSet.put(id, projectSchema);
        return projectSchema;
    }

    @Override
    public ProjectSchema find(int id) throws ProjectDoesNotExistException {
        ProjectSchema project = projectsTreeSet.get(id);
        if (project == null) throw new ProjectDoesNotExistException();
        return project;
    }
    @Override
    public ProjectSchema find(String name) throws ProjectDoesNotExistException{
        for (ProjectSchema projectSchema : projectsTreeSet.values()){
            if (projectSchema.getName().equals(name)) return projectSchema;
        }
        throw new ProjectDoesNotExistException();
    }
    @Override
    public ArrayList<ProjectSchema> findAll(){
        return new ArrayList<>(projectsTreeSet.values());
    }

    @Override
    public ProjectSchema update(ProjectSchema projectSchema) throws ProjectDoesNotExistException{
        //All of this is considered with the id not being changed from the beginning
        // So consider not changing the id at all
        if (projectsTreeSet.containsKey(projectSchema.getId()))
            return projectsTreeSet.replace(projectSchema.getId(), projectSchema);
        else
            throw new ProjectDoesNotExistException();
    }

    @Override
    public ProjectSchema delete(ProjectSchema projectSchema) throws ProjectDoesNotExistException{
        if (projectsTreeSet.containsKey(projectSchema.getId())) return projectsTreeSet.remove(projectSchema.getId());
        throw new ProjectDoesNotExistException();
    }
}
