package Models;

import java.time.DayOfWeek;
import java.time.Duration;

public class Settings {
    private int minSeuil;
    private Duration minimalDuration;
    private DayOfWeek startOfTheWeek;

    public Settings(int minSeuil, Duration minimalDuration, DayOfWeek startOfTheWeek) {
        this.minSeuil = minSeuil;
        this.minimalDuration = minimalDuration;
        this.startOfTheWeek = startOfTheWeek;
    }

    public Settings() {
        this.minSeuil = 5;
        this.minimalDuration = Duration.ofMinutes(30);
        this.startOfTheWeek = DayOfWeek.SUNDAY;
    }

    public int getMinSeuil() {
        return minSeuil;
    }

    public void setMinSeuil(int minSeuil) {
        this.minSeuil = minSeuil;
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
