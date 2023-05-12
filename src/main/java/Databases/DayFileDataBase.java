package Databases;

import Exceptions.FreeSlotNotFoundException;
import Models.Day.DaySchema;
import Models.FreeSlot.FreeSlotSchema;
import Models.Task.TaskSchema;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.TreeSet;

public class DayFileDataBase implements DayDatabase{
    private TreeSet<DaySchema> days = new TreeSet<>();


    @Override
    public ArrayList<DaySchema> findMany(LocalDate startDate, LocalDate endDate) {
        ArrayList<DaySchema> daysList = new ArrayList<>();
        for (DaySchema day: days) {
            if ( (day.getDate().isAfter(startDate) && day.getDate().isBefore(endDate)) || day.getDate().isEqual(startDate) || day.getDate().isEqual(endDate)){
                daysList.add(day);
            }
        }

        return daysList;
    }

    @Override
    public DaySchema create(LocalDate dayDate, ArrayList<FreeSlotSchema> freeSlotsOfTheDay, ArrayList<TaskSchema> tasksOfTheDay) {
        DaySchema day = new DaySchema(dayDate, freeSlotsOfTheDay, tasksOfTheDay);
        if (days.add(day)) return day;
        else return null;
    }

    @Override
    public DaySchema update() throws FreeSlotNotFoundException {
        return null;
    //TODO: Check if it's necessary or the update in the tasks and freeSLot DBs will be enough because everything is passed as a reference
    }

    //CRUD operations
    @Override
    public DaySchema find(LocalDate date){
        DaySchema tempDay = new DaySchema(date);
        //TODO: Check if we're going to let it like this because it's returning the one that's greater than or equal to what we're looking for
        return days.ceiling(tempDay);
    }
    public void update(DaySchema daySchema){
        if (days.contains(daySchema)){
            //Sounds crazy like that but it'll search for the objects that's equal to the one we're passing with the
            // overridden method in DaySchema and adds the new object that we passed
            days.remove(daySchema);
            days.add(daySchema);
        }else{
            //Adds it if it doesn't exist
            days.add(daySchema);
        }
    }
    @Override
    public DaySchema delete(LocalDate date){
        //Passing the date will be more appropriate as we won't have to create a day object for it.
        DaySchema tempDay = new DaySchema(date);
        tempDay = days.ceiling(tempDay);
        if(days.remove(tempDay)) return tempDay;
        else return null;
    }
}
