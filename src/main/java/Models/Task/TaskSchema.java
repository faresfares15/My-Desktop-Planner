package Models.Task;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;


public abstract class TaskSchema implements Comparable<TaskSchema> {
    private LocalDate date; //useful to reference the day this instance belongs to
    private String name;
    private int id;
    private LocalTime startTime;
    private LocalTime endTime;
    private Duration duration;
    private Priority priority;
    private LocalDate deadline;
    private String category;
    private TaskStatus status;
    private Progress progress;

    public TaskSchema(LocalDate date, String name, LocalTime startTime, LocalTime endTime, Duration duration, Priority
            priority, LocalDate deadline, String category, TaskStatus status) {
        this.date = date;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.priority = priority;
        this.deadline = deadline;
        this.category = category;
        this.status = status;
        this.id = (name + category).hashCode() + deadline.hashCode();
    }
    public TaskSchema(String name, LocalTime startTime, Duration duration, Priority priority, LocalDate deadline, String category, TaskStatus status) {
        this.name = name;
        this.startTime = startTime;
        this.duration = duration;
        this.priority = priority;
        this.deadline = deadline;
        this.category = category;
        this.status = status;
        this.id = (name + category).hashCode() + deadline.hashCode();
    }

    public TaskSchema() {
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public LocalTime getStartTime() {
        return startTime;
    }



    public LocalTime getEndTime() {
        return endTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public int compareTo(TaskSchema o) {
        return this.date.compareTo(o.getDate());
    }

    @Override
    public boolean equals(Object obj) {
        return this.id == ((TaskSchema) obj).getId();
    }
}
