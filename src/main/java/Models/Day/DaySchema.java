package Models.Day;

import Models.Task.TaskSchema;
import Models.FreeSlot.FreeSlotSchema;

import java.time.LocalDate;
import java.util.ArrayList;

public class DaySchema implements Comparable<DaySchema> {
    private LocalDate date;

    public DaySchema(LocalDate date) {
        this.date = date;
    }
    public DaySchema(){
        this.date = LocalDate.now();
    }

    public DaySchema(int year, int month, int day){
        this.date = LocalDate.of(year, month, day);
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public int compareTo(DaySchema o) {
        return this.date.compareTo(o.getDate());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        } else if (obj instanceof DaySchema) {
            return this.date.equals(((DaySchema) obj).getDate());
        }
        return false;

    }
}
