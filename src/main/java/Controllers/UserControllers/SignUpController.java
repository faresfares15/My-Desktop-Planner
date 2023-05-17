package Controllers.UserControllers;

import Databases.UniqueUsernameViolationException;
import Databases.UserDoesNotExistException;
import Models.User.UserModel;
import esi.tp_poo_final.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {
    //assign models and views
    private final UserModel userModel = HelloApplication.userModel;
    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passwordField;

    public void handle() throws IOException {
        boolean successful = true;
        if(successful){
            //go to login page (view)
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
            fxmlLoader.setControllerFactory(c -> new LoginController());
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = (Stage) userNameField.getScene().getWindow();
            stage.setTitle("Login");

            //center the view on the user's screen
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);
            stage.setY((screenBounds.getHeight() - scene.getHeight()) / 2);
            stage.setScene(scene);

            String username = userNameField.getText();
            String password = passwordField.getText();
            try {
                userModel.create(username, password);



            } catch (UniqueUsernameViolationException e) {
                System.out.println(e.getMessage());
            } finally {
                System.out.println("UsersDB content");
                try {
                    System.out.println(userModel.find(username));
                } catch (UserDoesNotExistException e) {
                    System.out.println("User does not exist");
                }
            }


        } else{
            System.out.println("Sign up failed");
        }
    }
}
