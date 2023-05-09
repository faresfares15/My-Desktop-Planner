package Databases;

import Exceptions.DayDoesNotHaveFreeSlotsException;
import Models.FreeSlot.FreeSlotSchema;

import java.time.LocalDate;
import java.util.ArrayList;

public interface FreeSlotsDatabase {
    ArrayList<FreeSlotSchema> findMany(LocalDate dayDate) throws DayDoesNotHaveFreeSlotsException;
}
