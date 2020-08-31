package pl.szymanski.paker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.szymanski.paker.models.*;
import pl.szymanski.paker.models.enums.EStatus;
import pl.szymanski.paker.payload.request.OrderAddStatus;
import pl.szymanski.paker.payload.request.OrderNewReq;
import pl.szymanski.paker.payload.request.OrderRequest;
import pl.szymanski.paker.payload.response.MessageResponse;
import pl.szymanski.paker.payload.response.OrderListItem;
import pl.szymanski.paker.payload.response.OrderResponse;
import pl.szymanski.paker.payload.response.StatusList;
import pl.szymanski.paker.repository.*;

import java.io.CharArrayReader;
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
    private UserRepo user_R;
    @Autowired
    private CalculateRouteService pathFinder;


    public ResponseEntity<?> findAll() {
        List<OrderListItem> responseList = new ArrayList<>();

        for (Order u : order_R.findAll()) {
            responseList.add(orderToOrderListItem(u));
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

        for (Order u : order_R.findByStatusAndLocalization(status, location)) {
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


    public ResponseEntity<?> addNew(OrderNewReq orderRequest) {
        Order newOrder = orderNewRequestToOrder(orderRequest);
        return ResponseEntity.ok(new MessageResponse("Dodano"));
    }


    public ResponseEntity<?> del(String id) {
        Optional<Order> optionalOrder = order_R.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order
        }
        return ResponseEntity
                .ok(new MessageResponse("Deleted"));
    }
        return ResponseEntity
                .badRequest()
                .

    body(new MessageResponse("Not found"));
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

    private OrderListItem orderToOrderListItem(Order order) {
        OrderListItem listItem = new OrderListItem();
        listItem.setId(order.getId());
        listItem.setCar(order.getCar().getLicensePlate());
        listItem.setCustomer(order.getCustomer().getEmail());
        listItem.setDriver(order.getDriver().getUsername());
        listItem.setOrigin(order.getOrigin().getName());
        listItem.setDestiny(order.getDestiny().getName());
        listItem.setLastStatus(order.getLastStatus().name());

        return listItem;
    }

    private Order orderNewRequestToOrder(OrderNewReq orderRequest) {
        Order newOrder = new Order();
        Optional<Car> optionalCar = car_R.findById(orderRequest.getCar());
        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();
            newOrder.setCar(car);
            car.setFree(false);
            car_R.save(car);
        }

        Optional<User> optionalDriver = user_R.findById(orderRequest.getDriver());
        if (optionalDriver.isPresent()) {
            User driver = optionalDriver.get();
            newOrder.setDriver(driver);
            driver.setFree(false);
            user_R.save(driver);

        }

        Cargo newCargo = new Cargo(orderRequest.getCargo());
        cargo_R.save(newCargo);
        newOrder.setCargo(newCargo);

        Optional<Customer> optionalCustomer = customer_R.findById(orderRequest.getCustomer());
        if (optionalCustomer.isPresent()) {
            newOrder.setCustomer(optionalCustomer.get());
        }

        Optional<City> optionalOrigin = city_R.findById(orderRequest.getOrigin());
        if (optionalOrigin.isPresent()) {
            newOrder.setOrigin(optionalOrigin.get());
            newOrder.setLocalization(optionalOrigin.get());
        }

        Optional<City> optionalDestiny = city_R.findById(orderRequest.getDestiny());
        if (optionalDestiny.isPresent()) {
            newOrder.setDestiny(optionalDestiny.get());
        }
        Status newStatus = new Status();
        Optional<User> optionalCreator = user_R.findById(orderRequest.getCreator());
        if (optionalCreator.isPresent()) {
            newStatus.setWorker(optionalCreator.get());
            newStatus.setStatusCode(EStatus.STATUS_ACCEPTED);
            newStatus.setComments(optionalCreator.get().getUsername() + " - Stworzono zamówienie");
            status_R.save(newStatus);
        }
        List<Status> tmpStatus = newOrder.getStatusList();
        tmpStatus.add(newStatus);
        newOrder.setStatusList(tmpStatus);
        newOrder.setLastStatus(newStatus.getStatusCode());


        Route newRoute = pathFinder.calculateRoute(newOrder.getOrigin(), newOrder.getDestiny());
        route_R.save(newRoute);
        newOrder.setRoute(newRoute);
        order_R.save(newOrder);


        return newOrder;
    }


    public ResponseEntity<?> addStatus(OrderAddStatus orderStatus) {
        Optional<Order> optionalOrder = order_R.findById(orderStatus.getOrderId());
        if (optionalOrder.isPresent()) {
            Optional<User> optionalUser = user_R.findById(orderStatus.getWorkerID());
            if (optionalUser.isPresent()) {
                Order order = optionalOrder.get();
                User user = optionalUser.get();

                Status newStatus = new Status();
                newStatus.setStatusCode(orderStatus.getStatus());
                newStatus.setComments(user.getUsername() + " - " + orderStatus.getComment());
                newStatus.setWorker(user);

                status_R.save(newStatus);

                List<Status> statusList = order.getStatusList();
                statusList.add(newStatus);
                order.setStatusList(statusList);
                order.setLastStatus(orderStatus.getStatus());

                if (order.getLastStatus() == EStatus.STATUS_DELETED || order.getLastStatus() == EStatus.STATUS_DELIVERED) {
                    Car car = order.getCar();
                    car.setFree(true);
                    car_R.save(car);
                    User driver = order.getDriver();
                    driver.setFree(true);
                    user_R.save(driver);
                }

                order_R.save(order);
                return ResponseEntity
                        .ok()
                        .body(new MessageResponse("Status changed"));
            }
            return ResponseEntity
                    .ok()
                    .body(new MessageResponse("Worker not found"));
        }
        return ResponseEntity
                .ok()
                .body(new MessageResponse("Order not found"));
    }

    public ResponseEntity<?> getStatusList(String id) {
        Optional<Order> optionalOrder = order_R.findById(id);
        if (optionalOrder.isPresent()) {
            StatusList list = new StatusList();
            list.setList(optionalOrder.get().getStatusList());
            return ResponseEntity
                    .ok()
                    .body(list);
        }
        return ResponseEntity
                .ok()
                .body(new MessageResponse("Order not found"));
    }
}
