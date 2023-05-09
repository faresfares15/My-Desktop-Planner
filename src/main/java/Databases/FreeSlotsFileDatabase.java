package Databases;

import Exceptions.DayDoesNotHaveFreeSlotsException;
import Models.FreeSlot.FreeSlotSchema;

import java.time.LocalDate;
import java.time.LocalTime;
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
    public FreeSlotSchema delete(LocalDate dayDate, LocalTime startTime){
        //find the day in the map
        ArrayList<FreeSlotSchema> freeSlotsList = this.freeSlotsMap.get(dayDate);

        //find the free slot in the list
        FreeSlotSchema freeSlotSchema = null;
        for(FreeSlotSchema freeSlot : freeSlotsList){
            if(freeSlot.getStartTime().equals(startTime)){
                freeSlotSchema = freeSlot;
                break;
            }
        }

        //remove the free slot from the list
        freeSlotsList.remove(freeSlotSchema);

        //return the deleted free slot
        return freeSlotSchema;
    }
    public FreeSlotSchema find(LocalDate dayDate, LocalTime startTime){
        //find the day in the map
        ArrayList<FreeSlotSchema> freeSlotsList = this.freeSlotsMap.get(dayDate);

        //find the free slot in the list
        FreeSlotSchema freeSlotSchema = null;
        for(FreeSlotSchema freeSlot : freeSlotsList){
            if(freeSlot.getStartTime().equals(startTime)){
                freeSlotSchema = freeSlot;
                break;
            }
        }

        //return the free slot
        return freeSlotSchema;
    }
}
