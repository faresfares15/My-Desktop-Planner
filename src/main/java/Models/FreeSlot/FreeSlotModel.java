package Models.FreeSlot;

import Databases.FreeSlotsDatabase;
import Databases.FreeSlotsFileDatabase;
import Databases.TaskDatabase;
import Databases.TaskFileDatabase;
import Exceptions.DayDoesNotHaveFreeSlotsException;
import Exceptions.FreeSlotNotFoundException;
import esi.tp_poo_final.HelloApplication;

import java.io.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.TreeMap;

public class FreeSlotModel {
    FreeSlotsDatabase freeSlotsDatabase;
    public FreeSlotModel(FreeSlotsDatabase freeSlotsDatabase){
        this.freeSlotsDatabase = freeSlotsDatabase;
    }

    public void save() throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(HelloApplication.usersDirectoryName
                + "/" + HelloApplication.currentUserName + "/" + HelloApplication.freeSlotDbFileName))) {
            objectOutputStream.writeObject(freeSlotsDatabase);
        }
    }
    public void load() throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(HelloApplication.usersDirectoryName
                + "/" + HelloApplication.currentUserName + "/" + HelloApplication.freeSlotDbFileName))) {
            freeSlotsDatabase = (FreeSlotsDatabase) objectInputStream.readObject();
        }
    }
    public ArrayList<FreeSlotSchema> findMany(LocalDate date) throws DayDoesNotHaveFreeSlotsException {
        return this.freeSlotsDatabase.findMany(date);
    }
    public TreeMap<LocalDate, ArrayList<FreeSlotSchema> > findMany(LocalDate startDate, LocalDate endDate) {
        return this.freeSlotsDatabase.findMany(startDate, endDate);
    }
    public FreeSlotSchema find(LocalDate date, LocalTime startTime) throws FreeSlotNotFoundException {
        return this.freeSlotsDatabase.find(date, startTime);
    }
    public FreeSlotSchema delete(LocalDate date, LocalTime startTime){
        return this.freeSlotsDatabase.delete(date, startTime);
    }
    public FreeSlotSchema create(LocalDate date, LocalTime startTime, LocalTime endTime){
        return this.freeSlotsDatabase.create(date, startTime, endTime);
    }
    public void initialize(LocalDate date){
        this.freeSlotsDatabase.initialize(date);
    }
    public FreeSlotSchema create(FreeSlotSchema freeSlotSchema){
        return this.freeSlotsDatabase.create(freeSlotSchema);
    }
    public ArrayList<FreeSlotSchema> create(ArrayList<FreeSlotSchema> freeSlotsList){
        return this.freeSlotsDatabase.create(freeSlotsList);
    }

    public FreeSlotSchema update(LocalDate date, LocalTime startTime, LocalTime newStartTime) throws FreeSlotNotFoundException {
        return this.freeSlotsDatabase.update(date, startTime, newStartTime);
    }
}
