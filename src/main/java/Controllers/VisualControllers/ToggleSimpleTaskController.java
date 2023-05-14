package Controllers.VisualControllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ToggleSimpleTaskController implements EventHandler<ActionEvent> {
    private Label taskNameLabel;
    private TextField taskNameField;

    public ToggleSimpleTaskController(Label taskNameLabel, TextField taskNameField){
        this.taskNameLabel = taskNameLabel;
        this.taskNameField = taskNameField;
    }
    @Override
    public void handle(ActionEvent actionEvent) {
        this.taskNameLabel.setVisible(false);
        this.taskNameField.setVisible(false);
    }
}
