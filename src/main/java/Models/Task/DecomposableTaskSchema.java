package Models.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DecomposableTaskSchema extends TaskSchema {
    private ArrayList<SimpleTaskSchema> subTasks;

    public DecomposableTaskSchema(SimpleTaskSchema task) {
        //The one we'll be using I think
        this.subTasks = new ArrayList<SimpleTaskSchema>();
        this.subTasks.add(task);
        this.setDate(task.getDate());
        this.setProjectId(task.getProjectId());
        this.setStatus(task.getStatus());
        this.setProgress(task.getProgress());
        this.setPriority(task.getPriority());
        this.setDuration(task.getDuration());
        this.setDate(task.getDate());
        this.setName(task.getName());
        this.setCategory(task.getCategory());
        this.setDeadline(task.getDeadline());
        this.setId((getName() + getCategory()).hashCode() + getDuration().hashCode());
    }

    public DecomposableTaskSchema() {

    }

    public ArrayList<SimpleTaskSchema> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(ArrayList<SimpleTaskSchema> subTasks) {
        this.subTasks = subTasks;
    }

    public void addSubTask(SimpleTaskSchema simpleTaskSchema){
        simpleTaskSchema.setId(this.getId());
        subTasks.add(simpleTaskSchema);
        //sort subtasks by date
        Collections.sort(subTasks);
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
