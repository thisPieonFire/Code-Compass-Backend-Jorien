package local.code_compass_backend.client;

import local.code_compass_backend.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class SBAuthClient {
    @Value("${SUPABASE_ANON_KEY}")
    private String supabaseApiKey;
    @Value("${SUPABASE_SERVICE_ROLE_KEY}")
    private String supabaseServiceRoleKey;
    @Value("${SUPABASE_AUTH_URL}")
    private String supabaseAuthUrl;
    //todo get the .env to work

    private final RestTemplate restTemplate = new RestTemplate();


    public AuthenticationDetails authenticateAndGetUser(LoginDto loginDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", supabaseApiKey);
        String url = supabaseAuthUrl + "/auth/v1/token?grant_type=password";
        HttpEntity<LoginDto> request = new HttpEntity<>(loginDto, headers);

        ResponseEntity<LoginResponseDto> response = restTemplate.exchange(url, HttpMethod.POST, request, LoginResponseDto.class);
        LoginResponseDto loginResponseDto = response.getBody();
        if (loginResponseDto == null)
            throw new IllegalStateException("Lege response van Authentication API");

        String userId = loginResponseDto.getUser().getId();
        String accesToken = loginResponseDto.getAccessToken();

        return new AuthenticationDetails(userId, accesToken);
    }

    public record AuthenticationDetails(String userId, String accessToken) {
    }

    public RegisterResponseDto validateAndCreateNewUser(CreateUserDto createUserDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", supabaseApiKey);
        headers.setBearerAuth(supabaseApiKey);

        String url = supabaseAuthUrl + "/auth/v1/signup";

        SignUpRequest body = new SignUpRequest(
                createUserDto.getEmail(),
                createUserDto.getPassword()
        );
        HttpEntity<SignUpRequest> request = new HttpEntity<>(body, headers);
        ResponseEntity<RegisterResponseDto> response = restTemplate.exchange(url, HttpMethod.POST, request, RegisterResponseDto.class);
        RegisterResponseDto registerResponseDto = response.getBody();
        if (registerResponseDto == null)
            throw new IllegalStateException("Lege response van Authentication API");
       return setRoleAndDisplayName(registerResponseDto.getId(), createUserDto.getDisplayName());


       // return registerResponseDto;
    }

    private RegisterResponseDto setRoleAndDisplayName(String userId, String displayName) {
        String patchUrl = supabaseAuthUrl + "/auth/v1/admin/users/" + userId;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", supabaseServiceRoleKey);
        headers.setBearerAuth(supabaseServiceRoleKey);
        
        Map<String, Object> patch = Map.of("app_metadata", Map.of("role", "TRAINEE"), "user_metadata", Map.of("display_name", displayName));
        HttpEntity<Map<String, Object>> patchReq = new HttpEntity<>(patch, headers);
        
        try {
            ResponseEntity<RegisterResponseDto> response = restTemplate.exchange(patchUrl, HttpMethod.PUT, patchReq, RegisterResponseDto.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new IllegalStateException("Failed to update user: " + response.getStatusCode());
            }
            return response.getBody();
        } catch (Exception e) {
            throw new IllegalStateException("Error updating user: " + e.getMessage(), e);
        }

    }
}