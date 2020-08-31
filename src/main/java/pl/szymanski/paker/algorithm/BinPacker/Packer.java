package pl.szymanski.paker.algorithm.BinPacker;

import pl.szymanski.paker.models.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Packer {

    Node root = new Node();
    List<Node> nodeList;

    public Packer(float w, float h) {
        this.root.w = w;
        this.root.h = h;
        nodeList = new ArrayList<>();
    }

    public void loadItems(List<Item> itemList) {
        itemList.sort(new AreaComparator());
        for (Item item : itemList) {
            nodeList.add(new Node(item.width, item.depth));
        }
    }

    public boolean checkResult(){
        List<Node> tmp = new ArrayList<>();
        for(Node node : this.nodeList){
            if(node.fit != null){
                tmp.add(node);
            }
        }
        return this.nodeList.size()==tmp.size();
    }

    public void fit() {
        Node tmp;
        for (Node node : this.nodeList) {
            tmp = findNode(this.root, node.w, node.h);
            if (tmp != null) {
                node.fit = splitNode(tmp, node.w, node.h);
            }
        }
    }

    public Node findNode(Node root, float w, float h) {
        if (root.used) {
            Node right = findNode(root.right, w, h);
            if (right != null) {
                return right;
            } else {
                return findNode(root.down, w, h);
            }
        } else if ((w <= root.w) && (h <= root.h)) {
            return root;
        }
        return null;
    }

    public Node splitNode(Node node, float w, float h) {
        node.used = true;
        node.down = new Node(node.x, node.y + h, node.w, node.h - h);
        node.right = new Node(node.x + w, node.y, node.w - w, h);
        return node;
    }

}

class AreaComparator implements Comparator<Item> {
    @Override
    public int compare(Item o1, Item o2) {
        Float area1 = o1.width* o1.depth;
        Float area2 = o2.width* o2.depth;
        return area1.compareTo(area2);
    }
}
