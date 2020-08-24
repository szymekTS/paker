package pl.szymanski.paker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.szymanski.paker.models.City;
import pl.szymanski.paker.models.Province;
import pl.szymanski.paker.payload.request.CityAddNeighbour;
import pl.szymanski.paker.payload.request.CityRequest;
import pl.szymanski.paker.payload.response.CityResponse;
import pl.szymanski.paker.payload.response.MessageResponse;
import pl.szymanski.paker.payload.response.NeighbourCityResponse;
import pl.szymanski.paker.repository.CityRepo;
import pl.szymanski.paker.repository.ProvinceRepo;

import java.util.*;

@Service
public class CityService {

    @Autowired
    private CityRepo city_R;

    @Autowired
    private ProvinceRepo prov_R;

    public ResponseEntity<?> findByID(String id) {
        Optional<City> city = city_R.findById(id);
        if (city.isPresent())
            return ResponseEntity.ok(cityToCityResponse(city.get()));
        else
            return ResponseEntity.ok("");
    }

    public ResponseEntity<?> findByName(String name) {
        List<CityResponse> responseList = new ArrayList<>();

        for (City c : city_R.findByNameRegex(name)) {
            responseList.add(cityToCityResponse(c));
        }
        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<?> findByZipCode(String zipCode) {
        List<CityResponse> responseList = new ArrayList<>();

        for (City c : city_R.findByZipCode(zipCode)) {
            responseList.add(cityToCityResponse(c));
        }
        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<?> findByProvince(String prov) {
        List<CityResponse> responseList = new ArrayList<>();

        Province province = prov_R.findByName(prov);

        for (City c : city_R.findByProvince(province)) {
            responseList.add(cityToCityResponse(c));
        }
        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<?> findAll() {
        List<CityResponse> responseList = new ArrayList<>();

        for (City c : city_R.findAll()) {
            responseList.add(cityToCityResponse(c));
        }
        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<?> findNeighbours(String id) {
        Optional<City> cityOptional = city_R.findById(id);
        @SuppressWarnings("SpellCheckingInspection") NeighbourCityResponse ncityResponse;
        City city;
        if (cityOptional.isPresent()) {
            city = cityOptional.get();
            System.out.println(city);
            ncityResponse = cityToNCityResponse(city);
            return ResponseEntity
                    .ok(ncityResponse);
        }
        return ResponseEntity.ok("");
    }

    public ResponseEntity<?> addNeighbour(CityAddNeighbour cityAddNeighbour) {
        Optional<City> cityOptional = city_R.findById(cityAddNeighbour.getId());
        Optional<City> neighbourOptional = city_R.findById(cityAddNeighbour.getCity_id());
        City city, neighbour;
        if (cityOptional.isPresent()) {
            city = cityOptional.get();
            if (neighbourOptional.isPresent()) {
                neighbour = neighbourOptional.get();

                if(city.getId().equals(neighbour.getId())){
                    return ResponseEntity
                            .badRequest()
                            .body(new MessageResponse("Can't be own neighbour"));
                }
                Map<String, Double> map = city.getNeighbours();
                if (!map.containsKey(neighbour.getId())) {
                    Map<String, Double> map2 = neighbour.getNeighbours();
                    map.put(neighbour.getId(), cityAddNeighbour.getDistance());
                    city.setNeighbours(map);
                    map2.put(city.getId(), cityAddNeighbour.getDistance());
                    neighbour.setNeighbours(map2);

                    System.out.println("City added");
                    city_R.save(neighbour);
                    city_R.save(city);
                    return ResponseEntity.ok("added");
                }
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Neighbour exist"));
            }
        }
        return ResponseEntity.ok("");
    }

    public ResponseEntity<?> addCity(CityRequest newCity) {
        if (newCity.isValid()) {

            System.out.println(newCity.getName());
            System.out.println(newCity.getProvince());
            System.out.println(newCity.getZipCode());

            City city = cityRequestToCity(newCity);
            if (city_R.existsByZipCode(city.getZipCode())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("City exist"));
            }

            city_R.save(city);

            return ResponseEntity.ok(cityToCityResponse(city));
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not valid request"));
    }

    public ResponseEntity<?> delCity(String id) {
        if (city_R.existsById(id)) {
            Optional<City> cityOptional = city_R.findById(id);
            if (cityOptional.isPresent()) {
                City city = cityOptional.get();
                Map<String, Double> map = city.getNeighbours();
                if (!map.isEmpty()) {
                    Optional<City> toDelNeighbour;
                    City tmp;
                    for (Map.Entry<String, Double> entry : map.entrySet()) {
                        toDelNeighbour = city_R.findById(entry.getKey());
                        if (toDelNeighbour.isPresent()) {
                            tmp = toDelNeighbour.get();
                            tmp.getNeighbours().remove(id);
                            city_R.save(tmp);
                        }
                    }
                }
                city_R.deleteById(id);
                return ResponseEntity
                        .ok(new MessageResponse("Deleted"));
            }
            return ResponseEntity
                    .ok(new MessageResponse("Error"));
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not found"));
    }

    private City cityRequestToCity(CityRequest newCity) {
        return new City(newCity.getName(), newCity.getZipCode(), prov_R.findByName(newCity.getProvince()));
    }

    private NeighbourCityResponse cityToNCityResponse(City city) {
        Map<String, Double> map = new HashMap<>();
        Map<String,String> names = new HashMap<>();
        for (Map.Entry<String, Double> c : city.getNeighbours().entrySet()) {
            Optional<City> cityOptional = city_R.findById(c.getKey());
            @SuppressWarnings("SpellCheckingInspection") City citya;
            if (cityOptional.isPresent()) {
                citya = cityOptional.get();
                map.put(citya.getId(), c.getValue());
                names.put(citya.getId(), citya.getName());
            }
        }
        return new NeighbourCityResponse(city.getId(), map, names);
    }

    private CityResponse cityToCityResponse(City city) {
        CityResponse response = new CityResponse();
        response.setId(city.getId());
        response.setName(city.getName());
        response.setZipCode(city.getZipCode());
        response.setProvinceId(city.getProvince().getId());
        response.setProvince(city.getProvince().getName().name);
        return response;
    }
}
