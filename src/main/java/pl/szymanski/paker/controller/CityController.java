package pl.szymanski.paker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.szymanski.paker.models.City;
import pl.szymanski.paker.models.Province;
import pl.szymanski.paker.payload.request.CityRequest;
import pl.szymanski.paker.payload.response.CityResponse;
import pl.szymanski.paker.payload.response.MessageResponse;
import pl.szymanski.paker.payload.response.NeighbourCityResponse;
import pl.szymanski.paker.repository.CityRepo;
import pl.szymanski.paker.repository.ProvinceRepo;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/city/")
public class CityController {

    @Autowired
    private CityRepo city_R;

    @Autowired
    private ProvinceRepo prov_R;

    /**
     * Metoda zwraca miasto o podanym ID
     *
     * @param id
     * @return  zwraca miasto
     */
    @GetMapping("/find")
    public ResponseEntity<?> getCityById(@RequestParam String id) {
        Optional<City> city = city_R.findById(id);
        if (city.isPresent())
            return ResponseEntity.ok(cityToCityResponse(city.get()));
        else
            return ResponseEntity.ok("");
    }

    /**
     * Metoda szukająca miasta po części jej nazwy
     *
     * @param name (String) nazwa lub jej część
     * @return lista pasujących miast
     */
    @GetMapping("/find_name")
    public ResponseEntity<List<CityResponse>> getCityByName(@RequestParam String name) {
        List<CityResponse> responseList = new ArrayList<CityResponse>();

        for (City c : city_R.findByNameRegex(name)) {
            responseList.add(cityToCityResponse(c));
        }
        return ResponseEntity.ok(responseList);
    }

    /**
     * Metoda szukająca miasta po kodzie pocztowym
     *
     * @param zipCode kod pocztowy
     * @return  zwraca liste miast z kodem pocztowym
     */
    @GetMapping("/find_zip")
    public ResponseEntity<List<CityResponse>> getCityByZipCode(@RequestParam String zipCode) {
        List<CityResponse> responseList = new ArrayList<CityResponse>();

        for (City c : city_R.findByZipCode(zipCode)) {
            responseList.add(cityToCityResponse(c));
        }
        return ResponseEntity.ok(responseList);
    }

    /**
     * Metoda szukająca miasta po województwie
     *
     * @param prov kod województwa
     * @return zwraca liste miast z danego województwa
     */
    @GetMapping("/find_prov")
    public ResponseEntity<List<CityResponse>> getCityByProvince(@RequestParam String prov) {
        List<CityResponse> responseList = new ArrayList<CityResponse>();

        Province province = prov_R.findByName(prov);

        for (City c : city_R.findByProvince(province)) {
            responseList.add(cityToCityResponse(c));
        }
        return ResponseEntity.ok(responseList);
    }

    /**
     * Metoda zwraca wszystkie miasta
     *
     * @return lista wszystkich miast
     */
    @GetMapping("/find_all")
    public ResponseEntity<List<CityResponse>> getCityAll() {
        List<CityResponse> responseList = new ArrayList<CityResponse>();

        for (City c : city_R.findAll()) {
            responseList.add(cityToCityResponse(c));
        }
        return ResponseEntity.ok(responseList);
    }

    /**
     * Metoda zwraca sąsiadów wybranego miasta
     *
     * @param id id miasta
     * @return
     */
    @GetMapping("/get_neighbours")
    public ResponseEntity<?> getCityNeighbours(@RequestParam String id) {
        Optional<City> cityOptional = city_R.findById(id);
        NeighbourCityResponse ncityResponse;
        City city;
        if (cityOptional.isPresent()) {
            city = cityOptional.get();
            ncityResponse = cityToNCityResponse(city);
            return ResponseEntity
                    .ok(ncityResponse);
        }
        return ResponseEntity.ok("");
    }

    /**
     * Metoda dodaje sąsiada do miasta
     * @param id
     * @param city_id
     * @param distance
     * @return
     */
    @GetMapping("/add_neighbour")
    public ResponseEntity<?> addNeighbour(@RequestParam String id, @RequestParam String city_id, @RequestParam double distance) {
        Optional<City> cityOptional = city_R.findById(id);
        Optional<City> neighbourOptional = city_R.findById(city_id);
        City city, neighbour;
        if (cityOptional.isPresent()) {
            city = cityOptional.get();
            if (neighbourOptional.isPresent()) {
                neighbour = neighbourOptional.get();

                Map<String,Double> map = city.getNeighbours();
                if (!map.containsKey(neighbour.getId())) {
                    Map<String,Double> map2 = neighbour.getNeighbours();
                    map.put(neighbour.getId(), distance);
                    city.setNeighbours(map);
                    map2.put(city.getId(),distance);
                    neighbour.setNeighbours(map2);

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

    /**
     * Metoda ładuje do bazy nowe miasto
     * @param newCity obiekt klasu {@link pl.szymanski.paker.payload.request.CityRequest}
     * @return zwraca ResponseEntity, w przypadku powodzenia obiekt klasy  {@link pl.szymanski.paker.payload.response.CityResponse},
     * w wypadku istnienia miasta zwraca {@link pl.szymanski.paker.payload.response.MessageResponse} BadRequest,
     * w wypadku niepoprawnego obiektu zwraca {@link pl.szymanski.paker.payload.response.MessageResponse}BadRequest
     */
    @PostMapping("/new")
    public ResponseEntity<?> addCity(@Valid @RequestBody CityRequest newCity){
        if(newCity.isValid()){


            City city = cityRequestToCity(newCity);
            if(city_R.existsByZipCode(city.getZipCode())){
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

    @DeleteMapping("/del")
    public ResponseEntity<?> delCity(@RequestParam String id){
        if(city_R.existsById(id)){
            Optional<City> cityOptional = city_R.findById(id);
            if(cityOptional.isPresent()) {
                City city = cityOptional.get();
                Map<String,Double> map = city.getNeighbours();
                if(!map.isEmpty()){
                    Optional<City> toDelNeighbour;
                    City tmp;
                    for(Map.Entry<String,Double> entry: map.entrySet()){
                         toDelNeighbour = city_R.findById(entry.getKey());
                         if(toDelNeighbour.isPresent()){
                             tmp = toDelNeighbour.get();
                             tmp.getNeighbours().remove(id);
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

    /**
     * Metoda zamieniająca obiekt klasy {@link pl.szymanski.paker.payload.request.CityRequest} na obiekty klasy {@link pl.szymanski.paker.models.City}
     *
     * @param newCity okiekt klasy {@link pl.szymanski.paker.payload.request.CityRequest}
     * @return  obiekty klasy {@link pl.szymanski.paker.models.City}
     */
    private City cityRequestToCity(CityRequest newCity){
        return new City(newCity.getName(), newCity.getZipCode(), prov_R.findByName(newCity.getProvince()));
    }

    /**
     * Metoda zamieniająca obiekt klasy {@link pl.szymanski.paker.models.City} na obiekty klasy {@link pl.szymanski.paker.payload.response.NeighbourCityResponse}
     *
     * @param city okiekt klasy {@link pl.szymanski.paker.models.City}
     * @return  obiekty klasy {@link pl.szymanski.paker.payload.response.NeighbourCityResponse}
     */
    private NeighbourCityResponse cityToNCityResponse(City city) {
        Map<String, Double> map = new HashMap<String, Double>();
        for (Map.Entry<String,Double> c : city.getNeighbours().entrySet()) {
            Optional<City> cityOptional = city_R.findById(c.getKey());
            City citya;
            if (cityOptional.isPresent()) {
                citya = cityOptional.get();
                map.put(citya.getId(), c.getValue());
            }
        }
        return new NeighbourCityResponse(city.getId(), map);
    }

    /**
     * Metoda zamieniająca obiekt klasy {@link pl.szymanski.paker.models.City} na obiekty klasy {@link pl.szymanski.paker.payload.response.CityResponse}
     *
     * @param city obiekt klasy {@link pl.szymanski.paker.models.City}
     * @return obiekty klasy {@link pl.szymanski.paker.payload.response.CityResponse}
     */
    private CityResponse cityToCityResponse(City city) {
        return new CityResponse(city.getId(), city.getName(), city.getProvince().getName().name(), city.getZipCode());
    }

}
