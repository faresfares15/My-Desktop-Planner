package Models.Day;

import Models.Task.TaskSchema;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.TreeSet;

public class DayModel {
    private TreeSet<DaySchema> days = new TreeSet<>();

    public DayModel(TreeSet<DaySchema> days) {
        this.days = days;
    }

    public DayModel() {
    }
    //CRUD operations
    public void create(DaySchema daySchema){
        days.add(daySchema);
    }
    public DaySchema read(LocalDate date){
        DaySchema tempDay = new DaySchema(date);
        //TODO: Check if we're going to let it like this because it's returning the one that's greater than or equal to what we're looking for
        return days.ceiling(tempDay);
    }
    public void update(DaySchema daySchema){
        if (days.contains(daySchema)){
            //Sounds crazy like that but it'll search for the objects that's equal to the one we're passing with the
            // overridden method in DaySchema and adds the new object that we passed
            days.remove(daySchema);
            days.add(daySchema);
        }else{
            //Adds it if it doesn't exist
            days.add(daySchema);
        }
    }
    public void delete(LocalDate date){
        //Passing the date will be more appropriate as we won't have to create a day object for it.
        DaySchema tempDay = new DaySchema(date);
        days.remove(tempDay);
    }
}
