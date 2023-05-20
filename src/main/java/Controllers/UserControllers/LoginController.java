package Controllers.UserControllers;

import Databases.UniqueUsernameViolationException;
import Databases.UserDoesNotExistException;
import Exceptions.PasswordNotProvidedException;
import Exceptions.UserNameNotProvidedException;
import Exceptions.WrongPasswordException;
import Models.User.UserModel;
import Models.User.UserSchema;
import esi.tp_poo_final.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.*;

public class LoginController {
    private final UserModel userModel = HelloApplication.userModel;
    @FXML
    TextField userName;
    @FXML
    PasswordField password;
    @FXML
    Button loginButton;

    public void handle() throws IOException {
        boolean successful = false;

        //go to calendar page (view)
//            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("calendar-view.fxml"));
//            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("plan-task-view.fxml"));
////            fxmlLoader.setControllerFactory(c -> new ...());
//            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
//            Stage stage = (Stage) userName.getScene().getWindow();
//            stage.setTitle("Calendar");
//
//            //center the view on the user's screen
//            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
//            stage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);
//            stage.setY((screenBounds.getHeight() - scene.getHeight()) / 2);
//            stage.setScene(scene);

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
            HelloApplication.freeSlotModel.load();
            HelloApplication.projectsModel.load();
            System.out.println("Logged in successfully");

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("calendar-view.fxml"));
//            fxmlLoader.setControllerFactory(c -> new LoginController());
            Scene scene = new Scene(fxmlLoader.load(), 840, 400);
            Stage stage = (Stage) userName.getScene().getWindow();
            stage.setTitle("Calendar");

            //center the view on the user's screen
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);
            stage.setY((screenBounds.getHeight() - scene.getHeight()) / 2);
            stage.setScene(scene);

        } catch (PasswordNotProvidedException | UserNameNotProvidedException | UserDoesNotExistException | WrongPasswordException e) {
            System.out.println(e.getMessage());
            //TODO: show Popup here
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            //TODO:check this too
        }
    }
}
