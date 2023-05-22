package Models.Category;

import javafx.scene.paint.Color;

import java.time.Duration;
import java.util.TreeMap;

public class CategorySchema implements Comparable<CategorySchema> {
    private String name;
    private Color color;
    private int totalTasks = 0;
    private Duration totalDuration = Duration.ZERO;
    public CategorySchema(String name, Color color) {
        this.name = name;
        this.color = color;
    }
    public CategorySchema(String name, Color color, Duration duration) {
        this.name = name;
        this.color = color;
        this.totalDuration = duration;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }

    public Duration getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(Duration totalDuration) {
        this.totalDuration = totalDuration;
    }

    public int getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(int totalTasks) {
        this.totalTasks = totalTasks;
    }
    public void incrementTotalTasks(){
        this.totalTasks++;
    }
    public void decrementTotalTasks(){
        this.totalTasks--;
    }
    public void addDuration(Duration duration){
        this.totalDuration = this.totalDuration.plus(duration);
    }

    @Override
    public boolean equals(Object obj) {
        return this.name.equals(((CategorySchema)obj).getName());
    }
    @Override
    public int compareTo(CategorySchema o) {
        return this.name.compareTo(o.getName());
    }
}
