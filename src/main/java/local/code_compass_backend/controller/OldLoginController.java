package local.code_compass_backend.controller;

import local.code_compass_backend.dto.ProfileDto;
import local.code_compass_backend.service.OldLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OldLoginController {

 @Autowired
    private OldLoginService oldLoginService;

 @PostMapping ("/api/trainee_login")
    public ResponseEntity<Void>  validateLogIn(@RequestBody ProfileDto profileDto) {
        oldLoginService.validateLogIn(profileDto);
        return ResponseEntity.ok().build();
    }
}
