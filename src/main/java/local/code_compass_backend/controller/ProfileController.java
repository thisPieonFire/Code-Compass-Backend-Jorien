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

    @PostMapping ("/login") //Checks if the email address is registered at all. Current status: gives no response when there is a registered email
    public void validateLogIn(@RequestBody ProfileDto profileDto) {
        profileService.validateLogIn(profileDto);
    }

    //TODO "/me" geeft profielinfo terug (id, role, display_name) of 401
    //TODO "/logout" wist de cookie

    //gebruiker aanmaken = iemand registreert zich, dan moet de backend een bericht sturen naar SupaBase met "registreer deze gebruiker"
    //de UUID die dan terugkomt moet de UserId worden, dit is een 1 op 1 relatie (Dat is dus die PK/FK relatie)
}
