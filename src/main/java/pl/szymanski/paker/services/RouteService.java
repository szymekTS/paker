package pl.szymanski.paker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import pl.szymanski.paker.models.City;
import pl.szymanski.paker.models.Route;
import pl.szymanski.paker.payload.response.MessageResponse;
import pl.szymanski.paker.payload.response.RouteResponse;
import pl.szymanski.paker.repository.RouteRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RouteService {
    @Autowired
    private RouteRepo route_R;

    public ResponseEntity<?> findByID(String id) {
        Optional<Route> routeOptional = route_R.findById(id);
        if (routeOptional.isPresent()) {
            Route route = routeOptional.get();
            RouteResponse response = routeToRouteResponse(route);
            return ResponseEntity
                    .ok(response);
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not Found"));
    }

    private RouteResponse routeToRouteResponse(Route route) {
        RouteResponse response = new RouteResponse();

        response.setId(route.getId());
        response.setDistance(route.getDistance());
        List<String> tmp = new ArrayList<String>();
        for(City city : route.getRoute()){
            tmp.add(city.getId());
        }
        response.setRoute(tmp);

        return response;
    }
}
