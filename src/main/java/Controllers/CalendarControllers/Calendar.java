package Controllers.CalendarControllers;

import Controllers.TaskControllers.TaskController;
import Controllers.TaskControllers.TaskManager;
import Models.ProjectModel.Project;
import Models.TaskModel.Day;
import Models.TaskModel.TaskStatus;
import Trash.CalendarComponent.*;
import Models.*;
import Trash.TaskComponent.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Calendar implements CalendarManager {
    protected static TaskManager taskManager = new TaskController();
    private ScheduleMediator scheduleMediator = new ScheduleMediator();
    private int currentYear;
    private Day[][] yearDays = new Day[12][]; // for each month, the days of the month
    private boolean isLeapYear;
    private Day currentDay;
    private int currentStreak;
    private ArrayList<Project> projectsList;
    private Settings settings;

    public Calendar(Day[][] yearDays, boolean isLeapYear, Day currentDay, int currentStreak,
                    ArrayList<Project> projectsList, Settings settings) {
        this.yearDays = yearDays;
        this.isLeapYear = isLeapYear;
        this.currentDay = currentDay;
        this.currentStreak = currentStreak;
        this.projectsList = projectsList;
        this.settings = settings;
    }
    public Calendar(){
        this.currentYear = 2023;
        this.isLeapYear = false;
        initYearDays(this.isLeapYear);
        this.currentDay = new Day(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());;
        this.currentStreak = 0;
        this.projectsList = null;
        this.settings = new Settings();
    }
    public Calendar(boolean isLeapYear){
        this.currentYear = 2023;
        this.isLeapYear = isLeapYear;
        initYearDays(this.isLeapYear);
        this.currentDay = new Day(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
        this.currentStreak = 0;
        this.projectsList = null;
        this.settings = new Settings();
    }

    public void setCurrentDay(int month, int day) {
        this.currentDay = this.yearDays[month][day];
    }

    public int getCurrentYear() {
        return currentYear;
    }

    public void addFreeSlot(int month, int day, int startHour, int startMinute, int endHour, int endMinute){
        this.yearDays[month][day].addFreeSlot(startHour, startMinute, endHour, endMinute);
    }

    public Settings getSettings() {
        return settings;
    }
    public boolean isFreeSlotAvailable(int month, int day, ScheduleMediator scheduleMediator, Duration minimalDuration)
            throws MinimalDurationException {
        return this.yearDays[month][day].isFreeSlotAvailable(scheduleMediator, minimalDuration);
    }
    public Day getDay( int month, int day){
        return this.yearDays[month][day];
    }

    private void initYearDays(boolean isLeapYear){
        for(int month = 0; month < 12; month++){
            int monthDays = getMonthDays(month, isLeapYear);
            this.yearDays[month] = new Day[monthDays];
            for(int day = 0; day < monthDays; day++){
                this.yearDays[month][day] = new Day(this.currentYear, month+1,day+1);
            }
        }
    }
    private int getMonthDays(int month, boolean isLeapYear){
        switch (month) {
            case 0, 2, 4, 6, 7, 9, 11 -> {
                return 31;
            }
            case 1 -> {
                if (isLeapYear) {
                    return 29;
                }
                return 28;
            }
            case 3, 5, 8, 10 -> {
                return 30;
            }
            default -> {
                return 0;
            }
        }
    }

    public void addFreeSlot(Calendar calendar, int startHour, int startMinute, int endHour, int endMinute){
        calendar.addFreeSlot(LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth(),startHour, startMinute, endHour, endMinute);
    }
    public void scheduleSimpleTask(int month, int day, int startHour, int startMinute, int endHour, int endMinute,
                                   String taskName, Priority priority, LocalDate deadline, String category,
                                   TaskStatus status, Duration periodicity){
        //This schedules a task given its starting and ending times
        try{
            //TODO: implement checkTask() in the TaskController and validate the input task format
            scheduleMediator.setScheduleStartTime( LocalTime.of(startHour, startMinute) );
            scheduleMediator.setScheduleEndTime( LocalTime.of(endHour, endMinute) );

            //check if the tasks doesn't overlap with another existing task in the given day
            if(this.taskManager.isTaskOverlapping(LocalDate.of(this.currentYear, month, day),
                    scheduleMediator.getScheduleStartTime(), scheduleMediator.getScheduleEndTime()))
            {
                throw new Exception("Task "+taskName+ " overlaps with another existing task");
            }

            //get the times of tasks that are around the new task
            scheduleMediator.setFloorTaskEndTime( this.taskManager.getFloorTaskEndTime(LocalDate.of(this.currentYear, month, day),
                    startHour, startMinute, endHour, endMinute));

            scheduleMediator.setCeilingTaskStartTime( this.taskManager.getCeilingTaskStartTime(LocalDate.of(this.currentYear, month, day),
                    startHour, startMinute, endHour, endMinute) );

            //check if a free slot is available for this task (throws MinimalDurationException)
            if(!getDay(month, day).isFreeSlotAvailable(scheduleMediator, settings.getMinimalDuration()) ){
                throw new Exception("No free slot available for the task: "+taskName);
            }

            //creating the new task
            Duration duration = Duration.between(scheduleMediator.getScheduleStartTime(), scheduleMediator.getScheduleEndTime());
            this.taskManager.createSimpleTask(taskName, scheduleMediator.getScheduleStartTime(), duration, priority, deadline, category, status, periodicity);

        }catch (MinimalDurationException e){
            System.out.println("Minimal duration exception, allocate full available slot");

            Duration duration = Duration.between(scheduleMediator.getFloorTaskEndTime(), scheduleMediator.getCeilingTaskStartTime());
            this.taskManager.createSimpleTask(taskName, scheduleMediator.getFloorTaskEndTime(), duration, priority, deadline, category, status, periodicity);

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void scheduleSimpleTask2(int month, int day, int startHour, int startMinute, int endHour, int endMinute,
                                   String taskName, Priority priority, LocalDate deadline, String category,
                                   TaskStatus status, Duration periodicity){
        //This schedules a task given its starting and ending times

        try{
            //TODO: implement checkTask() in the TaskController and validate the input task format
            scheduleMediator.setScheduleStartTime( LocalTime.of(startHour, startMinute) );
            scheduleMediator.setScheduleEndTime( LocalTime.of(endHour, endMinute) );

            //check if the tasks doesn't overlap with another existing task in the given day
            if(Day.taskScheduler.isTaskOverlapping(LocalDate.of(this.currentYear, month, day),
                    scheduleMediator.getScheduleStartTime(), scheduleMediator.getScheduleEndTime()))
            {
                throw new Exception("Task "+taskName+ " overlaps with another existing task");
            }

            //get the times of tasks that are around the new task
            scheduleMediator.setFloorTaskEndTime( this.taskManager.getFloorTaskEndTime(LocalDate.of(this.currentYear, month, day),
                    startHour, startMinute, endHour, endMinute));

            scheduleMediator.setCeilingTaskStartTime( this.taskManager.getCeilingTaskStartTime(LocalDate.of(this.currentYear, month, day),
                    startHour, startMinute, endHour, endMinute) );

            //check if a free slot is available for this task (throws MinimalDurationException)
            if(!getDay(month, day).isFreeSlotAvailable(scheduleMediator, settings.getMinimalDuration()) ){
                throw new Exception("No free slot available for the task: "+taskName);
            }

            //creating the new task
            Duration duration = Duration.between(scheduleMediator.getScheduleStartTime(), scheduleMediator.getScheduleEndTime());
            Day.taskScheduler.createSimpleTask(taskName, scheduleMediator.getScheduleStartTime(), duration, priority, deadline, category, status, periodicity);

        }catch (MinimalDurationException e){
            System.out.println("Minimal duration exception, allocate full available slot");

            Duration duration = Duration.between(scheduleMediator.getFloorTaskEndTime(), scheduleMediator.getCeilingTaskStartTime());
            Day.taskScheduler.createSimpleTask(taskName, scheduleMediator.getFloorTaskEndTime(), duration, priority, deadline, category, status, periodicity);

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void scheduleSimpleTask(int month, int day, Duration duration, String taskName, Priority priority,
                                   LocalDate deadline, String category, TaskStatus status, Duration periodicity){
        //This schedules a task given only its duration
        //TODO: validate input
        try{
            //The approach is to find all free slots that can hold the task, then check if enough space is remaining
            /*
            1- get a free slot that can contain this task
            2- iterate over tasks in this day and check if the same free slot contains another task:
                if yes: subtract the task duration from the free slot duration
            3- see if the final resulted duration still can contain the new task
            
            issue: if the tasks are scattered in the free slot, the task has to be decomposed (becomes decomposed task)
             */
            //1- get a free slot that can contain this task
            //access the selected day, get the free slots, loop through all of them and do checks


            //The way I will see things is like this: Calendar provides the target day with task timing infos,
                //then the day will use its scheduler to check and return an answer to the calendar, then the calendar
                //decides whether to tell its task manager to create the task
            
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
