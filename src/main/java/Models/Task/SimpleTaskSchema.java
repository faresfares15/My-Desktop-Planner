package Models.Task;

import Models.Category.CategorySchema;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class SimpleTaskSchema extends TaskSchema{
//    private Duration periodicity;
    private int periodicity;
    public SimpleTaskSchema(LocalDate date, String name, LocalTime startTime, Duration duration, Priority priority, LocalDate deadline, TaskStatus status, int periodicity) {
        super(date, name, startTime, duration, priority, deadline, status);
        this.periodicity = periodicity;
    }
    @Override
    public double getProgressPercentage() {
        return this.getProgress().pourcentage;
    }

    public SimpleTaskSchema(String name, Duration duration, Priority priority, LocalDate deadline, TaskStatus status, int periodicity){
        super(name, duration, priority, deadline, status);
        this.periodicity = periodicity;
    }

    public int getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(int periodicity) {
        this.periodicity = periodicity;
    }
}
