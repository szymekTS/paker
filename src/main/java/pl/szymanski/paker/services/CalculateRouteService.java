package pl.szymanski.paker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.szymanski.paker.algorithm.DijkstraPath.Dijkstra;
import pl.szymanski.paker.algorithm.DijkstraPath.Graf;
import pl.szymanski.paker.algorithm.DijkstraPath.Wezel;
import pl.szymanski.paker.models.City;
import pl.szymanski.paker.models.Route;
import pl.szymanski.paker.repository.CityRepo;

import java.util.*;

@Service
public class CalculateRouteService {
    @Autowired
    CityRepo city_R;
    public Route calculateRoute(City origin,City destiny){
        Route route = new Route();
        if(city_R.existsById(origin.getId())&&city_R.existsById(destiny.getId())) {
            List<City> cityList = city_R.findAll();
            Map<String, Wezel> wezelMap = new HashMap<>();

            for (City city : cityList) {
                wezelMap.put(city.getId(), new Wezel(city));
            }

            for (Map.Entry<String, Wezel> wezel : wezelMap.entrySet()) {

                for (Map.Entry<String, Double> element : wezel.getValue().getMiasto().getNeighbours().entrySet()) {
                    wezel.getValue().dodajSasiada(wezelMap.get(element.getKey()), element.getValue());
                }
            }
            Graf graf = new Graf();

            for (Map.Entry<String, Wezel> wezel : wezelMap.entrySet()) {
                graf.dodajWezel(wezel.getValue());
            }

            Graf resultGraf = Dijkstra.obliczNajkrotszaSciezkeZZrodla(graf, wezelMap.get(origin.getId()));

            List<City> path = new ArrayList<>();
            for (Wezel element : wezelMap.get(destiny.getId()).getSciezka()) {
                path.add(element.getMiasto());
            }
            route.setRoute(path);
            route.setDistance(wezelMap.get(destiny.getId()).getDystans());
        }
        return route;}
    }

