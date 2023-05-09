package Models.Day;

import Models.Task.TaskSchema;
import Models.FreeSlot.FreeSlotSchema;

import java.time.LocalDate;
import java.util.ArrayList;

public class DaySchema{
    private LocalDate date;
    private ArrayList<FreeSlotSchema> freeSlots = new ArrayList<>();
    private ArrayList<TaskSchema> taskSchemas;
    public DaySchema(){
        this.date = LocalDate.now();
        this.taskSchemas = null;
    }
    public DaySchema(int year, int month, int day){
        this.date = LocalDate.of(year, month, day);
        this.taskSchemas = null;
    }

    public LocalDate getDate() {
        return date;
    }
}
