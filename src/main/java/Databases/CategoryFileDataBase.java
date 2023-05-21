package Databases;

import Exceptions.TaskDoesNotExistException;
import Models.Category.CategorySchema;
import Models.Task.TaskSchema;

import java.util.ArrayList;

public class CategoryFileDataBase implements CategoryDatabase{
    @Override
    public ArrayList<CategorySchema> findMany() {
        return null;
    }

    @Override
    public CategorySchema create(TaskSchema taskSchema) {
        return null;
    }

    @Override
    public CategorySchema read(String name) {
        return null;
    }

    @Override
    public CategorySchema find(String name) {
        return null;
    }

    @Override
    public void update(String oldName, CategorySchema updatedCategory) {

    }

    @Override
    public void delete(String name) throws TaskDoesNotExistException {

    }
}
