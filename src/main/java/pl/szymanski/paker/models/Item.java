package pl.szymanski.paker.models;

public class Item {
    private String name;
    public float width;
    public float depth;
    public float height;
    public float weight;
    public float value;

    public Item() {
    }

    public Item(String name, float width, float depth, float height, float weight, float value) {
        this.name = name;
        this.width = width;
        this.depth = depth;
        this.height = height;
        this.weight = weight;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getDepth() {
        return depth;
    }

    public void setDepth(float depth) {
        this.depth = depth;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

}