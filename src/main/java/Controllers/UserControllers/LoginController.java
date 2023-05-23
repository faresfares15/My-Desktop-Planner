package Controllers.UserControllers;

import Databases.UserDoesNotExistException;
import Exceptions.PasswordNotProvidedException;
import Exceptions.UserNameNotProvidedException;
import Exceptions.WrongPasswordException;
import Models.User.UserModel;
import Models.User.UserSchema;
import Utils.Popups;
import esi.tp_poo_final.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;

public class LoginController {
    private final UserModel userModel = HelloApplication.userModel;
    @FXML
    TextField userName;
    @FXML
    PasswordField password;
    @FXML
    Button loginButton;

    public void handle() throws IOException {

        String username = this.userName.getText();
        String password = this.password.getText();
        try  {

            if (username == null || username.length() == 0) throw new UserNameNotProvidedException();
            if (password == null || password.length() == 0) throw new PasswordNotProvidedException();

            UserSchema user = userModel.find(username); //If he doesn't exist an exception will be thrown
            if (!user.getPassword().equals(password)) throw new WrongPasswordException();
            HelloApplication.currentUserName = username;
            HelloApplication.currentUserSettings = user.getSettings();

            //Load the DBs from the corresponding files
            HelloApplication.taskModel.load();
            HelloApplication.dayModel.load();

//            //create days from today to 30 days from now, and 10 days before
//            for (int i = -10; i < 40; i++) {
//                HelloApplication.dayModel.create(LocalDate.now().plusDays(i));
//            }

            HelloApplication.freeSlotModel.load();
            HelloApplication.projectsModel.load();
            HelloApplication.categoryModel.load();
            System.out.println("Logged in successfully");

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("calendar-view.fxml"));
//            fxmlLoader.setControllerFactory(c -> new LoginController());
            Scene scene = new Scene(fxmlLoader.load(), 840, 500);
            Stage stage = (Stage) userName.getScene().getWindow();
            stage.setTitle("Calendar");

            //center the view on the user's screen
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);
            stage.setY((screenBounds.getHeight() - scene.getHeight()) / 2);
            stage.setScene(scene);

        } catch (PasswordNotProvidedException | UserNameNotProvidedException | UserDoesNotExistException | WrongPasswordException e) {
            System.out.println(e.getMessage());
            Popups.showErrorMessage(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            Popups.showErrorMessage("An error has occurred while loading the files");
        }
    }
    private void showErrorMessage(String message) {
        Alert errorMessage = new Alert(Alert.AlertType.ERROR);
        errorMessage.setContentText(message);
        errorMessage.setHeaderText("Error");
        errorMessage.setTitle("Error");
        errorMessage.showAndWait();
    }
    public void moveToSignupView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signup-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = (Stage) userName.getScene().getWindow();
        stage.setTitle("Login");

        //center the view on the user's screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - scene.getHeight()) / 2);
        stage.setScene(scene);
    }
}
