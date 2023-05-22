package Models.Day;

import Models.Task.TaskSchema;
import Models.FreeSlot.FreeSlotSchema;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class DaySchema implements Comparable<DaySchema>, Serializable {
    private LocalDate date;
    private int tasksCompletedOnThisDay;
    private boolean wasCongratulatedToday;

    public DaySchema(LocalDate date) {
        this.date = date;
        this.tasksCompletedOnThisDay = 0;
        this.wasCongratulatedToday = false;
    }
    public DaySchema(){
        this.date = LocalDate.now();
    }

    public DaySchema(int year, int month, int day){
        this.date = LocalDate.of(year, month, day);
    }

    public int getTasksCompletedOnThisDay() {
        return tasksCompletedOnThisDay;
    }

    public void setTasksCompletedOnThisDay(int tasksCompletedOnThisDay) {
        this.tasksCompletedOnThisDay = tasksCompletedOnThisDay;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isWasCongratulatedToday() {
        return wasCongratulatedToday;
    }

    public void setWasCongratulatedToday(boolean wasCongratulatedToday) {
        this.wasCongratulatedToday = wasCongratulatedToday;
    }

    @Override
    public int compareTo(DaySchema o) {
        return this.date.compareTo(o.getDate());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        } else if (obj instanceof DaySchema) {
            return this.date.equals(((DaySchema) obj).getDate());
        }
        return false;

    }
}
