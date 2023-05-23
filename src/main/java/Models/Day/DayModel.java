package Models.Day;

import Databases.*;
import Exceptions.FreeSlotNotFoundException;
import Models.FreeSlot.FreeSlotSchema;
import Models.Task.TaskSchema;
import esi.tp_poo_final.HelloApplication;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

public class DayModel {
    private DayDatabase dayDatabase;


    public DayModel(DayDatabase dayDatabase){
        this.dayDatabase = dayDatabase;
    }
    public void save() throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(HelloApplication.usersDirectoryName
                + "/" + HelloApplication.currentUserName + "/" + HelloApplication.dayDbFileName))) {
            objectOutputStream.writeObject(dayDatabase);
        }
    }
    public void load() throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(HelloApplication.usersDirectoryName
                + "/" + HelloApplication.currentUserName + "/" + HelloApplication.dayDbFileName))) {
            dayDatabase = (DayFileDataBase) objectInputStream.readObject();
        }
    }
    public DaySchema create(LocalDate date){
        return dayDatabase.create(date);
    }
    public ArrayList<DaySchema> findMany(LocalDate startDate, LocalDate endDate){
        return dayDatabase.findMany(startDate, endDate);
    }
    public DaySchema find(LocalDate date){
        return dayDatabase.find(date);
    }
//    public DaySchema update(LocalDate date, ArrayList<FreeSlotSchema> freeSlots, ArrayList<TaskSchema> tasks) throws FreeSlotNotFoundException {
//        return dayDatabase.update();
//    }
    public DaySchema delete(LocalDate date){
        return dayDatabase.delete(date);
    }
}
