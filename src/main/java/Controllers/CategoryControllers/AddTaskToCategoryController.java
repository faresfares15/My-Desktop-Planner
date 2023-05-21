package Controllers.CategoryControllers;

import Models.Project.ProjectSchema;
import Models.Task.TaskSchema;
import esi.tp_poo_final.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Objects;

public class AddTaskToCategoryController {
    @FXML
    Text taskName;
    @FXML
    Text currentCategory;
    @FXML
    ComboBox<String> categoriesList;
    @FXML
    TextField newCategoryName;
    @FXML
    ColorPicker newCategoryColor;
    String currentCategoryName = "";
    private TaskSchema task;
    public AddTaskToCategoryController(TaskSchema task){
        this.task = task;
        if(!Objects.equals(task.getCategory(), "")){
            this.currentCategoryName = task.getCategory();
        }
    }
    @FXML
    public void initialize(){
        //initialize the categories list combobox

        //set the current category text name
        if(Objects.equals(currentCategoryName, "")){
            currentCategory.setText("No category");
        }else{
            currentCategory.setText(currentCategoryName);
        }

        //setting the category combo box
        ObservableList<String> categories = FXCollections.observableArrayList(HelloApplication.categoryModel.getCategoriesNames());
        categories.add("New category");
        categoriesList.setValue(currentCategoryName);
        categoriesList.getItems().clear();
        categoriesList.getItems().setAll(categories);

        categoriesList.setOnAction(event -> {
            // If the user has selected "New category," then make the category text field visible.
            if (categoriesList.getValue().equals("New category")) {
                newCategoryName.setDisable(false);
                newCategoryColor.setDisable(false);
            } else {
                newCategoryName.setDisable(true);
                newCategoryColor.setDisable(true);
            }
        });
    }
    public void initData(){
        taskName.setText(task.getName());
        currentCategoryName = task.getCategory();
    }

    public void addTaskToCategory(){

        try{
            ViewInfos viewInfos = new ViewInfos();
            String categoryName = viewInfos.getCategoryName();

            if(categoriesList.getValue().equals("New category")){
                //get the new category color
                Color color = viewInfos.getCategoryColor();
                ArrayList<TaskSchema> tasks = new ArrayList<>();
                tasks.add(task);


                //create a new category
                HelloApplication.categoryModel.addCategory(categoryName, color);

                //update the category name in the task
                task.setCategory(categoryName);

                setCurrentCategoryName(categoryName);
            }
            else{
                //find the project in the database
                try{
                    HelloApplication.categoryModel.find(categoryName);
                }catch (Exception e){
                    //ignore
                }

                //add the task to the category
                task.setCategory(categoryName);

                //set the current category text
                setCurrentCategoryName(categoryName);
            }

            //show success message
            showSuccessMessage("Category added successfully", "Task added to category successfully");

            //close the window
            quit();

        }catch (Exception e){
            e.printStackTrace();
            showErrorMessage("Error", e.getMessage());
            setCurrentCategoryName("No Category");
        }
    }
    public void cancel(){
        quit();
    }
    private void quit(){
        taskName.getScene().getWindow().hide();
    }
    public String getCurrentCategoryName() {
        return currentCategoryName;
    }

    public void setCurrentCategoryName(String currentCategoryName) {
        this.currentCategoryName = currentCategoryName;
    }

    private class ViewInfos{
        private String getCategoryName() throws Exception {
            String categoryName = categoriesList.getValue();
            if (categoryName == null) throw new Exception("No category selected");
            if(categoryName.equals("New category")){
                categoryName = newCategoryName.getText();
                if(categoryName.isBlank()) throw new Exception("No name provided for the new category");

                //check if the category already exists (just from the list of categories)
                for(String project : categoriesList.getItems()){
                    if(project.equals(categoryName)) throw new Exception("Category already exists");
                }
            }

            return categoryName;
        }
        private Color getCategoryColor(){
            return newCategoryColor.getValue();
        }

    }
    private void showSuccessMessage(String title, String message) {
        Alert successMessage = new Alert(Alert.AlertType.INFORMATION);
        successMessage.setContentText(message);
        successMessage.setHeaderText(title);
        successMessage.setTitle(title);
        successMessage.showAndWait();
    }
    private void showErrorMessage(String title, String message){
        Alert errorMessage = new Alert(Alert.AlertType.ERROR);
        errorMessage.setContentText(message);
        errorMessage.setHeaderText(title);
        errorMessage.setTitle(title);
        errorMessage.showAndWait();
    }
}
