package Models.Category;

import javafx.scene.paint.Color;

//import java.awt.*;


public class CategorySchema implements Comparable<CategorySchema> {
    private String name;
    private Color color;

    public CategorySchema(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public CategorySchema() {
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

    @Override
    public boolean equals(Object obj) {
        return this.name.equals(((CategorySchema)obj).getName());
    }

    @Override
    public int compareTo(CategorySchema o) {
        return this.name.compareTo(o.getName());
    }
}
