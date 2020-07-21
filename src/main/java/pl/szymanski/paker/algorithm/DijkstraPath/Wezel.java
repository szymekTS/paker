package pl.szymanski.paker.algorithm.DijkstraPath;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Wezel {
    private int id;
    private String nazwa;
    private Double dystans = Double.MAX_VALUE;
    private List<Wezel> sciezka = new LinkedList<>();
    private Map<Wezel, Double> sasiedzi = new HashMap<>();

    public void dodajSasiada(Wezel sasiad, Double odleglosc) {
        this.sasiedzi.put(sasiad, odleglosc);
    }

    public Wezel(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
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
        return nazwa;
    }
}
