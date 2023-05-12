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
    ArrayList<DaySchema> findMany(LocalDate startDate, LocalDate endDate);
    public DaySchema find(LocalDate date);
    DaySchema create(LocalDate dayDate, ArrayList<FreeSlotSchema> freeSlotsOfTheDay, ArrayList<TaskSchema> tasksOfTheDay);
    DaySchema update() throws FreeSlotNotFoundException;
    DaySchema delete(LocalDate date);
}
