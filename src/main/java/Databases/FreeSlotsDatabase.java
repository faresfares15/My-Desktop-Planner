package Databases;

import Exceptions.DayDoesNotHaveFreeSlotsException;
import Exceptions.FreeSlotNotFoundException;
import Models.FreeSlot.FreeSlotSchema;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public interface FreeSlotsDatabase {
    ArrayList<FreeSlotSchema> findMany(LocalDate dayDate) throws DayDoesNotHaveFreeSlotsException;
    FreeSlotSchema find(LocalDate dayDate, LocalTime startTime);
    FreeSlotSchema create(LocalDate dayDate, LocalTime startTime, LocalTime endTime);
    FreeSlotSchema create(FreeSlotSchema freeSlotSchema);
    ArrayList<FreeSlotSchema> create(ArrayList<FreeSlotSchema> freeSlotsList);
    void initialize(LocalDate dayDate);
    FreeSlotSchema update(LocalDate dayDate, LocalTime startTime, LocalTime newStartTime) throws FreeSlotNotFoundException;
    FreeSlotSchema delete(LocalDate dayDate, LocalTime startTime);
}
