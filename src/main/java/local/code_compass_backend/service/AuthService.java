package local.code_compass_backend.service;

import local.code_compass_backend.client.SBAuthClient;
import local.code_compass_backend.database.entity.Role;
import local.code_compass_backend.database.repository.ProfileRepository;
import local.code_compass_backend.dto.AuthDto;
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

    public SBAuthClient.AuthResult authenticateAdmin(AuthDto authDto) {
        if (authDto == null || authDto.getEmail() == null || authDto.getEmail().isBlank()
                || authDto.getPassword() == null || authDto.getPassword().isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Voer je gegevens in.");
        }

        SBAuthClient.AuthResult auth;
        try {
            auth = sbAuthClient.authenticateAndGetUser(authDto.getEmail(), authDto.getPassword());
        } catch (HttpStatusCodeException | IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Ongeldige inloggegevens.");
        }

        boolean isAdmin = profileRepository.existsByIdAndRole(auth.getUserId(), Role.ADMIN);
        if (!isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Onvoldoende rechten.");
        }
        return auth;

    }
}