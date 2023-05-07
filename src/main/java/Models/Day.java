package Models;

import CalendarComponent.ScheduleMediator;
import Controllers.TaskSchedule;
import Controllers.TaskScheduler;
import TaskComponent.*;

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
    public boolean isFreeSlotAvailable(ScheduleMediator scheduleMediator, Duration minimalDuration)
            throws MinimalDurationException {
        for(FreeSlot freeSlot : this.freeSlots){
            if(freeSlot.containsSimpleTask(scheduleMediator, minimalDuration))
                return true;
        }
        System.out.println("No free slot available for this task");
        return false;
    }
    public boolean isFreeSlotAvailableComposed(ScheduleMediator scheduleMediator, Duration minimalDuration){
        for(FreeSlot freeSlot: this.freeSlots){
            if(freeSlot.containsComposedTask(this.date, scheduleMediator, minimalDuration))
                return true;
        }
        System.out.println("No free slot available for this task");
        return false;
    }
}
