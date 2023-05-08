package Models.Task;

import java.util.ArrayList;

public class ComposedTaskSchema extends TaskSchema {
    private ArrayList<SimpleTaskSchema> subTasks;

    public ComposedTaskSchema(ArrayList<SimpleTaskSchema> subTasks) {
        this.subTasks = subTasks;
    }
    public ComposedTaskSchema(SimpleTaskSchema task) {
        //The one we'll be using I think
        this.subTasks = new ArrayList<SimpleTaskSchema>();
        this.subTasks.add(task);
    }

    public ArrayList<SimpleTaskSchema> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(ArrayList<SimpleTaskSchema> subTasks) {
        this.subTasks = subTasks;
    }

}
