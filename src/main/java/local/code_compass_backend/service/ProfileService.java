package local.code_compass_backend.service;

import local.code_compass_backend.database.entity.ProfileEntity;
import local.code_compass_backend.database.repository.ProfileRepository;
import local.code_compass_backend.dto.ProfileDto;
import local.code_compass_backend.mapper.ProfileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileMapper profileMapper;
    public void validateLogIn(ProfileDto profileDto) {
        if (profileDto == null || profileDto.getEmail() == null || profileDto.getEmail().isBlank()) {
            // Geen details lekken (user enumeration voorkomen)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Ongeldige inloggegevens.");
        }
        boolean exists = profileRepository.existsByEmail(profileDto.getEmail());
        if (!exists) {
            // Blokkeer login wanneer email niet bestaat
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Geen emailadres gevonden.");
        }
        // Bestaat: niets teruggeven, geen mutaties uitvoeren
        // todo Bestaat: verify via de Supabase Auth API
        // to do Zoek profiles record.
        //
        //Check role = ADMIN. if not: 403 error, geen admin
        //
        //Ontvang JWT van Supabase
        //
        //Zet JWT in HttpOnly cookie (Secure, SameSite, HttpOnly).
        //Security opzetten via Hibernate/Filter
        // Response: { "ok": true }.
    }

}
