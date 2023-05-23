package Controllers.UserControllers;

import Models.User.UserSchema;
import esi.tp_poo_final.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class StatisticsController {
    @FXML
    Text mostProductiveDate;
    @FXML
    Text mostTasksCompletedInADay;
    @FXML
    Text maxCongratsReceivedInARow;
    @FXML
    Text totalCongrats;
    @FXML
    Text currentCongratsInARow;
    @FXML
    Text goodBadges;
    @FXML
    Text veryGoodBadges;
    @FXML
    Text excellentBadges;
    private UserSchema user;
    public StatisticsController(UserSchema user){
        this.user = user;
    }
    public void initData(){
        mostProductiveDate.setText(user.getMostProductiveDate().toString());
        mostTasksCompletedInADay.setText(String.valueOf(user.getMostTasksCompletedInADay()));
        maxCongratsReceivedInARow.setText(String.valueOf(user.getMostCongratsReceivedInARow()));
        totalCongrats.setText(String.valueOf(user.getTotalCongratsReceived()));
        currentCongratsInARow.setText(String.valueOf(user.getCongratsInARowCounter()));
        goodBadges.setText(String.valueOf(user.getNumberOfGoodBadges()));
        veryGoodBadges.setText(String.valueOf(user.getNumberOfVeryGoodBadges()));
        excellentBadges.setText(String.valueOf(user.getNumberOfExcellentBadges()));
    }
    public void backToCalendar() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("calendar-view.fxml"));
//            fxmlLoader.setControllerFactory(c -> new ...());
        Scene scene = new Scene(fxmlLoader.load(), 840, 500);
        Stage stage = (Stage) totalCongrats.getScene().getWindow();
        stage.setTitle("Calendar");

        //center the view on the user's screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - scene.getHeight()) / 2);

        stage.setScene(scene);
    }
}
