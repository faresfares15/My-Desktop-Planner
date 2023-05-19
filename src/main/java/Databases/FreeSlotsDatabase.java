package Databases;

import Exceptions.DayDoesNotHaveFreeSlotsException;
import Exceptions.FreeSlotNotFoundException;
import Models.FreeSlot.FreeSlotSchema;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.TreeMap;

public interface FreeSlotsDatabase extends Serializable {
    ArrayList<FreeSlotSchema> findMany(LocalDate dayDate) throws DayDoesNotHaveFreeSlotsException;
    TreeMap<LocalDate, ArrayList<FreeSlotSchema>> findMany(LocalDate startDate, LocalDate endDate);
    FreeSlotSchema find(LocalDate dayDate, LocalTime startTime);
    FreeSlotSchema create(LocalDate dayDate, LocalTime startTime, LocalTime endTime);
    FreeSlotSchema create(FreeSlotSchema freeSlotSchema);
    ArrayList<FreeSlotSchema> create(ArrayList<FreeSlotSchema> freeSlotsList);
    void initialize(LocalDate dayDate);
    FreeSlotSchema update(LocalDate dayDate, LocalTime startTime, LocalTime newStartTime) throws FreeSlotNotFoundException;
    FreeSlotSchema delete(LocalDate dayDate, LocalTime startTime);
}
