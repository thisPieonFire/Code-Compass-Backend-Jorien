package local.code_compass_backend.controller;

import local.code_compass_backend.client.SBAuthClient;
import local.code_compass_backend.dto.AuthDto;
import local.code_compass_backend.service.AuthService;
import local.code_compass_backend.utility.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
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

    @PostMapping("/api/login")
    public ResponseEntity<?> authenticateAdmin(@RequestBody AuthDto authDto) {
        SBAuthClient.AuthResult auth = authService.authenticateAdmin(authDto);
            return cookieUtil
                .setJwtCookie(ResponseEntity.ok(), auth.getAccessToken())
                .body("Admin, welcome!");

    }
}
