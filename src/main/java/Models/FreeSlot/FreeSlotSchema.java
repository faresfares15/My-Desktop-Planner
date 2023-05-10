package Models.FreeSlot;

import java.time.Duration;
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

    public FreeSlotSchema(LocalDate dayDate, LocalTime startTime, LocalTime endTime) {
        this.dayDate = dayDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    //get duration (instance of Duration)
    public Duration getDuration() {
        return Duration.between(startTime, endTime);
    }


}
