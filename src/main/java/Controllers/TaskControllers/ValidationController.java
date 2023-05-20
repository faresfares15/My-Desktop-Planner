package Controllers.TaskControllers;

import Exceptions.DayDoesNotHaveFreeSlotsException;
import Exceptions.FreeSlotNotFoundException;
import Models.FreeSlot.FreeSlotModel;
import Models.FreeSlot.FreeSlotSchema;
import Models.Task.TaskModel;
import Models.Task.TaskSchema;
import esi.tp_poo_final.HelloApplication;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.TreeMap;

public class ValidationController implements EventHandler<ActionEvent> {

    @FXML
    Button validateButton;
    @FXML
    Button declineButton;
    TreeMap<TaskSchema, ArrayList<FreeSlotSchema>> preValidationMap;
    TaskModel taskModel = HelloApplication.taskModel;
    FreeSlotModel freeSlotModel = HelloApplication.freeSlotModel;

    public ValidationController(TreeMap<TaskSchema, ArrayList<FreeSlotSchema>> preValidationMap) {
        this.preValidationMap = preValidationMap;
    }

    @Override
    public void handle(ActionEvent event) {
        isValidated();
    }

    public void isValidated() {
        boolean planningValidated;
        validateButton.setOnAction(event -> {
            validatePlanification();
        });
        validateButton.getScene().getWindow().hide();
        
        declineButton.setOnAction(event -> {
            declinePlanification();
        });
        declineButton.getScene().getWindow().hide();

    }
    public void validatePlanification(){
        for (TaskSchema task : preValidationMap.keySet()) {
            taskModel.create(task);
        }
    }
    public void declinePlanification(){

        boolean dayHaveFreeSlots;
        boolean freeSlotIsUpdated = false;
        ArrayList<FreeSlotSchema> freeSlotsOfTheDay = new ArrayList<>();
        //recreate the freeSlots
        for (TaskSchema task : preValidationMap.keySet()) {
            //recreating the freeSLots
            for (FreeSlotSchema freeSlot : preValidationMap.get(task)) {
                try {
                    dayHaveFreeSlots = true;
                    freeSlotsOfTheDay = freeSlotModel.findMany(freeSlot.getDate());
                } catch (DayDoesNotHaveFreeSlotsException e) {
                    dayHaveFreeSlots = false;
                }
                if (dayHaveFreeSlots) {
                    //check if there's another freeSlot that starts at the end time of this one
                    for (FreeSlotSchema freeSlotOfThisDay : freeSlotsOfTheDay) {
                        if (freeSlotOfThisDay.getStartTime().equals(freeSlot.getEndTime())) {
                            //Merge them into one freeSlot by updating the existing one
                            try {
                                freeSlotIsUpdated = true;
                                freeSlotModel.update(freeSlotOfThisDay.getDate(), freeSlotOfThisDay.getStartTime(), freeSlot.getStartTime());
                                //Normally we shouldn't have problems since we are calling this controller just after planning the tasks
                            } catch (FreeSlotNotFoundException e) {
                                System.out.println("a problem has occurred when merging the two slots");
                                freeSlotIsUpdated = false;
                            }
                            break; //we've finished with this task
                        }
                    }
                    if (!freeSlotIsUpdated)
                        freeSlotModel.create(freeSlot); //Because there wasn't a merge of two freeSLots, so we'll create it in this day

                } else {
                    //put it back directly since it's the only one
                    freeSlotModel.create(freeSlot);
                }
            }
        }
    }

}
