package Models.Category;

import Databases.CategoryDatabase;
import Databases.CategoryFileDataBase;
import Databases.ProjectFileDataBase;
import Exceptions.CategoryDoesNotExistException;
import esi.tp_poo_final.HelloApplication;
import javafx.scene.paint.Color;

import java.io.*;
import java.time.Duration;
import java.util.ArrayList;

public class CategoryModel {
    //will have the same architecture as the other models
    private CategoryDatabase categoryDatabase;
    public CategoryModel(CategoryDatabase categoryDatabase){
        this.categoryDatabase = categoryDatabase;
    }
    public void save() throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(HelloApplication.usersDirectoryName
                + "/" + HelloApplication.currentUserName + "/" + HelloApplication.categoryDbFileName));
        objectOutputStream.writeObject(categoryDatabase);
    }
    public void load() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(HelloApplication.usersDirectoryName
                + "/" + HelloApplication.currentUserName + "/" + HelloApplication.categoryDbFileName));
        categoryDatabase = (CategoryFileDataBase) objectInputStream.readObject();
    }
    public CategorySchema create(String name, Color color) throws Exception {
        //create the category
        return categoryDatabase.create(name, color);

    }
    public CategorySchema create(String name, Color color, Duration duration) throws Exception {
        //create the category
        return categoryDatabase.create(name, color, duration);
    }
    public CategorySchema find(String name) throws CategoryDoesNotExistException {
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
