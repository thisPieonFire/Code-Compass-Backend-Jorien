package local.code_compass_backend.service;

import local.code_compass_backend.client.SBAuthClient;
import local.code_compass_backend.database.entity.Role;
import local.code_compass_backend.database.repository.ProfileRepository;
import local.code_compass_backend.dto.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private SBAuthClient sbAuthClient;
    public record LoginResult(String accessToken, String email, String displayName) {}


    public LoginResult loginResult(LoginDto loginDto) {
        SBAuthClient.AuthenticationDetails authenticationDetails = authenticateUser(loginDto);

        var profileInformation = profileRepository.findById(authenticationDetails.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profiel niet gevonden"));
            return new LoginResult(authenticationDetails.getAccessToken(), profileInformation.getEmail(), profileInformation.getDisplayName());
    }

    public SBAuthClient.AuthenticationDetails authenticateUser(LoginDto loginDto) {
        if (loginDto == null || loginDto.getEmail() == null || loginDto.getEmail().isBlank()
                || loginDto.getPassword() == null || loginDto.getPassword().isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Voer je gegevens in.");
        }

        boolean profileExists = profileRepository.existsByEmail(loginDto.getEmail());
        if (!profileExists) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Geen geregistreerd emailadres gevonden.");
        }


        SBAuthClient.AuthenticationDetails authenticationDetails;
        try {
            authenticationDetails = sbAuthClient.authenticateAndGetUser(loginDto.getEmail(), loginDto.getPassword());
        } catch (HttpStatusCodeException | IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Ongeldige inloggegevens.");
        }

        boolean isAdmin = profileRepository.existsByIdAndRole(authenticationDetails.getUserId(), Role.ADMIN);
                if (!isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Onvoldoende rechten.");
        }
        return authenticationDetails;
    }

}