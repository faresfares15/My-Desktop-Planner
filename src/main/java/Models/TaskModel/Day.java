package Models.TaskModel;

import Controllers.TaskControllers.TaskSchedule;
import Controllers.TaskControllers.TaskScheduler;
import Models.FreeSlot;
import Trash.CalendarComponent.ScheduleMediator;
import Trash.TaskComponent.MinimalDurationException;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

public class Day {
    private LocalDate date;
    private ArrayList<FreeSlot> freeSlots = new ArrayList<>();
    private ArrayList<Task> tasks;
    public static TaskSchedule taskScheduler = new TaskScheduler();
    public Day(){
        this.date = LocalDate.now();
        this.tasks = null;
    }
    public Day(int year, int month, int day){
        this.date = LocalDate.of(year, month, day);
        this.tasks = null;
    }
    public int getMonth(){
        return this.date.getMonthValue();
    }
    public int getDay(){
        return this.date.getDayOfMonth();
    }
    public void addFreeSlot(int startHour, int startMinute, int endHour, int endMinute){
        this.freeSlots.add(new FreeSlot(startHour, startMinute, endHour, endMinute));
    }

}
