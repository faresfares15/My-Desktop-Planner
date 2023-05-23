package Models.Calendar;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.Duration;

public class Settings implements Serializable {
    private int minimalNumberOfTasksPerDay;
    private Duration minimalDuration;
    private DayOfWeek startOfTheWeek;

    public Settings(int minimalNumberOfTasksPerDay, Duration minimalDuration, DayOfWeek startOfTheWeek) {
        this.minimalNumberOfTasksPerDay = minimalNumberOfTasksPerDay;
        this.minimalDuration = minimalDuration;
        this.startOfTheWeek = startOfTheWeek;
    }

    public Settings() {
        this.minimalNumberOfTasksPerDay = 5;
        this.minimalDuration = Duration.ofMinutes(30);
        this.startOfTheWeek = DayOfWeek.SUNDAY;
    }

    public int getMinimalNumberOfTasksPerDay() {
        return minimalNumberOfTasksPerDay;
    }

    public void setMinimalNumberOfTasksPerDay(int minimalNumberOfTasksPerDay) {
        this.minimalNumberOfTasksPerDay = minimalNumberOfTasksPerDay;
    }

    public Duration getMinimalDuration() {
        return minimalDuration;
    }

    public void setMinimalDuration(Duration minimalDuration) {
        this.minimalDuration = minimalDuration;
    }

    public DayOfWeek getStartOfTheWeek() {
        return startOfTheWeek;
    }

    public void setStartOfTheWeek(DayOfWeek startOfTheWeek) {
        this.startOfTheWeek = startOfTheWeek;
    }
}
