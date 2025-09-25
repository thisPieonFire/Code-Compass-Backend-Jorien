package local.code_compass_backend.client;

import local.code_compass_backend.database.repository.ProfileRepository;
import local.code_compass_backend.dto.LoginResponseDto;
import local.code_compass_backend.dto.CreateUserDto;
import local.code_compass_backend.dto.LoginDto;
import local.code_compass_backend.dto.RegisterResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class SBAuthClient {
    @Value("${supabase.api-key}")
    private String supabaseApiKey;
    @Value("${supabase.service-role-key}")
    private String supabaseServiceRoleKey;
    @Value("${supabase.auth-url}")
    private String supabaseAuthUrl;
    //todo get the .env to work
    private final RestTemplate restTemplate = new RestTemplate();
    private final ProfileRepository profileRepository;

    public SBAuthClient(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

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

    public CreationDetails validateAndCreateNewUser(CreateUserDto createUserDto) {
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
        //String testResponse = restTemplate.postForObject(url, request, String.class);
        ResponseEntity<RegisterResponseDto> response = restTemplate.exchange(url, HttpMethod.POST, request, RegisterResponseDto.class);
        RegisterResponseDto registerResponseDto = response.getBody();
        if (registerResponseDto == null)
            throw new IllegalStateException("Lege response van Authentication API");
        setRole(registerResponseDto.getId());


        String email = createUserDto.getEmail();
        String displayName = createUserDto.getDisplayName();

        return new CreationDetails(email, displayName);
    }

    private void setRole(String userId) {
        String patchUrl = supabaseAuthUrl + "/auth/v1/admin/users/" + userId;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", supabaseServiceRoleKey);
        headers.setBearerAuth(supabaseServiceRoleKey);
        
        Map<String, Object> patch = Map.of("app_metadata", Map.of("role", "TRAINEE"));
        HttpEntity<Map<String, Object>> patchReq = new HttpEntity<>(patch, headers);
        
        try {
            ResponseEntity<String> response = restTemplate.exchange(patchUrl, HttpMethod.PUT, patchReq, String.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new IllegalStateException("Failed to set user role: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new IllegalStateException("Error setting user role: " + e.getMessage(), e);
        }
    }

    public record CreationDetails(String email, String displayName) {}
    public record SignUpRequest(String email, String password) {}
    //todo aparte class maken hiervoor
}