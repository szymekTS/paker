package pl.szymanski.paker.algorithm.BinPacker;
class Node {
    public boolean root = false;
    public boolean used = false;
    public float x = 0;
    public float y = 0;
    public float w = 0;
    public float h = 0;
    public Node right = null;
    public Node down = null;
    public Node fit = null;

    public Node() {

    }

    public Node(float w, float h) {
        this.w = w;
        this.h = h;
    }

    public Node(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

}