package Models.FreeSlot;

import Databases.FreeSlotsDatabase;
import Exceptions.DayDoesNotHaveFreeSlotsException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class FreeSlotModel {
    FreeSlotsDatabase freeSlotsDatabase;
    public FreeSlotModel(FreeSlotsDatabase freeSlotsDatabase){
        this.freeSlotsDatabase = freeSlotsDatabase;
    }
    public ArrayList<FreeSlotSchema> findMany(LocalDate date) throws DayDoesNotHaveFreeSlotsException {
        return this.freeSlotsDatabase.findMany(date);
    }
    public FreeSlotSchema delete(LocalDate date, LocalTime startTime){
        return this.freeSlotsDatabase.delete(date, startTime);
    }
    public FreeSlotSchema update(LocalDate date, LocalTime startTime, Duration newDuration){
        //find the free slot
        FreeSlotSchema freeSlotSchema = this.freeSlotsDatabase.find(date, startTime);

        //update the free slot (cropping the upper part of the slot)
        freeSlotSchema.setStartTime(freeSlotSchema.getEndTime().minus(newDuration));

        //return the updated free slot
        return freeSlotSchema;
    }
}
