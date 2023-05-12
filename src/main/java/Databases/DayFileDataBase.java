package Databases;

import Exceptions.FreeSlotNotFoundException;
import Models.Day.DaySchema;
import Models.FreeSlot.FreeSlotSchema;
import Models.Task.TaskSchema;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class DayFileDataBase implements DayDatabase{
    @Override
    public ArrayList<DaySchema> findMany(LocalDate dayDate) {
        return null;
    }

    @Override
    public DaySchema find(LocalDate dayDate) {
        return null;
    }

    @Override
    public DaySchema create(LocalDate dayDate, ArrayList<FreeSlotSchema> freeSlotsOfTheDay, ArrayList<TaskSchema> tasksOfTheDay) {
        return null;
    }

    @Override
    public FreeSlotSchema update(LocalDate dayDate, LocalTime startTime, LocalTime newStartTime) throws FreeSlotNotFoundException {
        return null;
    }

    @Override
    public FreeSlotSchema delete(LocalDate dayDate) {
        return null;
    }
}
