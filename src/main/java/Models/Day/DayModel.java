package Models.Day;

import Databases.DayDatabase;
import Exceptions.FreeSlotNotFoundException;
import Models.FreeSlot.FreeSlotSchema;
import Models.Task.TaskSchema;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

public class DayModel {
    private DayDatabase dayDatabase;


    public DayModel(DayDatabase dayDatabase){
        this.dayDatabase = dayDatabase;
    }
    public DaySchema create(LocalDate date, ArrayList<FreeSlotSchema> freeSlots, ArrayList<TaskSchema> tasks){
        return dayDatabase.create(date, freeSlots, tasks);
    }
    public ArrayList<DaySchema> findMany(LocalDate startDate, LocalDate endDate){
        return dayDatabase.findMany(startDate, endDate);
    }
    public DaySchema find(LocalDate date){
        return dayDatabase.find(date);
    }
//    public DaySchema update(LocalDate date, ArrayList<FreeSlotSchema> freeSlots, ArrayList<TaskSchema> tasks) throws FreeSlotNotFoundException {
//        return dayDatabase.update();
//    }
    public DaySchema delete(LocalDate date){
        return dayDatabase.delete(date);
    }
}
