package Models.Calendar;

import java.time.DayOfWeek;
import java.time.Duration;

public class Settings {
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
}
