package Controllers.UserControllers;

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

public class LoginController {
    @FXML
    TextField userName;
    @FXML
    PasswordField password;
    public void handle() throws IOException {
        boolean successful = true;

        if (successful) {
            //go to calendar page (view)
//            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("calendar-view.fxml"));
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("plan-task-view.fxml"));
//            fxmlLoader.setControllerFactory(c -> new ...());
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = (Stage) userName.getScene().getWindow();
            stage.setTitle("Calendar");

            //center the view on the user's screen
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);
            stage.setY((screenBounds.getHeight() - scene.getHeight()) / 2);

            stage.setScene(scene);
            return;
        }
        System.out.println("Login failed");
    }
}
