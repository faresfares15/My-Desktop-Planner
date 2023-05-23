package Controllers.CategoryControllers;

import Exceptions.CategoryDoesNotExistException;
import Exceptions.ProjectDoesNotExistException;
import Utils.Popups;
import esi.tp_poo_final.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class CreateNewCategoryController {
    @FXML
    TextField categoryName;
    @FXML
    ColorPicker categoryColor;
    public void createCategory() {
        try {
            //get the category name and color
            ViewInfos viewInfos = new ViewInfos();
            String name = viewInfos.getCategoryName();
            Color color = viewInfos.getCategoryColor();

            //check if the category name already exists
            try {
                if (HelloApplication.categoryModel.find(name) != null) {
                    throw new Exception("Category already exists");
                }
            }catch (CategoryDoesNotExistException e){
                //ignore
            }catch (Exception e){
                Popups.showErrorMessage("Error", e.getMessage());
                return;
            }

            //create a new project
            HelloApplication.categoryModel.create(name, color);

            //show success message
            Popups.showSuccessMessage("Success", "Category created successfully");

            //close the window
            quit();
        } catch (Exception e) {
            //show an error message
            Popups.showErrorMessage("Error", e.getMessage());
            e.printStackTrace();
        }
    }
    public void cancel() {
        //close the window
        quit();
    }
    private class ViewInfos{
        private String getCategoryName() throws Exception {
            if(categoryName.getText().isEmpty()) {
                throw new Exception("Project name is empty");
            }
            return categoryName.getText();
        }
        private Color getCategoryColor(){
            return categoryColor.getValue();
        }
    }
    private void quit(){
        categoryName.getScene().getWindow().hide();
    }
}
