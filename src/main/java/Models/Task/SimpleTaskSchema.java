package Models.Task;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class SimpleTaskSchema extends TaskSchema {
    private Duration periodicity;

    public SimpleTaskSchema(String name, LocalTime startTime, Duration duration, Priority priority, LocalDate deadline, String category, TaskStatus status) {
        //Sounds useless but it'll be used in the DecomposableTask constructor
        super(name, startTime, duration, priority, deadline, category, status);
    }

    public SimpleTaskSchema(String name, LocalTime startTime, Duration duration, Priority priority, LocalDate deadline, String category, TaskStatus status, Duration periodicity) {
        super(name, startTime, duration, priority, deadline, category, status);
        this.periodicity = periodicity;
    }

    public Duration getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(Duration periodicity) {
        this.periodicity = periodicity;
    }
}
