package Models.Day;

import java.io.Serializable;
import java.time.LocalDate;

public class DaySchema implements Comparable<DaySchema>, Serializable {
    private LocalDate date;
    private int numberOfTasksCompletedOnThisDay;
    private boolean wasCongratulatedToday;
    private boolean receivedBadgeToday;
    public DaySchema(LocalDate date) {
        this.date = date;
        this.numberOfTasksCompletedOnThisDay = 0;
        this.wasCongratulatedToday = false;
        this.receivedBadgeToday = false;
    }
    public DaySchema(){
        this.date = LocalDate.now();
        this.numberOfTasksCompletedOnThisDay = 0;
        this.wasCongratulatedToday = false;
        this.receivedBadgeToday = false;
    }
    public DaySchema(int year, int month, int day){
        this.date = LocalDate.of(year, month, day);
        this.numberOfTasksCompletedOnThisDay = 0;
        this.wasCongratulatedToday = false;
        this.receivedBadgeToday = false;
    }

    public boolean isReceivedBadgeToday() {
        return receivedBadgeToday;
    }

    public void setReceivedBadgeToday(boolean receivedBadgeToday) {
        this.receivedBadgeToday = receivedBadgeToday;
    }

    public int getNumberOfTasksCompletedOnThisDay() {
        return numberOfTasksCompletedOnThisDay;
    }

    public void setNumberOfTasksCompletedOnThisDay(int tasksCompletedOnThisDay) {
        this.numberOfTasksCompletedOnThisDay = tasksCompletedOnThisDay;
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
