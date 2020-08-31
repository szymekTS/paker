package pl.szymanski.paker.models.enums;

public enum ECarType {
    TYPE_SMALL(100.0f, 100.0f, 100.0f, 300.0f),
    TYPE_MID(150.0f, 200.0f, 150.0f, 1000.0f),
    TYPE_BIG(200.0f, 500.0f, 200.0f, 1500.0f),
    TYPE_SPECIAL(300.0f, 1000.0f, 250.0f, 10000.0f);

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