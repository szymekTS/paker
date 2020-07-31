package pl.szymanski.paker.models.enums;

public enum ECartype {
    TYPE_SMALL(1.0f, 1.0f, 1.0f),
    TYPE_MID(1.5f, 2.0f, 1.5f),
    TYPE_BIG(2.0f, 5.0f ,1.5f);

    public final float width;
    public final float depth;
    public final float height;

    private ECartype(float width, float depth, float height) {
        this.depth = depth;
        this.height = height;
        this.width = width;
    }
}