package local.code_compass_backend.client;

import local.code_compass_backend.dto.AuthResponseDto;
import local.code_compass_backend.dto.LoginDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Map;


@Component
public class SBAuthClient {
    @Value("${supabase.api-key}")
    private String supabaseApiKey;

    @Value("${supabase.auth-url}")
    private String supabaseAuthUrl;
    //todo get the .env to work


    private final RestTemplate restTemplate = new RestTemplate();

    public AuthenticationDetails authenticateAndGetUser(LoginDto loginDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", supabaseApiKey);
        String url = supabaseAuthUrl + "/auth/v1/token?grant_type=password";
        HttpEntity<LoginDto> request = new HttpEntity<>(loginDto, headers);

        ResponseEntity<AuthResponseDto> response = restTemplate.exchange(url, HttpMethod.POST, request, AuthResponseDto.class);
        AuthResponseDto authResponseDto = response.getBody();
        if (authResponseDto == null)
            throw new IllegalStateException("Lege response van Authentication API");

        String userId = authResponseDto.getUser().getId();
        String accesToken = authResponseDto.getAccessToken();

        return new AuthenticationDetails(userId, accesToken);
    }
    public record AuthenticationDetails(String userId, String accessToken) {}

    public CreationResponse validateAndCreateNewUser (String email, String displayName, String role) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", supabaseApiKey);

        Map<String, Object> body = Map.of("email", email, "displayName", displayName, "role", role);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        String url = supabaseAuthUrl + "/auth/v1/signup";

        return new CreationResponse(email, displayName);
// er moet een standaard wachtwoord worden toegevoegd aan de oproep naar SB
    }

    public final class CreationResponse {
        private final String email;
        private final String displayName;

        public CreationResponse(String email, String displayName) {
            this.email = email;
            this.displayName = displayName;
        }

        public String getEmail() {
            return email;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

}