package Models.TaskModel;

import Models.Priority;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class SimpleTask extends Task {
    private Duration periodicity;

    public SimpleTask(String name, LocalTime startTime, Duration duration, Priority priority, LocalDate deadline, String category, TaskStatus status, Duration periodicity) {
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
