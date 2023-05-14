package Models.Calendar;

import Models.Day.DaySchema;
import Models.Project.ProjectSchema;

import java.time.DayOfWeek;
import java.time.Duration;
import java.util.ArrayList;

public class CalendarSchema {
    private int minimalNumberOfTasksPerDay;
    private Duration minimalDuration;
    private DaySchema[][] days;
    private DayOfWeek startOfTheWeek;

    public CalendarSchema(int minimalNumberOfTasksPerDay, Duration minimalDuration, DayOfWeek startOfTheWeek) {
        this.minimalNumberOfTasksPerDay = minimalNumberOfTasksPerDay;
        this.minimalDuration = minimalDuration;
        this.startOfTheWeek = startOfTheWeek;
        //TODO: copy-paste the method that inits the days to here
    }
    public CalendarSchema() {
        this.minimalNumberOfTasksPerDay = 5;
        this.minimalDuration = Duration.ofMinutes(30);
        this.startOfTheWeek = DayOfWeek.SUNDAY;
    }
}
