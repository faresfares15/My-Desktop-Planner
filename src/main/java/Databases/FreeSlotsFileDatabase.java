package Databases;

import Exceptions.DayDoesNotHaveFreeSlotsException;
import Models.FreeSlot.FreeSlotSchema;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TreeMap;

public class FreeSlotsFileDatabase implements FreeSlotsDatabase{
    TreeMap<LocalDate, ArrayList<FreeSlotSchema>> freeSlotsMap = new TreeMap<>();
    public ArrayList<FreeSlotSchema> findMany(LocalDate dayDate) throws DayDoesNotHaveFreeSlotsException{
        //find the day in the map
        ArrayList<FreeSlotSchema> freeSlotsList = this.freeSlotsMap.get(dayDate);

        //check if the day has free slots
        if(freeSlotsList == null) throw new DayDoesNotHaveFreeSlotsException();

        //return the free slots
        return freeSlotsList;
    }
}
