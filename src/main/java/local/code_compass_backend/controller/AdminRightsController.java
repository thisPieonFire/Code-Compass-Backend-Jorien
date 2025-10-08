package local.code_compass_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminRightsController {

    @Autowired
    private AdminRightsController adminRightsController;

    @GetMapping("/api/admincheck")
    @PreAuthorize("hasRole('ADMIN')")
    public String AdminCheck () {
        return "Welcome, admin!";
    }

}
//todo Hier een response entity oid van maken ipv een string
// daarna: 200 als goed, en dat terugsturen
// if not: iets terugsturen zodat de frontend de user 'm terugstuurt naar de forbidden page