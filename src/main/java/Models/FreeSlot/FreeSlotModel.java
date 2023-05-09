package Models.FreeSlot;

import Databases.FreeSlotsDatabase;
import Exceptions.DayDoesNotHaveFreeSlotsException;

import java.time.LocalDate;
import java.util.ArrayList;

public class FreeSlotModel {
    FreeSlotsDatabase freeSlotsDatabase;
    public FreeSlotModel(FreeSlotsDatabase freeSlotsDatabase){
        this.freeSlotsDatabase = freeSlotsDatabase;
    }
    public ArrayList<FreeSlotSchema> findMany(LocalDate date) throws DayDoesNotHaveFreeSlotsException {
        return this.freeSlotsDatabase.findMany(date);
    }
}
