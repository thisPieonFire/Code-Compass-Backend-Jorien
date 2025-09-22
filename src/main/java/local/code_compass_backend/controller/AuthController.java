package local.code_compass_backend.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import local.code_compass_backend.dto.AuthDto;
import local.code_compass_backend.service.AuthService;
import local.code_compass_backend.utility.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private CookieUtil cookieUtil;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private record User(String email, String displayName) {}

    private record LoginResponse(User user) {}

    @PostMapping("/api/login")
    public ResponseEntity<?> loginResult(@RequestBody AuthDto authDto) {
        AuthService.LoginResult result = authService.loginResult(authDto);
        LoginResponse body = new LoginResponse(new User(result.email(), result.displayName()));
        return cookieUtil
                .setJwtCookie(ResponseEntity.ok(), result.accessToken())
                .body(body);
    }


    // "/api/me" geeft profielinfo terug (id, role, display_name) of 401
/*    @GetMapping("/api/me")
    public ResponseEntity<?> personalInformation(@RequestBody AuthDto authDto) {
        AuthService.LoginResult result = authService.loginAdmin(authDto);
        return
    }
        return ResponseEntity.ok().build();
        -
    - On GET /api/me:
        - 200 OK with { email: string, displayName?: string } when the session cookie is valid.
        - 401 with { message: string } when not authenticated.
        Hij moet de JWT uit de cookie lezen en verifiëren?
        */

}
