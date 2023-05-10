package Models.FreeSlot;

import Databases.FreeSlotsDatabase;
import Exceptions.DayDoesNotHaveFreeSlotsException;
import Exceptions.FreeSlotNotFoundException;

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
    public FreeSlotSchema create(LocalDate date, LocalTime startTime, LocalTime endTime){
        return this.freeSlotsDatabase.create(date, startTime, endTime);
    }
    public FreeSlotSchema update(LocalDate date, LocalTime startTime, LocalTime newStartTime) throws FreeSlotNotFoundException {
        return this.freeSlotsDatabase.update(date, startTime, newStartTime);
    }
}
