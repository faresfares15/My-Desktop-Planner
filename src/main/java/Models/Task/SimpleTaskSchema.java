package Models.Task;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class SimpleTaskSchema extends TaskSchema {
//    private Duration periodicity;
    private int periodicity;
//    public SimpleTaskSchema(LocalDate date, String name, LocalTime startTime, Duration duration, Priority priority, LocalDate deadline, String category, TaskStatus status, Duration periodicity) {
//        super(date, name, startTime, duration, priority, deadline, category, status);
//        this.periodicity = periodicity;
//    }
    public SimpleTaskSchema(LocalDate date, String name, LocalTime startTime, Duration duration, Priority priority, LocalDate deadline, String category, TaskStatus status, int periodicity) {
        super(date, name, startTime, duration, priority, deadline, category, status);
        this.periodicity = periodicity;
    }

    @Override
    public double getProgressPercentage() {
        return (double) this.getProgress().ordinal() / (Progress.values().length -1 );
    }

    public SimpleTaskSchema(String name, Duration duration, Priority priority, LocalDate deadline, String category, TaskStatus status, int periodicity) {
        super(name, duration, priority, deadline, category, status);
        this.periodicity = periodicity;
    }

    //    public Duration getPeriodicity() {
//        return periodicity;
//    }
    public int getPeriodicity() {
        return periodicity;
    }

//    public void setPeriodicity(Duration periodicity) {
//        this.periodicity = periodicity;
//    }
    public void setPeriodicity(int periodicity) {
        this.periodicity = periodicity;
    }
}
