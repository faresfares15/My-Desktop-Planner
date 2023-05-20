package Databases;

import Exceptions.DayDoesNotHaveFreeSlotsException;
import Exceptions.FreeSlotNotFoundException;
import Models.FreeSlot.FreeSlotSchema;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

public class FreeSlotsFileDatabase implements FreeSlotsDatabase{
    TreeMap<LocalDate, ArrayList<FreeSlotSchema>> freeSlotsMap = new TreeMap<>();

    public FreeSlotsFileDatabase(TreeMap<LocalDate, ArrayList<FreeSlotSchema>> freeSlotsMap) {
        this.freeSlotsMap = freeSlotsMap;
    }

    public FreeSlotsFileDatabase() {
    }

    public ArrayList<FreeSlotSchema> findMany(LocalDate dayDate) throws DayDoesNotHaveFreeSlotsException{
        //find the day in the map
        ArrayList<FreeSlotSchema> freeSlotsList = this.freeSlotsMap.get(dayDate);

        //check if the day has free slots
        if(freeSlotsList == null) throw new DayDoesNotHaveFreeSlotsException();

        //return the free slots
        return freeSlotsList;
    }
    public TreeMap<LocalDate, ArrayList<FreeSlotSchema> > findMany(LocalDate startDate, LocalDate endDate){
        //create a new tree map
        TreeMap<LocalDate, ArrayList<FreeSlotSchema>> result = new TreeMap<>();

        //loop through the dates
        for(LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)){

            if(freeSlotsMap.containsKey(date)) {
                result.put(date, freeSlotsMap.get(date));
            }
        }

        //return the free slots
        return result;
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
    public FreeSlotSchema update(LocalDate dayDate, LocalTime startTime, LocalTime newStartTime) throws FreeSlotNotFoundException{
        //find the day in the map

        //find the free slot in the list using the find method
        FreeSlotSchema freeSlotSchema = this.find(dayDate, startTime);
        if(freeSlotSchema == null) throw new FreeSlotNotFoundException();

        //update the free slot (setting the new start time)
        freeSlotSchema.setStartTime(newStartTime);

        //return the updated free slot
        return freeSlotSchema;
    }
    public FreeSlotSchema create(LocalDate dayDate, LocalTime startTime, LocalTime endTime){
        //create the free slot
        FreeSlotSchema freeSlotSchema = new FreeSlotSchema(dayDate, startTime, endTime);

        //find the day in the map
        ArrayList<FreeSlotSchema> freeSlotsList = this.freeSlotsMap.get(dayDate);

        //check if the day has free slots
        if(freeSlotsList == null){
            //create a new list of free slots
            freeSlotsList = new ArrayList<>();

            //add the free slot to the list
            freeSlotsList.add(freeSlotSchema);

            //add the list to the map
            this.freeSlotsMap.put(dayDate, freeSlotsList);
        }else{
            //add the free slot to the list
            freeSlotsList.add(freeSlotSchema);
            Collections.sort(freeSlotsList);
        }

        //return the created free slot
        return freeSlotSchema;
    }
    public FreeSlotSchema create(FreeSlotSchema freeSlotSchema){
        //create the free slot

        //find the day in the map
        ArrayList<FreeSlotSchema> freeSlotsList = this.freeSlotsMap.get(freeSlotSchema.getDate());

        //check if the day has free slots
        if(freeSlotsList == null){
            //create a new list of free slots
            freeSlotsList = new ArrayList<>();

            //add the free slot to the list
            freeSlotsList.add(freeSlotSchema);

            //add the list to the map
            this.freeSlotsMap.put(freeSlotSchema.getDate(), freeSlotsList);
        }else{
            //add the free slot to the list
            freeSlotsList.add(freeSlotSchema);
            Collections.sort(freeSlotsList);
        }

        //return the created free slot
        return freeSlotSchema;
    }

    @Override
    public ArrayList<FreeSlotSchema> create(ArrayList<FreeSlotSchema> freeSlotsList) {
        for(FreeSlotSchema freeSlotSchema : freeSlotsList){
            this.create(freeSlotSchema);
        }
        return freeSlotsList;
    }

    @Override
    public void initialize(LocalDate dayDate) {
        if (!freeSlotsMap.containsKey(dayDate)) freeSlotsMap.put(dayDate, new ArrayList<>());
        //just an empty arrayList for the days that doesn't have free slots
    }
}
