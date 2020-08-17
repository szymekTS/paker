package pl.szymanski.paker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.szymanski.paker.models.Car;
import pl.szymanski.paker.models.Maintenance;
import pl.szymanski.paker.models.enums.ERepair;
import pl.szymanski.paker.payload.request.MaintenenceRequest;
import pl.szymanski.paker.payload.response.MaintenenceResponse;
import pl.szymanski.paker.payload.response.MessageResponse;
import pl.szymanski.paker.repository.CarRepo;
import pl.szymanski.paker.repository.MaintenanceRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceService {

    @Autowired
    MaintenanceRepo maintenance_R;
    @Autowired
    CarRepo car_R;

    public ResponseEntity<?> findAll() {
        List<MaintenenceResponse> responseList = new ArrayList<>();

        for (Maintenance m : maintenance_R.findAll()) {
            responseList.add(mainToMainResponse(m));
        }
        return ResponseEntity.ok(responseList);
    }


    public ResponseEntity<?> findById(String id) {
        Optional<Maintenance> maintenanceOptional = maintenance_R.findById(id);
        if (maintenanceOptional.isPresent()) {
            Maintenance maintenance = maintenanceOptional.get();
            MaintenenceResponse response = mainToMainResponse(maintenance);
            return ResponseEntity
                    .ok(response);
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not Found"));
    }

    public ResponseEntity<?> findByCar(String carId) {
        List<MaintenenceResponse> responseList = new ArrayList<>();
        Optional<Car> carOptional = car_R.findById(carId);
        if(carOptional.isPresent()){
            Car car = carOptional.get();
            for( Maintenance m : maintenance_R.findByCar(car)) {
                responseList.add(mainToMainResponse(m));
            }
            return ResponseEntity.ok(responseList);
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not Found"));
    }
    public ResponseEntity<?> findByStatus(String status) {
        List<MaintenenceResponse> responseList = new ArrayList<>();

        for (Maintenance m : maintenance_R.findByStatus(status)) {
            responseList.add(mainToMainResponse(m));
        }
        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<?> addMaintenance(MaintenenceRequest newMaintenance) {
        if (newMaintenance.isValid()) {
            Maintenance maintenance = mainRequestToMain(newMaintenance);
            if(maintenance.getCar().getId() == null){
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Car not exist"));
            }
            maintenance_R.save(maintenance);
            return ResponseEntity.ok(mainToMainResponse(maintenance));
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not valid request"));
    }



    public ResponseEntity<?> delMaintenance(String id) {
        if (maintenance_R.existsById(id)) {
            maintenance_R.deleteById(id);
            return ResponseEntity
                    .ok(new MessageResponse("Deleted"));
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not found"));
    }

    public ResponseEntity<?> changeStatus(String id) {
        Optional<Maintenance> maintenanceOptional = maintenance_R.findById(id);
        if (maintenanceOptional.isPresent()) {
            Maintenance maintenance = maintenanceOptional.get();

            switch (maintenance.getStatus()){
                case REPORTED:
                    maintenance.setStatus(ERepair.PENDING);
                    break;
                case PENDING:
                    maintenance.setStatus(ERepair.INPROGRES);
                    break;
                case INPROGRES:
                    maintenance.setStatus(ERepair.DONE);
                    break;
            }
            maintenance_R.save(maintenance);
                return ResponseEntity.ok(mainToMainResponse(maintenance));
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not found"));
    }

    private MaintenenceResponse mainToMainResponse(Maintenance maintenance){
        return new MaintenenceResponse(maintenance.getId(), maintenance.getCar().getId(), maintenance.getProblemDescription(), maintenance.getStatus(), maintenance.getStartTime(), maintenance.getDoneTime());
    }

    private Maintenance mainRequestToMain(MaintenenceRequest newMaintenance) {
        Optional<Car> optionalCar = car_R.findById(newMaintenance.getCarId());
        Car car = new Car();
        if (optionalCar.isPresent()) {
            car = optionalCar.get();
        }
        return new Maintenance(car, newMaintenance.getDescription());
    }
}
