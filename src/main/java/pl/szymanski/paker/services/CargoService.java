package pl.szymanski.paker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.szymanski.paker.algorithm.BinPacker.Packer;
import pl.szymanski.paker.models.Cargo;
import pl.szymanski.paker.models.Item;
import pl.szymanski.paker.models.enums.ECarType;
import pl.szymanski.paker.payload.request.CargoRequest;
import pl.szymanski.paker.payload.request.CargoCheckType;
import pl.szymanski.paker.payload.response.CargoResponse;
import pl.szymanski.paker.payload.response.MessageResponse;
import pl.szymanski.paker.repository.CargoRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CargoService {

    @Autowired
    private CargoRepo cargo_R;

    public ResponseEntity<?> findAll() {
        List<CargoResponse> responseList = new ArrayList<>();

        for (Cargo c : cargo_R.findAll()) {
            responseList.add(cargoToCargoResponse(c));
        }
        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<?> findById(String id) {
        Optional<Cargo> cargoOptional = cargo_R.findById(id);
        if (cargoOptional.isPresent()) {
            Cargo cargo = cargoOptional.get();
            CargoResponse response = cargoToCargoResponse(cargo);
            return ResponseEntity
                    .ok(response);
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not Found"));
    }

    public ResponseEntity<?> findByValue(float min, float max) {
        List<CargoResponse> responseList = new ArrayList<>();

        for (Cargo c : cargo_R.findByValueBetween(min,max)) {
            responseList.add(cargoToCargoResponse(c));
        }
        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<?> findByWeight(float min, float max) {
        List<CargoResponse> responseList = new ArrayList<>();

        for (Cargo c : cargo_R.findByWeightBetween(min,max)) {
            responseList.add(cargoToCargoResponse(c));
        }
        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<?> update(CargoRequest updateCargo) {
        if (updateCargo.idValid()) {
            Cargo cargo = cargoRequestToCargo(updateCargo);
            Optional<Cargo> cargoOptional = cargo_R.findById(cargo.getId());
            if (cargoOptional.isPresent()) {
                Cargo toUpdateCargo = cargoOptional.get();
                toUpdateCargo.setRegister(cargo.getRegister());
                toUpdateCargo.setValue(cargo.getValue());
                toUpdateCargo.setWeight(cargo.getWeight());
                cargo_R.save(toUpdateCargo);
                return ResponseEntity.ok(cargoToCargoResponse((toUpdateCargo)));
            }
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Not found"));
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not valid request"));
    }

    public ResponseEntity<?> addNew(CargoRequest newCargo) {
        if (newCargo.idValid()) {
            Cargo cargo = cargoRequestToCargo(newCargo);
            cargo_R.save(cargo);
            return ResponseEntity.ok(cargoToCargoResponse(cargo));
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not valid request"));
    }

    private CargoResponse cargoToCargoResponse(Cargo cargo){
        return new CargoResponse(cargo.getId(),cargo.getRegister(),cargo.getValue(), cargo.getWeight());
    }

    private Cargo cargoRequestToCargo(CargoRequest cargo){
        Cargo tmp = new Cargo();
        tmp.setRegister(cargo.getRegister());
        tmp.setValue(cargo.getValue());
        tmp.setWeight(cargo.getWeight());
        return tmp;
    }

    public ResponseEntity<?> checkType(CargoCheckType listItem) {
        Packer packer;
        float maxH =0f;
        float listWeight=0f;
        for(Item item : listItem.getList()){
            if(item.height>maxH){
                maxH = item.height;
            }
            listWeight+= item.weight;
        }
        if(listWeight<=ECarType.TYPE_SMALL.weight){
            if(maxH<=ECarType.TYPE_SMALL.height){
                packer = new Packer(ECarType.TYPE_SMALL.width,ECarType.TYPE_SMALL.depth);
                packer.loadItems(listItem.getList());
                packer.fit();
                if(packer.checkResult()){
                    return ResponseEntity.ok(ECarType.TYPE_SMALL);
                }
            }
        }
        if(listWeight<=ECarType.TYPE_MID.weight){
            if(maxH<=ECarType.TYPE_MID.height){
                packer = new Packer(ECarType.TYPE_MID.width,ECarType.TYPE_MID.depth);
                packer.loadItems(listItem.getList());
                packer.fit();
                if(packer.checkResult()){
                    return ResponseEntity.ok(ECarType.TYPE_MID);
                }
            }
        }
        if(listWeight<=ECarType.TYPE_BIG.weight){
            if(maxH<=ECarType.TYPE_BIG.height){
                packer = new Packer(ECarType.TYPE_BIG.width,ECarType.TYPE_BIG.depth);
                packer.loadItems(listItem.getList());
                packer.fit();
                if(packer.checkResult()){
                    return ResponseEntity.ok(ECarType.TYPE_BIG);
                }
            }
        }
        if(listWeight<=ECarType.TYPE_SPECIAL.weight){
            if(maxH<=ECarType.TYPE_SPECIAL.height){
                packer = new Packer(ECarType.TYPE_SPECIAL.width,ECarType.TYPE_SPECIAL.depth);
                packer.loadItems(listItem.getList());
                packer.fit();
                if(packer.checkResult()){
                    return ResponseEntity.ok(ECarType.TYPE_SPECIAL);
                }
            }
        }
        return ResponseEntity.ok("NO");
    }
}
