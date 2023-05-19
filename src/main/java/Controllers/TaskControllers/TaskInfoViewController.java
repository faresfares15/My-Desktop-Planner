package Controllers.TaskControllers;

import Models.Task.TaskSchema;
import esi.tp_poo_final.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class TaskInfoViewController {
    @FXML
    Label taskName;
    @FXML
    Text startTime;
    @FXML
    Text duration;
    @FXML
    Text priority;
    @FXML
    Text deadline;
    @FXML
    Text category;
    @FXML
    Text status;
    @FXML
    Text progress;
    @FXML
    public void initialize(){
    }
    TaskSchema task;
    public TaskInfoViewController(TaskSchema task){
        this.task = task;
    }
    public void initData(){
        taskName.setText(task.getName());
        startTime.setText(task.getStartTime().toString());
        duration.setText(task.getDuration().toString());
        priority.setText(task.getPriority().toString());
        deadline.setText(task.getDeadline().toString());
        category.setText(task.getCategory());
//        status.setText(task.getStatus().toString());
//        progress.setText(task.getProgress().toString());
    }
    public void goToCalendar(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("calendar-view.fxml"));
//            fxmlLoader.setControllerFactory(c -> new ...());
            Scene scene = new Scene(fxmlLoader.load(), 800, 400);
            Stage stage = (Stage) taskName.getScene().getWindow();
            stage.setTitle("Calendar");

            //center the view on the user's screen
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);
            stage.setY((screenBounds.getHeight() - scene.getHeight()) / 2);

            stage.setScene(scene);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
