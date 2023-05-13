package Models.Task;

import java.util.ArrayList;

public class DecomposableTaskSchema extends TaskSchema {
    private ArrayList<SimpleTaskSchema> subTasks;

    public DecomposableTaskSchema(SimpleTaskSchema task) {
        //The one we'll be using I think
        this.subTasks = new ArrayList<SimpleTaskSchema>();
        this.subTasks.add(task);
        this.setDate(task.getDate());
        this.setName(task.getName());
        this.setCategory(task.getCategory());
        this.setDeadline(task.getDeadline());
        this.setId((getName() + getCategory()).hashCode() + getDeadline().hashCode());
    }


    public ArrayList<SimpleTaskSchema> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(ArrayList<SimpleTaskSchema> subTasks) {
        this.subTasks = subTasks;
    }

    public void addSubTask(SimpleTaskSchema simpleTaskSchema){
        subTasks.add(simpleTaskSchema);
    }

    @Override
    public double getProgressPercentage() {
        double sum = 0;
        for (int i = 1; i < subTasks.size(); i++) {
            sum += subTasks.get(i).getProgressPercentage();
        }
        // minus 1 because we don't want to count the mother task from which the others were decomposed
        return sum / (subTasks.size()-1);
    }
}
