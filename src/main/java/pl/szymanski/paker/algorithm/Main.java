package pl.szymanski.paker.algorithm;

import pl.szymanski.paker.algorithm.DijkstraPath.Dijkstra;
import pl.szymanski.paker.algorithm.DijkstraPath.Graf;
import pl.szymanski.paker.algorithm.DijkstraPath.Wezel;

public class Main {

    public static void main(String[] args) {
        Wezel nodeA = new Wezel("A");
        Wezel nodeB = new Wezel("B");
        Wezel nodeC = new Wezel("C");
        Wezel nodeD = new Wezel("D");
        Wezel nodeE = new Wezel("E");
        Wezel nodeF = new Wezel("F");

        nodeA.dodajSasiada(nodeB, 10.0);
        nodeA.dodajSasiada(nodeC, 15.0);

        nodeB.dodajSasiada(nodeA, 10.0);
        nodeB.dodajSasiada(nodeD, 12.0);
        nodeB.dodajSasiada(nodeF, 15.0);

        nodeC.dodajSasiada(nodeE, 10.0);
        nodeC.dodajSasiada(nodeA, 15.0);

        nodeD.dodajSasiada(nodeB, 12.0);
        nodeD.dodajSasiada(nodeE, 2.0);
        nodeD.dodajSasiada(nodeF, 1.0);

        nodeF.dodajSasiada(nodeB, 15.0);
        nodeF.dodajSasiada(nodeD, 1.0);
        nodeF.dodajSasiada(nodeE, 5.0);

        nodeE.dodajSasiada(nodeC, 10.0);
        nodeE.dodajSasiada(nodeD, 2.0);
        nodeE.dodajSasiada(nodeF, 5.0);

        Graf graf = new Graf();

        graf.dodajWezel(nodeA);
        graf.dodajWezel(nodeB);
        graf.dodajWezel(nodeC);
        graf.dodajWezel(nodeD);
        graf.dodajWezel(nodeE);
        graf.dodajWezel(nodeF);

        graf = Dijkstra.obliczNajkrotszaSciezkeZZrodla(graf, nodeF);
        System.out.println(graf.getWezly());
        System.out.println(nodeC.getDystans());
        System.out.println(nodeC.getSciezka());
    }
}
