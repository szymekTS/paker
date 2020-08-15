package pl.szymanski.paker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.szymanski.paker.services.RouteService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/route /")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping("/find")
    public ResponseEntity<?> findById(@RequestParam String id) {
        return routeService.findByID(id);
    }
}
