package Controllers.CategoryControllers;

import Models.Category.CategorySchema;
import esi.tp_poo_final.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ViewCategoryController {
    @FXML
    VBox categoriesContainer;
    public void initData(){
        //clear the content of the container
        categoriesContainer.getChildren().clear();
        categoriesContainer.setPadding(new Insets(10, 10, 10, 10));
        categoriesContainer.setSpacing(10);

        //get the categories from the database
        ArrayList<CategorySchema> categories = HelloApplication.categoryModel.findAll();

        if(categories.size() == 0) {
            Text noProjectsText = new Text("No categories found");
            categoriesContainer.setAlignment(Pos.TOP_CENTER);
            categoriesContainer.getChildren().add(noProjectsText);
            return;
        }

        //iterate over the categories and add them to the scroll container
        for(CategorySchema category : categories) {
//            if(Objects.equals(category.getName(), "")) continue; //skip the empty category

            //create the texts objects
            Text categoryName = new Text("Name: "+category.getName());
            Text categoryColor = new Text("Color (hexadecimal): "+category.getColor());
            Text categoryTotalDuration = new Text("Total Duration: "+category.getTotalDuration().toHours() + ":"+ category.getTotalDuration().toMinutes() % 60);

            //create the container for the texts
            VBox categoryContainer = new VBox(categoryName, categoryColor, categoryTotalDuration);

            //add a bottom border for the container
            Border border = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1)));
            categoryContainer.setBorder(border);
            categoryContainer.setPadding(new Insets(5, 5, 5, 5));

            //add the container to the scroll container
            categoriesContainer.getChildren().add(categoryContainer);
        }
    }

    public void createNewCategory() throws IOException {
        //show a new window to create a new category
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("create-new-category-view.fxml"));
        Scene createNewCategoryScene = new Scene(fxmlLoader.load());
        Stage createNewCategoryStage = new Stage();
        createNewCategoryStage.setTitle("Create new category");
        createNewCategoryStage.initModality(Modality.APPLICATION_MODAL);
        createNewCategoryStage.initOwner(categoriesContainer.getScene().getWindow());
        createNewCategoryStage.setScene(createNewCategoryScene);
        createNewCategoryStage.showAndWait();

        initData();
    }
    public void goToCalendarPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("calendar-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 840, 500);
        Stage stage = (Stage) categoriesContainer.getScene().getWindow();
        stage.setTitle("Calendar");

        //center the view on the user's screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - scene.getHeight()) / 2);

        stage.setScene(scene);
    }
}
