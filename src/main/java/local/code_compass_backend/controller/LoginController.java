package local.code_compass_backend.controller;


import local.code_compass_backend.dto.ProfileDto;
import local.code_compass_backend.utility.CookieUtil;
import local.code_compass_backend.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

 @Autowired
    private LoginService loginService;

    @Autowired
    private CookieUtil cookieUtil;

    @PostMapping ("/api/trainee_login") //Checks if the email address is registered at all. Current status: gives no response when there is a registered email
    public ResponseEntity<Void>  validateLogIn(@RequestBody ProfileDto profileDto) {
        loginService.validateLogIn(profileDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie cookie = cookieUtil.clearJwtCookie();

        return ResponseEntity.ok()
                .header("Set-Cookie", cookie.toString())
                .body("Logged out");
    }

/*
//TODO "/api/me" geeft profielinfo terug (id, role, display_name) of 401 (met getForObject()?)
    @GetMapping("/api/me")
    public ResponseEntity<?> personalInformation(@RequestBody ProfileDto profileDto) {
        return ResponseEntity.ok().build();
    }*/




    //gebruiker aanmaken = iemand registreert zich, dan moet de backend een bericht sturen naar SupaBase met "registreer deze gebruiker"
    //de UUID die dan terugkomt moet de UserId worden, dit is een 1 op 1 relatie (Dat is dus die PK/FK relatie)
}
