package pl.szymanski.paker.algorithm.DijkstraPath;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class Dijkstra {
    public static Graf obliczNajkrotszaSciezkeZZrodla(Graf graf, Wezel zrodlo) {
        zrodlo.setDystans(0.0);

        Set<Wezel> ustalony = new HashSet<>();
        Set<Wezel> nieustalony = new HashSet<>();

        nieustalony.add(zrodlo);
        while (nieustalony.size() != 0) {
            Wezel sprawdzany = znajdzNajblizszyWezel(nieustalony);
            nieustalony.remove(sprawdzany);
            for (Map.Entry<Wezel, Double> parySasiadow : sprawdzany.getSasiedzi().entrySet()) {
                Wezel sasiedniWezel = parySasiadow.getKey();
                Double odlegloscSasiada = parySasiadow.getValue();
                if (!ustalony.contains(sasiedniWezel)) {
                    obliczMinimalnyDystans(sasiedniWezel, odlegloscSasiada, sprawdzany);
                    nieustalony.add(sasiedniWezel);

                }
            }
            ustalony.add(sprawdzany);
        }
        return graf;
    }

    private static void obliczMinimalnyDystans(Wezel sasiedniWezel, Double odlegloscSasiada, Wezel sprawdzany) {
        Double odlegloscOdZrodla = sprawdzany.getDystans();
        if(odlegloscOdZrodla + odlegloscSasiada < sasiedniWezel.getDystans()){
            sasiedniWezel.setDystans(odlegloscOdZrodla + odlegloscSasiada);
            LinkedList<Wezel> sciezka = new LinkedList<>(sprawdzany.getSciezka());
            sciezka.add(sprawdzany);
            sasiedniWezel.setSciezka(sciezka);
        }
    }

    private static Wezel znajdzNajblizszyWezel(Set<Wezel> nieustalony) {
        Wezel najblizszyWezel = null;
        Double najmniejszyDystans = Double.MAX_VALUE;
        for (Wezel wezel : nieustalony) {
            Double dystans = wezel.getDystans();
            if (dystans < najmniejszyDystans){
                najmniejszyDystans = dystans;
                najblizszyWezel = wezel;
            }
        }
        return najblizszyWezel;
    }
}
