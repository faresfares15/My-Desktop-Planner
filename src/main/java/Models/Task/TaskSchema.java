package Models.Task;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public abstract class TaskSchema implements Comparable<TaskSchema> {
    private String name;
    private int id;
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

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }


    @Override
    public int compareTo(TaskSchema o) {
        return this.id - o.getId();
    }

    @Override
    public boolean equals(Object obj) {
        return this.id == ((TaskSchema) obj).getId();
    }
}
