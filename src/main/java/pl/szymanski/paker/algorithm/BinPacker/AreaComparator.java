package pl.szymanski.paker.algorithm.BinPacker;

import pl.szymanski.paker.models.Item;

import java.util.Comparator;

class AreaComparator implements Comparator<Item> {
    @Override
    public int compare(Item o1, Item o2) {
        Float area1 = o1.width* o1.depth;
        Float area2 = o2.width* o2.depth;
        return area1.compareTo(area2);
    }
}