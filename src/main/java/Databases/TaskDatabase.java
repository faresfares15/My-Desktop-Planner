package Databases;

import Models.Task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.TreeMap;

public interface TaskDatabase {
    public void addTask(Task task);
    public TreeMap<LocalTime, LocalTime> getTasks(LocalDate date);
    public boolean isTaskOverlapping(LocalDate date, LocalTime startTime, LocalTime endTime);
    public boolean isTaskIntervalsOverlapping(LocalDate date, int startHour, int startMinute, int endHour, int endMinute);
    public LocalTime getFloorTaskEndTime(LocalDate date, int startHour, int startMinute, int endHour, int endMinute);
    public LocalTime getCeilingTaskStartTime(LocalDate date, int startHour, int startMinute, int endHour, int endMinute);
}
