package Databases;

import Exceptions.TaskDoesNotExistException;
import Models.Category.CategorySchema;
import Models.Task.TaskSchema;
import javafx.scene.paint.Color;

import java.time.Duration;
import java.util.ArrayList;
import java.util.TreeMap;

public class CategoryFileDataBase implements CategoryDatabase{
    private TreeMap<String, CategorySchema> categories = new TreeMap<>();
    @Override
    public CategorySchema create(String name, Color color) throws Exception {

        //if the category already exists, throw an error
        if(categories.containsKey(name)) throw new Exception("Category already exists");

        //create the new category instance
        CategorySchema newCategory = new CategorySchema(name, String.valueOf(color));

        //add the category
        categories.put(name, newCategory);

        return newCategory;
    }
    @Override
    public CategorySchema create(String name, Color color, Duration duration) throws Exception {

        //if the category already exists, throw an error
        if(categories.containsKey(name)) throw new Exception("Category already exists");

        //create the new category instance
        CategorySchema newCategory = new CategorySchema(name, String.valueOf(color), duration);

        //add the category
        categories.put(name, newCategory);

        return newCategory;
    }

    @Override
    public CategorySchema read(String name) {
        return null;
    }

    @Override
    public CategorySchema find(String name) throws Exception {
        //check if the category already exists, throw an error if it doesn't
        if(!categories.containsKey(name)) throw new Exception("Category doesn't exist");

        //return the color
        return categories.get(name);
    }
    @Override
    public ArrayList<CategorySchema> findAll() {
        return new ArrayList<>(categories.values());
    }
    @Override
    public ArrayList<String> getCategoriesNames(){
        return new ArrayList<>(categories.keySet());
    }

    @Override
    public void update(String oldName, CategorySchema updatedCategory) throws Exception {
        //check if the category already exists, throw an error if it doesn't
        if(!categories.containsKey(oldName)) throw new Exception("Category doesn't exist");

        //update the category
        categories.replace(oldName, updatedCategory);
    }

    @Override
    public void delete(String name) throws Exception {
        //check if the category already exists, throw an error if it doesn't
        if(!categories.containsKey(name)) throw new Exception("Category doesn't exist");

        //remove the category
        categories.remove(name);
    }
}
