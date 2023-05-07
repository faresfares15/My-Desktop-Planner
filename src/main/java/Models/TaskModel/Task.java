package Models.TaskModel;

import Models.Priority;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public abstract class Task {
    //TODO: add methods to it
    private String name;
    private LocalTime startTime;
    private Duration duration;
    private Priority priority;
    private LocalDate deadline;
    private String category;
    private TaskStatus status;

    public Task(String name, LocalTime startTime, Duration duration, Priority priority, LocalDate deadline, String category, TaskStatus status) {
        this.name = name;
        this.startTime = startTime;
        this.duration = duration;
        this.priority = priority;
        this.deadline = deadline;
        this.category = category;
        this.status = status;
    }

    public Task() {
    }
    public boolean contains(int startHour, int startMinute, int endHour, int endMinute) {
        LocalTime start = LocalTime.of(startHour, startMinute);
        LocalTime end = LocalTime.of(endHour, endMinute);
        return (start.isAfter(startTime) && end.isBefore(startTime.plus(duration)));
    }
    public String getName() {
        return name;
    }

    public Duration getDuration() {
        return duration;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public LocalTime getStartTime() {
        return startTime;
    }
    public LocalTime getEndTime() {
        return startTime.plus(duration);
    }
}
