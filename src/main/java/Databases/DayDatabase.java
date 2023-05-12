package Databases;

import Exceptions.DayDoesNotHaveFreeSlotsException;
import Exceptions.FreeSlotNotFoundException;
import Models.Day.DaySchema;
import Models.FreeSlot.FreeSlotSchema;
import Models.Task.TaskSchema;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public interface DayDatabase {
    ArrayList<DaySchema> findMany(LocalDate dayDate);
    DaySchema find(LocalDate dayDate);
    DaySchema create(LocalDate dayDate, ArrayList<FreeSlotSchema> freeSlotsOfTheDay, ArrayList<TaskSchema> tasksOfTheDay);
    FreeSlotSchema update(LocalDate dayDate, LocalTime startTime, LocalTime newStartTime) throws FreeSlotNotFoundException;
    FreeSlotSchema delete(LocalDate dayDate);
}
