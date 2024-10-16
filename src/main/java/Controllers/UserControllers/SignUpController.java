package Controllers.UserControllers;

import Databases.ProjectFileDataBase;
import Databases.UniqueUsernameViolationException;
import Databases.UserDoesNotExistException;
import Exceptions.PasswordNotProvidedException;
import Exceptions.UserNameNotProvidedException;
import Models.Day.DayModel;
import Models.FreeSlot.FreeSlotModel;
import Models.Project.ProjectModel;
import Models.Task.TaskModel;
import Models.User.UserModel;
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
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;

public class SignUpController {
    //assign models and views
    private final UserModel userModel = HelloApplication.userModel;
    private final TaskModel taskModel = HelloApplication.taskModel;
    private final ProjectModel projectModel = HelloApplication.projectsModel;
    private final FreeSlotModel freeSlotModel = HelloApplication.freeSlotModel;
    private final DayModel dayModel = HelloApplication.dayModel;
    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button signUpButton;

    public void handle() throws IOException {

        String username = userNameField.getText();
        String password = passwordField.getText();
        try  {

            if (username == null || username.length() == 0) throw new UserNameNotProvidedException();
            if (password == null || password.length() == 0) throw new PasswordNotProvidedException();

            if(userModel.exists(username)) throw new UniqueUsernameViolationException();

            userModel.create(username, password);

            //Create a directory with the username as the name
            if(new File(HelloApplication.usersDirectoryName + "/" + username).mkdirs()){
                System.out.println("Directory created");
                //Create a file for each FileDataBase class
                HelloApplication.currentUserName = username;
                //TODO: create a view for the users who sign up for the first time to get their settings
                System.out.println(new File(HelloApplication.usersDirectoryName + "/" + username + "/" + HelloApplication.taskDbFileName).createNewFile());
                new File(HelloApplication.usersDirectoryName + "/" + username + "/" + HelloApplication.freeSlotDbFileName).createNewFile();
                new File(HelloApplication.usersDirectoryName + "/" + username + "/" + HelloApplication.dayDbFileName).createNewFile();
                new File(HelloApplication.usersDirectoryName + "/" + username + "/" + HelloApplication.projectDbFileName).createNewFile();

                //Show the calendar view

                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("calendar-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 840, 500);
                Stage stage = (Stage) userNameField.getScene().getWindow();
                stage.setTitle("Calendar");

                //center the view on the user's screen
                Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                stage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);
                stage.setY((screenBounds.getHeight() - scene.getHeight()) / 2);
                stage.setScene(scene);
            } else {
                throw new IOException("A problem occurred when creating the directory");
            }

        } catch (UniqueUsernameViolationException | UserNameNotProvidedException | PasswordNotProvidedException | IOException e) {
            System.out.println(e.getMessage());
            Popups.showErrorMessage(e.getMessage());
        }
        finally {

            System.out.println("\nUsersDB content");


            //create days from today to 30 days from now, and 10 days before
            for (int i = -10; i < 40; i++) {
                dayModel.create(LocalDate.now().plusDays(i));
            }

            try {
                System.out.println(userModel.find(username));
            } catch (UserDoesNotExistException e) {
                Popups.showErrorMessage("User does not exist");
            }
        }


    }
    public void moveToLoginView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = (Stage) userNameField.getScene().getWindow();
        stage.setTitle("Login");

        //center the view on the user's screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - scene.getHeight()) / 2);
        stage.setScene(scene);
    }
}
