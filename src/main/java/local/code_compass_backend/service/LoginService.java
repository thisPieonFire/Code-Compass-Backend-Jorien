package local.code_compass_backend.service;

import local.code_compass_backend.database.repository.ProfileRepository;
import local.code_compass_backend.dto.ProfileDto;
import local.code_compass_backend.mapper.ProfileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class LoginService {

    @Autowired
    private ProfileRepository profileRepository;

    public void validateLogIn(ProfileDto profileDto) {
        if (profileDto == null || profileDto.getEmail() == null || profileDto.getEmail().isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Ongeldige inloggegevens.");
        }
        boolean exists = profileRepository.existsByEmail(profileDto.getEmail());
        if (!exists) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Geen geregistreerd emailadres gevonden.");
        }
        // Bestaat: niets teruggeven, geen mutaties uitvoeren
    }
}