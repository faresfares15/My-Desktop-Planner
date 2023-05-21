package Models.Task;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.TreeMap;

public class Category {
    private TreeMap<String, Color> categories = new TreeMap<>();
    public Color getCategoryColor(String name) throws Exception{
        //check if the category already exists, throw an error if it doesn't
        if(!categories.containsKey(name)) throw new Exception("Category doesn't exist");

        //return the color
        return categories.get(name);
    }
    public TreeMap<String, Color> getCategories() {
        //get all categories
        return categories;
    }
    public ArrayList<String> getCategoriesNames(){
        return new ArrayList<>(categories.keySet());
    }

    public void addCategory(String name, Color color) throws Exception{
        //if it the category already exists, throw an error
        if(categories.containsKey(name)) throw new Exception("Category already exists");

        //add the category
        categories.put(name, color);
    }
    public void find(String name) throws Exception{
        //check if the category already exists, throw an error if it doesn't
        if(!categories.containsKey(name)) throw new Exception("Category doesn't exist");

        return;
    }
    public void removeCategory(String name) throws Exception{
        //check if the category already exists, throw an error if it doesn't
        if(!categories.containsKey(name)) throw new Exception("Category doesn't exist");

        //remove the category
        categories.remove(name);
    }
    public void editCategoryColor(String name, Color color) throws Exception{
        //check if the category already exists, throw an error if it doesn't
        if(!categories.containsKey(name)) throw new Exception("Category doesn't exist");

        //update the color
        categories.replace(name, color);
    }
}
