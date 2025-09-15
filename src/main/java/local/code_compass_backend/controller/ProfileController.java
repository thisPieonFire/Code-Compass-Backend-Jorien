package local.code_compass_backend.controller;

import local.code_compass_backend.dto.ProfileDto;
import local.code_compass_backend.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

 @Autowired
    private ProfileService profileService;

 //get the input

   /* @PostMapping ("/api/login")
    public void loginAuthentication(@RequestBody ProfileDto profileDto) {
        profileService.loginAuthentication(profileDto);

    }*/



}
