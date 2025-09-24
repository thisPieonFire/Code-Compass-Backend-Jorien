package local.code_compass_backend.client;

import local.code_compass_backend.database.repository.ProfileRepository;
import local.code_compass_backend.dto.AuthResponseDto;
import local.code_compass_backend.dto.CreateUserDto;
import local.code_compass_backend.dto.LoginDto;
import local.code_compass_backend.dto.ProfileDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.jdbc.datasource.UserCredentialsDataSourceAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SBAuthClient {
    @Value("${supabase.api-key}")
    private String supabaseApiKey;
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

        ResponseEntity<AuthResponseDto> response = restTemplate.exchange(url, HttpMethod.POST, request, AuthResponseDto.class);
        AuthResponseDto authResponseDto = response.getBody();
        if (authResponseDto == null)
            throw new IllegalStateException("Lege response van Authentication API");

        String userId = authResponseDto.getUser().getId();
        String accesToken = authResponseDto.getAccessToken();

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

        UserMetadata userMetadata = new UserMetadata(createUserDto.getDisplayName(), createUserDto.getRole());
        SignUpOptions signUpOptions = new SignUpOptions(userMetadata);
        SignUpRequest body = new SignUpRequest(
                createUserDto.getEmail(),
                createUserDto.getPassword(),
                signUpOptions
                );
        HttpEntity<SignUpRequest> request = new HttpEntity<>(body, headers);

        ResponseEntity<AuthResponseDto> response = restTemplate.exchange(url, HttpMethod.POST, request, AuthResponseDto.class);
        AuthResponseDto authResponseDto = response.getBody();
        if (authResponseDto == null)
            throw new IllegalStateException("Lege response van Authentication API");


        String email = createUserDto.getEmail();
        String displayName = createUserDto.getDisplayName();

        return new CreationDetails(email, displayName);
    }

    public record CreationDetails(String email, String displayName) {}
    public record SignUpRequest(String email, String password, SignUpOptions options) {}
    public record SignUpOptions(UserMetadata data) {}
    public record UserMetadata(String display_name, String role) {}
}