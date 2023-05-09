package Databases;

import Exceptions.DayDoesNotHaveFreeSlotsException;
import Models.FreeSlot.FreeSlotSchema;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public interface FreeSlotsDatabase {
    ArrayList<FreeSlotSchema> findMany(LocalDate dayDate) throws DayDoesNotHaveFreeSlotsException;
    FreeSlotSchema find(LocalDate dayDate, LocalTime startTime);
    FreeSlotSchema delete(LocalDate dayDate, LocalTime startTime);
}
