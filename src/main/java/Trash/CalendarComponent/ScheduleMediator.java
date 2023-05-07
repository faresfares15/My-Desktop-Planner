package Trash.CalendarComponent;

import java.time.LocalTime;

public class ScheduleMediator {
    //This class will be the intermediate between the Calendar and the FreeSlot classes, and probably also the TaskDB class
    private LocalTime scheduleStartTime;
    private LocalTime scheduleEndTime;
    private LocalTime FloorTaskEndTime;
    private LocalTime CeilingTaskStartTime;

    public LocalTime getScheduleStartTime() {
        return scheduleStartTime;
    }

    public LocalTime getScheduleEndTime() {
        return scheduleEndTime;
    }

    public LocalTime getFloorTaskEndTime() {
        return FloorTaskEndTime;
    }

    public LocalTime getCeilingTaskStartTime() {
        return CeilingTaskStartTime;
    }

    public void setScheduleStartTime(LocalTime scheduleStartTime) {
        this.scheduleStartTime = scheduleStartTime;
    }

    public void setScheduleEndTime(LocalTime scheduleEndTime) {
        this.scheduleEndTime = scheduleEndTime;
    }

    public void setFloorTaskEndTime(LocalTime floorTaskEndTime) {
        FloorTaskEndTime = floorTaskEndTime;
    }

    public void setCeilingTaskStartTime(LocalTime ceilingTaskStartTime) {
        CeilingTaskStartTime = ceilingTaskStartTime;
    }
}
