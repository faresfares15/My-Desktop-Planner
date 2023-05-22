package Models.Category;

import Databases.CategoryDatabase;
import javafx.scene.paint.Color;

import java.time.Duration;
import java.util.ArrayList;

public class CategoryModel {
    //will have the same architecture as the other models
    private CategoryDatabase categoryDatabase;
    public CategoryModel(CategoryDatabase categoryDatabase){
        this.categoryDatabase = categoryDatabase;
    }
    public CategorySchema create(String name, Color color) throws Exception {
        //create the category
        return categoryDatabase.create(name, color);

    }
    public CategorySchema create(String name, Color color, Duration duration) throws Exception {
        //create the category
        return categoryDatabase.create(name, color, duration);
    }
    public CategorySchema find(String name) throws Exception {
        //find the category
        return categoryDatabase.find(name);
    }
    public ArrayList<CategorySchema> findAll() {
        //find all categories
        return categoryDatabase.findAll();
    }
    public ArrayList<String> getCategoriesNames(){
        //get all categories names
        return categoryDatabase.getCategoriesNames();
    }
    public void update(String oldName, CategorySchema updatedCategory) throws Exception {
        //update the category
        categoryDatabase.update(oldName, updatedCategory);
    }
    public void delete(String name) throws Exception {
        //delete the category
        categoryDatabase.delete(name);
    }
}
