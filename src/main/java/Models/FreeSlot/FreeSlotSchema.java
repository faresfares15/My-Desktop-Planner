package Models.FreeSlot;

import java.time.LocalTime;

public class FreeSlotSchema {
    private LocalTime startTime;
    private LocalTime endTime;
    public FreeSlotSchema(int startHour, int startMinute, int endHour, int endMinute) {
        this.startTime = LocalTime.of(startHour, startMinute);
        this.endTime = LocalTime.of(endHour, endMinute);
    }
}
