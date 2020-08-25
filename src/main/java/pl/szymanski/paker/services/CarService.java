package pl.szymanski.paker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.szymanski.paker.models.Car;
import pl.szymanski.paker.models.CarType;
import pl.szymanski.paker.payload.request.CarRequest;
import pl.szymanski.paker.payload.response.CarResponse;
import pl.szymanski.paker.payload.response.MessageResponse;
import pl.szymanski.paker.repository.CarRepo;
import pl.szymanski.paker.repository.CarTypeRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    @Autowired
    private CarRepo car_R;
    @Autowired
    private CarTypeRepo carType_R;

    public ResponseEntity<?> findAll() {
        List<CarResponse> responseList = new ArrayList<>();

        for (Car c : car_R.findAll()) {
            responseList.add(carToCarResponse(c));
        }
        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<?> findByID(String id) {
        Optional<Car> carOptional = car_R.findById(id);
        if (carOptional.isPresent()) {
            Car car = carOptional.get();
            CarResponse response = carToCarResponse(car);
            return ResponseEntity
                    .ok(response);
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not Found"));
    }

    public ResponseEntity<?> findByBrand(String brand) {
        List<CarResponse> responseList = new ArrayList<>();

        for (Car c : car_R.findByBrandRegex(brand)) {
            responseList.add(carToCarResponse(c));
        }
        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<?> findByModel(String model) {
        List<CarResponse> responseList = new ArrayList<>();

        for (Car c : car_R.findByModelRegex(model)) {
            responseList.add(carToCarResponse(c));
        }
        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<?> findByPlate(String plate) {
        List<CarResponse> responseList = new ArrayList<>();

        for (Car c : car_R.findByLicensePlateRegex(plate)) {
            responseList.add(carToCarResponse(c));
        }
        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<?> findByType(String type) {
        List<CarResponse> responseList = new ArrayList<>();
        CarType carType = carType_R.findByType(type);

        for (Car c : car_R.findByType(carType)) {
            responseList.add(carToCarResponse(c));
        }
        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<?> addNew(CarRequest newCar) {
        if (newCar.idValid()) {
            Car car = carRequestToCar(newCar);
            if (car_R.existsByLicensePlate(car.getLicensePlate())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Car exists"));
            }
            car_R.save(car);
            return ResponseEntity.ok(carToCarResponse(car));
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not valid request"));
    }

    public ResponseEntity<?> del(String id) {
        if (car_R.existsById(id)) {
            car_R.deleteById(id);
            return ResponseEntity
                    .ok(new MessageResponse("Deleted"));
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not found"));
    }

    public ResponseEntity<?> update(CarRequest updateCar) {
        if (updateCar.idValid()) {
            Car car = carRequestToCar(updateCar);
            Optional<Car> carOptional = car_R.findByLicensePlate(car.getLicensePlate());
            if (carOptional.isPresent()) {
                Car toUpdateCar = carOptional.get();
                toUpdateCar.setBrand(car.getBrand());
                toUpdateCar.setModel(car.getModel());
                toUpdateCar.setType(car.getType());
                car_R.save(toUpdateCar);
                return ResponseEntity.ok(carToCarResponse(toUpdateCar));
            }
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Not found"));
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not valid request"));
    }

    private CarResponse carToCarResponse(Car car) {
        return new CarResponse(car.getId(), car.getBrand(), car.getModel(), car.getLicensePlate(), car.getType().getType().name(),car.isInRepair());
    }

    private Car carRequestToCar(CarRequest carRequest) {
        Car car = new Car();
        car.setBrand(carRequest.getBrand());
        car.setModel(carRequest.getModel());
        car.setLicensePlate(carRequest.getLicensePlate());
        CarType carType = carType_R.findByType(carRequest.getCarType());
        car.setType(carType);
        return car;
    }
}
