package Controllers;

import Models.Priority;
import Models.TaskStatus;

import java.time.Duration;
import java.time.LocalDate;

public interface CalendarManager {
    public void addFreeSlot(int month, int day, int startHour, int startMinute, int endHour, int endMinute);
//    public void addFreeSlot(int startHour, int startMinute, int endHour, int endMinute);
public void scheduleSimpleTask(int month, int day, int startHour, int startMinute, int endHour, int endMinute,
                               String taskName, Priority priority, LocalDate deadline, String category,
                               TaskStatus status, Duration periodicity);
    public void scheduleSimpleTask2(int month, int day, int startHour, int startMinute, int endHour, int endMinute,
                                   String taskName, Priority priority, LocalDate deadline, String category,
                                   TaskStatus status, Duration periodicity);

}
