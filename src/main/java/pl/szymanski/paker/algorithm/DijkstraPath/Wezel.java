package pl.szymanski.paker.algorithm.DijkstraPath;

import pl.szymanski.paker.models.City;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Wezel {
    private int id;
    private City miasto;
    private Double dystans = Double.MAX_VALUE;
    private List<Wezel> sciezka = new LinkedList<>();
    private Map<Wezel, Double> sasiedzi = new HashMap<>();

    public void dodajSasiada(Wezel sasiad, Double odleglosc) {
        this.sasiedzi.put(sasiad, odleglosc);
    }

    public Wezel(City nazwa) {
        this.miasto = nazwa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public City getMiasto() {
        return miasto;
    }

    public void setMiasto(City miasto) {
        this.miasto = miasto;
    }

    public List<Wezel> getSciezka() {
        return sciezka;
    }

    public void setSciezka(List<Wezel> sciezka) {
        this.sciezka = sciezka;
    }

    public Map<Wezel, Double> getSasiedzi() {
        return sasiedzi;
    }

    public void setSasiedzi(Map<Wezel, Double> sasiedzi) {
        this.sasiedzi = sasiedzi;
    }

    public Double getDystans() {
        return dystans;
    }

    public void setDystans(Double dystans) {
        this.dystans = dystans;
    }

    @Override
    public String toString() {
        return miasto.getId();
    }
}
