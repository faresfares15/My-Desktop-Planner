package Databases;

import Exceptions.CategoryDoesNotExistException;
import Exceptions.DayDoesNotHaveTasksException;
import Exceptions.TaskDoesNotExistException;
import Models.Category.CategorySchema;
import Models.Task.TaskSchema;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TreeMap;

public interface CategoryDatabase extends Serializable {
    public CategorySchema create(String name, Color color) throws Exception;
    public CategorySchema create(String name, Color color, Duration duration) throws Exception;
    public CategorySchema read(String name);
    public CategorySchema find(String name) throws CategoryDoesNotExistException;
    public ArrayList<CategorySchema> findAll();
    public ArrayList<String> getCategoriesNames();
    public void update(String oldName, CategorySchema updatedCategory) throws Exception;
    public void delete(String name) throws Exception;
}
