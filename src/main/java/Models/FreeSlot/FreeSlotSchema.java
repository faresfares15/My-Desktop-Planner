package Models.FreeSlot;

import java.time.LocalDate;
import java.time.LocalTime;

public class FreeSlotSchema {
    private LocalDate dayDate; //useful to reference the day this instance belongs to
    private LocalTime startTime;
    private LocalTime endTime;
    public FreeSlotSchema(int startHour, int startMinute, int endHour, int endMinute) {
        this.startTime = LocalTime.of(startHour, startMinute);
        this.endTime = LocalTime.of(endHour, endMinute);
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
}
