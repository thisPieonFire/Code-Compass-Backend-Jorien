package local.code_compass_backend.utility;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {
    private final String cookieName = "jwt";

    public ResponseCookie createJwtCookie(String token) {
        return ResponseCookie.from(cookieName, token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(60 * 60)
                .sameSite("None")
                .build();
    }
    public ResponseEntity.BodyBuilder setJwtCookie(ResponseEntity.BodyBuilder builder, String token) {
        ResponseCookie cookie = createJwtCookie(token);
        return builder.header("Set-Cookie", cookie.toString());
    }


    public ResponseCookie clearJwtCookie() {
        return ResponseCookie.from(cookieName, "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("None")
                .build();
    }


}
