package pl.szymanski.paker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.szymanski.paker.payload.request.StatusRequest;
import pl.szymanski.paker.services.StatusService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/status/")
public class StatusController {
    @Autowired
    StatusService statusService;


    @GetMapping("/find_all")
    public ResponseEntity<?> findAll() {
        return statusService.findAll();
    }

    @GetMapping("/find")
    public ResponseEntity<?> getCityById(@RequestParam String id) {
        return statusService.findByID(id);
    }

    @GetMapping("/find_status")
    public ResponseEntity<?> getByStatus(@RequestParam String status) {
        return statusService.findByStatus(status);
    }

    @GetMapping("/find_worker")
    public ResponseEntity<?> getByWorker(@RequestParam String workerId) {
        return statusService.findByWorker(workerId);
    }

    @PostMapping("/update_comment")
    public ResponseEntity<?> updateComment(@RequestBody StatusRequest statusRequest) {
        return statusService.updateComment(statusRequest);
    }


    @GetMapping("/change_status")
    public ResponseEntity<?> changeStatus(@RequestBody StatusRequest statusRequest) {
        return statusService.changeStatus(statusRequest);
    }

    @PostMapping("/add_status")
    public ResponseEntity<?> addStatus(@RequestBody StatusRequest statusRequest) {
        return statusService.addStatus(statusRequest);
    }

    @DeleteMapping("/del")
    public ResponseEntity<?> delStatus(@RequestParam String id) {
        return statusService.delStatus(id);
    }

}
