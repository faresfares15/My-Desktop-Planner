package Models.Day;

import Models.Task.TaskSchema;
import Models.FreeSlot.FreeSlotSchema;

import java.time.LocalDate;
import java.util.ArrayList;

public class DaySchema implements Comparable<DaySchema> {
    private LocalDate date;
    private ArrayList<FreeSlotSchema> freeSlots = new ArrayList<>();
    private ArrayList<TaskSchema> taskSchemas;
    public DaySchema(){
        this.date = LocalDate.now();
        this.taskSchemas = null;
    }

    public DaySchema(LocalDate date, ArrayList<FreeSlotSchema> freeSlots, ArrayList<TaskSchema> taskSchemas) {
        this.date = date;
        this.freeSlots = freeSlots;
        this.taskSchemas = taskSchemas;
    }

    public DaySchema(int year, int month, int day){
        this.date = LocalDate.of(year, month, day);
        this.taskSchemas = null;
    }

    public LocalDate getDate() {
        return date;
    }
    public DaySchema(LocalDate date) {
        this.date = date;
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
