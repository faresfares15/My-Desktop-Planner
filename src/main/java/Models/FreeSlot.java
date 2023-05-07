package Models;

import CalendarComponent.ScheduleMediator;
import Controllers.Calendar;
import TaskComponent.MinimalDurationException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.TreeMap;

public class FreeSlot {
    private LocalTime startTime;
    private LocalTime endTime;
    public FreeSlot(int startHour, int startMinute, int endHour, int endMinute) {
        this.startTime = LocalTime.of(startHour, startMinute);
        this.endTime = LocalTime.of(endHour, endMinute);
    }
    public boolean containsSimpleTask(ScheduleMediator scheduleMediator, Duration minimalDuration)
            throws MinimalDurationException {

        if(scheduleMediator.getFloorTaskEndTime() != null){

            if(scheduleMediator.getFloorTaskEndTime().isAfter(this.endTime)) {
                //if the task starts after the end the of the slot:
                return false;
            }
        }else{
            scheduleMediator.setFloorTaskEndTime(this.startTime);
        }

        if (scheduleMediator.getCeilingTaskStartTime() != null) {
            if (scheduleMediator.getCeilingTaskStartTime().isBefore(this.startTime))
                //if the task ends before the start of the slot:
                return false;
        } else {
            scheduleMediator.setCeilingTaskStartTime(this.endTime);
        }

        if (Duration.between(scheduleMediator.getFloorTaskEndTime(), scheduleMediator.getCeilingTaskStartTime()).compareTo(minimalDuration.plus(Duration.between(scheduleMediator.getScheduleStartTime(), scheduleMediator.getScheduleEndTime()))) < 0) {
            System.out.printf("Warning: remaining time is less than the minimal duration: %d:%d\n", minimalDuration.toHours(), minimalDuration.toMinutes() % 60);
            throw new MinimalDurationException();
        }

        return true;
    }
    public boolean containsComposedTask(LocalDate date, ScheduleMediator scheduleMediator, Duration minimalDuration){
        TreeMap<LocalTime, LocalTime> tasksList = Calendar.taskManager.getTasks(date);

        for(Map.Entry<LocalTime, LocalTime> taskEntry: tasksList.entrySet()){
            //TODO: add the logic here to check if the current Free slot can contain the provided

        }
        return false;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
