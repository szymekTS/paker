package pl.szymanski.paker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.szymanski.paker.models.*;
import pl.szymanski.paker.payload.request.OrderRequest;
import pl.szymanski.paker.payload.response.MessageResponse;
import pl.szymanski.paker.payload.response.OrderResponse;
import pl.szymanski.paker.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepo order_R;
    @Autowired
    private CargoRepo cargo_R;
    @Autowired
    private CarRepo car_R;
    @Autowired
    private CustomerRepo customer_R;
    @Autowired
    private CityRepo city_R;
    @Autowired
    private StatusRepo status_R;
    @Autowired
    private RouteRepo route_R;
    @Autowired
    private CalculateRouteService pathFinder;

    @Autowired
    private OrderRepoImpl orderRepo;


    public ResponseEntity<?> findAll() {
        List<OrderResponse> responseList = new ArrayList<>();

        for (Order u : order_R.findAll()) {
            responseList.add(orderToOrderResponse(u));
        }
        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<?> findByID(String id) {
        Optional<Order> optionalOrder = order_R.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            OrderResponse response = orderToOrderResponse(order);
            return ResponseEntity
                    .ok(response);
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not Found"));
    }

    public ResponseEntity<?> findByCar(String id) {
        List<OrderResponse> responseList = new ArrayList<>();
        Optional<Car> carOptional = car_R.findById(id);
        if (carOptional.isPresent()) {
            Car car = carOptional.get();
            for (Order order : order_R.findByCar(car)) {
                responseList.add(orderToOrderResponse(order));
            }
            return ResponseEntity.ok(responseList);
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not Found"));
    }

    public ResponseEntity<?> findByCargoValue(float min, float max) {
        List<OrderResponse> responseList = new ArrayList<>();
        List<Cargo> cargoList = cargo_R.findByValueBetween(min, max);
        Optional<Order> optionalOrder;
        for (Cargo cargo : cargoList) {
            optionalOrder = order_R.findByCargo(cargo);
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                responseList.add(orderToOrderResponse(order));
            }
        }
        return ResponseEntity
                .ok(responseList);
    }

    public ResponseEntity<?> findByCargoWeight(float min, float max) {
        List<OrderResponse> responseList = new ArrayList<>();
        List<Cargo> cargoList = cargo_R.findByWeightBetween(min, max);
        Optional<Order> optionalOrder;
        for (Cargo cargo : cargoList) {
            optionalOrder = order_R.findByCargo(cargo);
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                responseList.add(orderToOrderResponse(order));
            }
        }
        return ResponseEntity
                .ok(responseList);
    }

    public ResponseEntity<?> findByCustomer(String id) {

        List<OrderResponse> responseList = new ArrayList<>();
        Optional<Customer> customerOptional = customer_R.findById(id);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            for (Order order : order_R.findByCustomer(customer)) {
                responseList.add(orderToOrderResponse(order));
            }
            return ResponseEntity.ok(responseList);
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not Found"));
    }

    public ResponseEntity<?> findByStatus(String status) {
        List<OrderResponse> responseList = new ArrayList<>();

        for (Order u : order_R.findByLastStatus(status)) {
            responseList.add(orderToOrderResponse(u));
        }
        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<?> findByStatusAndLocalization(String status, String location) {
        List<OrderResponse> responseList = new ArrayList<>();

        for (Order u : orderRepo.findByStatusAndLocalization(status, location)) {
            responseList.add(orderToOrderResponse(u));
        }
        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<?> findByLocation(String localization) {
        List<OrderResponse> responseList = new ArrayList<>();

        for (Order u : order_R.findByLocalization(localization)) {
            responseList.add(orderToOrderResponse(u));
        }
        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<?> findByOrigin(String cityId) {
        List<OrderResponse> responseList = new ArrayList<>();
        Optional<City> cityOptional = city_R.findById(cityId);
        if (cityOptional.isPresent()) {
            City city = cityOptional.get();
            for (Order order : order_R.findByOrigin(city)) {
                responseList.add(orderToOrderResponse(order));
            }
            return ResponseEntity.ok(responseList);
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not Found"));
    }

    public ResponseEntity<?> findByDestiny(String cityId) {
        List<OrderResponse> responseList = new ArrayList<>();
        Optional<City> cityOptional = city_R.findById(cityId);
        if (cityOptional.isPresent()) {
            City city = cityOptional.get();
            for (Order order : order_R.findByDestiny(city)) {
                responseList.add(orderToOrderResponse(order));
            }
            return ResponseEntity.ok(responseList);
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not Found"));
    }

    public ResponseEntity<?> update(OrderRequest orderRequest) {
        Optional<Order> optionalOrder = order_R.findById(orderRequest.getId());
        if (optionalOrder.isPresent()) {
            Order orderDB = optionalOrder.get();
            orderDB.setLastStatus(orderRequest.getLastStatus());
            Optional<Status> statusOptional;
            List<Status> statusList = new ArrayList<>();
            for (String id : orderRequest.getStatusList()) {
                statusOptional = status_R.findById(id);
                if (statusOptional.isPresent())
                    statusList.add(statusOptional.get());
            }
            orderDB.setStatusList(statusList);
            order_R.save(orderDB);
            return ResponseEntity.ok(orderToOrderResponse(orderDB));
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not Found"));
    }


    public ResponseEntity<?> addNew(OrderRequest orderRequest) {
        Order newOrder = orderRequestToOrder(orderRequest);
        if (order_R.existsById(newOrder.getId())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Order exists"));
        }
        Route newRoute = pathFinder.calculateRoute(newOrder.getOrigin(), newOrder.getDestiny());
        route_R.save(newRoute);
        newOrder.setRoute(newRoute);
        order_R.save(newOrder);
        return ResponseEntity.ok(orderToOrderResponse(newOrder));
    }

    public ResponseEntity<?> del(String id) {
        if (order_R.existsById(id)) {
            Optional<Order> optionalOrder = order_R.findById(id);
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                for (Status status : order.getStatusList()) {
                    status_R.deleteById(status.getId());
                }
                route_R.deleteById(order.getRoute().getId());
                order_R.deleteById(id);
            }
            return ResponseEntity
                    .ok(new MessageResponse("Deleted"));
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not found"));
    }

    private OrderResponse orderToOrderResponse(Order order) {
        OrderResponse response = new OrderResponse();

        response.setId(order.getId());
        response.setCar(order.getCar().getId());
        response.setCargo(order.getCargo().getId());
        response.setCustomer(order.getCustomer().getId());
        response.setLastStatus(order.getLastStatus().toString());
        response.setLocalization(order.getLocalization().getId());
        response.setOrigin(order.getOrigin().getId());
        response.setDestiny(order.getDestiny().getId());
        response.setRoute(order.getRoute().getId());

        List<String> tmpList = new ArrayList<>();
        for (Status status : order.getStatusList()) {
            tmpList.add(status.getId());
        }
        response.setStatusList(tmpList);

        return response;
    }

    private Order orderRequestToOrder(OrderRequest request) {
        Order order = new Order();
        Optional<Car> carOptional = car_R.findById(request.getCar());
        if (carOptional.isPresent()) {
            order.setCar(carOptional.get());
        }
        Optional<Cargo> cargoOptional = cargo_R.findById(request.getCargo());
        if (cargoOptional.isPresent()) {
            order.setCargo(cargoOptional.get());
        }
        Optional<Customer> customerOptional = customer_R.findById(request.getCustomer());
        if (customerOptional.isPresent()) {
            order.setCustomer(customerOptional.get());
        }
        Optional<City> localizationOptional = city_R.findById(request.getLocalization());
        if (localizationOptional.isPresent()) {
            order.setOrigin(localizationOptional.get());
        }
        Optional<City> originOptional = city_R.findById(request.getOrigin());
        if (originOptional.isPresent()) {
            order.setOrigin(originOptional.get());
        }
        Optional<City> destinyOptional = city_R.findById(request.getDestiny());
        if (destinyOptional.isPresent()) {
            order.setOrigin(destinyOptional.get());
        }
        order.setLastStatus(request.getLastStatus());

        Optional<Status> statusOptional;
        List<Status> statusList = new ArrayList<>();
        for (String id : request.getStatusList()) {
            statusOptional = status_R.findById(id);
            if (statusOptional.isPresent())
                statusList.add(statusOptional.get());
        }
        order.setStatusList(statusList);


        return order;
    }


}
