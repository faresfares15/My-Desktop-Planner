package Models.Task;

import Exceptions.InvalidPriorityException;
import Models.Category.CategorySchema;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;


public abstract class TaskSchema implements Comparable<TaskSchema>, Serializable {
    private LocalDate date; //useful to reference the day this instance belongs to
    private String name;
    private int id;
    private LocalTime startTime;
    private Duration duration;
    private Priority priority;
    private LocalDate deadline;
    private CategorySchema category;
    private TaskStatus status;
    private Progress progress;
    private int projectId = -1;
    private boolean isBlocked = false;

    public TaskSchema(LocalDate date, String name, LocalTime startTime, Duration duration, Priority
            priority, LocalDate deadline, CategorySchema category, TaskStatus status) {
        Random random = new Random();
        this.date = date;
        this.name = name;
        this.startTime = startTime;
        this.duration = duration;
        this.priority = priority;
        this.deadline = deadline;
        this.category = category;
        this.status = status;
        this.id = (name + category).hashCode() + duration.hashCode() + random.nextInt(1000);
        this.progress = Progress.NOT_REALIZED;
    }
    public TaskSchema(String name, LocalTime startTime, Duration duration, Priority priority, LocalDate deadline, CategorySchema category, TaskStatus status) {
        Random random = new Random();
        this.name = name;
        this.startTime = startTime;
        this.duration = duration;
        this.priority = priority;
        this.deadline = deadline;
        this.category = category;
        this.status = status;
        this.progress = Progress.NOT_REALIZED;
        this.id = (name + category).hashCode() + duration.hashCode() + random.nextInt(1000);
    }

    public TaskSchema(String name, Duration duration, Priority priority, LocalDate deadline, CategorySchema category, TaskStatus status) {
        Random random = new Random();
        this.name = name;
        this.id = (name + category.getName()).hashCode() + duration.hashCode() + random.nextInt(1000);
        this.duration = duration;
        this.priority = priority;
        this.deadline = deadline;
        this.category = category;
        this.status = status;
        this.progress = Progress.NOT_REALIZED;
    }

    public TaskSchema() {
    }

    public TaskSchema(String name, Duration duration, Priority priority, LocalDate deadline, TaskStatus status) {
        Random random = new Random();
        this.name = name;
        this.category = new CategorySchema("", Color.TRANSPARENT);
        this.id = (name + category.getName()).hashCode() + duration.hashCode() + random.nextInt(1000);
        this.duration = duration;
        this.priority = priority;
        this.deadline = deadline;
        this.status = status;
        this.progress = Progress.NOT_REALIZED;
    }

    public TaskSchema(LocalDate date, String name, LocalTime startTime, Duration duration, Priority priority, LocalDate deadline, TaskStatus status) {
        Random random = new Random();
        this.date = date;
        this.name = name;
        this.startTime = startTime;
        this.category = new CategorySchema("", Color.TRANSPARENT);
        this.id = (name + category.getName()).hashCode() + duration.hashCode() + random.nextInt(1000);
        this.duration = duration;
        this.priority = priority;
        this.deadline = deadline;
        this.status = status;
        this.progress = Progress.NOT_REALIZED;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
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

    public CategorySchema getCategory() {
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

    public void setCategory(CategorySchema category) {
        this.category = category;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    @Override
    public int compareTo(TaskSchema o) {
//        return this.deadline.compareTo(o.getDeadline()); //TODO: the task here is supposed to compare by start time, no?
        int dateComparison = this.date.compareTo(o.getDate());
        if(dateComparison == 0){
            int startTimeComparison = this.startTime.compareTo(o.getStartTime());
            if(startTimeComparison == 0){
                return this.duration.compareTo(o.getDuration());
            }else{
                return startTimeComparison;
            }
        }
        return dateComparison;
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
    public abstract double getProgressPercentage();
}
