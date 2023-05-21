package Databases;

import Exceptions.DayDoesNotHaveTasksException;
import Exceptions.TaskDoesNotExistException;
import Models.Category.CategorySchema;
import Models.Task.TaskSchema;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TreeMap;

public interface CategoryDatabase {
    public ArrayList<CategorySchema> findMany();
    public CategorySchema create(TaskSchema taskSchema);
    public CategorySchema read(String name);
    public CategorySchema find(String name);
    public void update(String oldName, CategorySchema updatedCategory);
    public void delete(String name) throws TaskDoesNotExistException;
}
