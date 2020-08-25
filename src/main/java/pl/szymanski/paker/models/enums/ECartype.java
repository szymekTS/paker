package pl.szymanski.paker.models.enums;

public enum ECarType {
    TYPE_SMALL(1.0f, 1.0f, 1.0f, 300.0f),
    TYPE_MID(1.5f, 2.0f, 1.5f, 1000.0f),
    TYPE_BIG(2.0f, 5.0f, 1.5f, 1500.0f),
    TYPE_SPECIAL(3.0f, 10.0f, 2.5f, 10000.0f);

    public final float width;
    public final float depth;
    public final float height;
    public final float weight;

    private ECarType(float width, float depth, float height, float weight) {
        this.depth = depth;
        this.height = height;
        this.width = width;
        this.weight = weight;
    }
}