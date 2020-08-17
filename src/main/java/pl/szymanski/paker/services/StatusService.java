package pl.szymanski.paker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.szymanski.paker.models.Status;
import pl.szymanski.paker.models.User;
import pl.szymanski.paker.models.enums.EStatus;
import pl.szymanski.paker.payload.request.StatusRequest;
import pl.szymanski.paker.payload.response.MessageResponse;
import pl.szymanski.paker.payload.response.StatusResponse;
import pl.szymanski.paker.repository.StatusRepo;
import pl.szymanski.paker.repository.UserRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StatusService {
    @Autowired
    private StatusRepo status_R;
    @Autowired
    private UserRepo user_R;

    public ResponseEntity<?> findAll() {
        List<StatusResponse> responseList = new ArrayList<>();

        for (Status c : status_R.findAll()) {
            responseList.add(statusToStatusResponse(c));
        }
        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<?> findByID(String id) {
        Optional<Status> statusOptional = status_R.findById(id);
        if (statusOptional.isPresent()) {
            Status status = statusOptional.get();
            StatusResponse response = statusToStatusResponse(status);
            return ResponseEntity
                    .ok(response);
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not Found"));
    }

    public ResponseEntity<?> findByWorker(String workerId) {
        List<StatusResponse> responseList = new ArrayList<>();
        Optional<User> userOptional = user_R.findById(workerId);
        if (userOptional.isPresent()) {
            for (Status c : status_R.findByWorker(userOptional.get())) {
                responseList.add(statusToStatusResponse(c));
            }
        }
        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<?> findByStatus(String status) {
        List<StatusResponse> responseList = new ArrayList<>();
        for (Status c : status_R.findByStatus(status)) {
            responseList.add(statusToStatusResponse(c));
        }

        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<?> changeStatus(StatusRequest statusRequest) {
        if (statusRequest.isValid()) {
            Optional<Status> statusOptional = status_R.findById(statusRequest.getId());
            if (statusOptional.isPresent()) {
                Status status = statusOptional.get();
                switch (status.getStatusCode()) {
                    case STATUS_ACCEPTED:
                        status.setStatusCode(EStatus.STATUS_PACKING);
                        break;
                    case STATUS_PACKING:
                        status.setStatusCode(EStatus.STATUS_TRANSPORTING);
                        break;
                    case STATUS_TRANSPORTING:
                        status.setStatusCode(EStatus.STATUS_DELIVERED);
                        break;
                }
                status_R.save(status);
                return ResponseEntity.ok(statusToStatusResponse(status));
            }
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Status not found"));

        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not valid request"));
    }

    public ResponseEntity<?> addStatus(StatusRequest statusRequest) {
        if (statusRequest.isValid()) {
            Status status = statusRequestToStatus(statusRequest);
            if (status_R.existsById(status.getId())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Status exists"));
            }
            status.setStatusCode(EStatus.STATUS_ACCEPTED);
            status_R.save(status);
            return ResponseEntity.ok(statusToStatusResponse(status));
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not valid request"));
    }

    public ResponseEntity<?> delStatus(String id) {
        if (status_R.existsById(id)) {
            status_R.deleteById(id);
            return ResponseEntity
                    .ok(new MessageResponse("Deleted"));
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not found"));
    }

    public ResponseEntity<?> updateComment(StatusRequest statusRequest) {
        if (statusRequest.isValid()) {
            Status status = statusRequestToStatus(statusRequest);
            Optional<Status> statusOptional = status_R.findById(status.getId());
            if (statusOptional.isPresent()) {
                Status toUpdate = statusOptional.get();
                toUpdate.setComments(toUpdate.getComments() + "\n" + status.getComments());
                status_R.save(toUpdate);
                return ResponseEntity.ok(statusToStatusResponse(toUpdate));
            }
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Not found"));
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not valid request"));
    }


    private StatusResponse statusToStatusResponse(Status status) {
        return new StatusResponse(status.getId(), status.getStatusCode().name(), status.getWorker().getId(), status.getComments());
    }

    private Status statusRequestToStatus(StatusRequest statusRequest) {
        Status status = new Status();
        status.setId(statusRequest.getId());
        Optional<User> userOptional = user_R.findById(statusRequest.getWorkerId());
        if (userOptional.isPresent()) {
            status.setWorker(userOptional.get());
        }
        status.setComments(statusRequest.getComment());

        return status;
    }


}
