package Models.Task;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public abstract class TaskSchema {
    private LocalDate date; //useful to reference the day this instance belongs to
    private String name;
    private LocalTime startTime;
    private Duration duration;
    private Priority priority;
    private LocalDate deadline;
    private String category;
    private TaskStatus status;
    private Progress progress;

    public TaskSchema(String name, LocalTime startTime, Duration duration, Priority priority, LocalDate deadline, String category, TaskStatus status) {
        this.name = name;
        this.startTime = startTime;
        this.duration = duration;
        this.priority = priority;
        this.deadline = deadline;
        this.category = category;
        this.status = status;
    }

    public TaskSchema() {
    }
}
