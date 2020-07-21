package pl.szymanski.paker.algorithm.DijkstraPath;

import java.util.HashSet;

public class Graf {
    private HashSet<Wezel> nodes = new HashSet<>();

    public void dodajWezel(Wezel node){
        nodes.add(node);
    }

    public HashSet<Wezel> getWezly() {
        return nodes;
    }

    public void setWezly(HashSet<Wezel> nodes) {
        this.nodes = nodes;
    }
}
