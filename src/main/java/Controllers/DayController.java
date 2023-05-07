package Controllers;

import Models.FreeSlot;
import Models.TaskModel.Day;
import Trash.CalendarComponent.ScheduleMediator;
import Trash.TaskComponent.MinimalDurationException;

import java.time.Duration;

public class DayController {
    private Day day;

    public boolean isFreeSlotAvailable(ScheduleMediator scheduleMediator, Duration minimalDuration)
            throws MinimalDurationException {
        for(FreeSlot freeSlot : this.freeSlots){
            if(freeSlot.containsSimpleTask(scheduleMediator, minimalDuration))
                return true;
        }
        System.out.println("No free slot available for this task");
        return false;
    }
    public boolean isFreeSlotAvailableComposed(ScheduleMediator scheduleMediator, Duration minimalDuration){
        for(FreeSlot freeSlot: this.freeSlots){
            if(freeSlot.containsComposedTask(this.date, scheduleMediator, minimalDuration))
                return true;
        }
        System.out.println("No free slot available for this task");
        return false;
    }
}
