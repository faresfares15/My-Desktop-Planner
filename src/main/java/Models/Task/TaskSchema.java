package Models.Task;

import Exceptions.InvalidPriorityException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;


public abstract class TaskSchema implements Comparable<TaskSchema> {
    private LocalDate date; //useful to reference the day this instance belongs to
    private String name;
    private int id;
    private LocalTime startTime;
    private Duration duration;
    private Priority priority;
    private LocalDate deadline;
    private String category;
    private TaskStatus status;
    private Progress progress;

    public TaskSchema(LocalDate date, String name, LocalTime startTime, Duration duration, Priority
            priority, LocalDate deadline, String category, TaskStatus status) {
        this.date = date;
        this.name = name;
        this.startTime = startTime;
        this.duration = duration;
        this.priority = priority;
        this.deadline = deadline;
        this.category = category;
        this.status = status;
        this.id = (name + category).hashCode() + deadline.hashCode();
        this.progress = Progress.NOT_REALIZED;
    }
    public TaskSchema(String name, LocalTime startTime, Duration duration, Priority priority, LocalDate deadline, String category, TaskStatus status) {
        this.name = name;
        this.startTime = startTime;
        this.duration = duration;
        this.priority = priority;
        this.deadline = deadline;
        this.category = category;
        this.status = status;
        this.progress = Progress.NOT_REALIZED;
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
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Duration getDuration() {
        return duration;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Progress getProgress() {
        return progress;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int compareTo(TaskSchema o) {
        return this.date.compareTo(o.getDate()); //TODO: the task here is supposed to compare by start time, no?
    }

    @Override
    public boolean equals(Object obj) {
        return this.id == ((TaskSchema) obj).getId();
    }

    //Utility methods
    public static boolean validatePriority(String priorityName) throws InvalidPriorityException {
        //Check if the provided priority string is a valid priority of the enum Priority
        for(Priority priority: Priority.values()){
            if(priority.name().equals(priorityName)){
                return true;
            }
        }
        return false;
    }
}
