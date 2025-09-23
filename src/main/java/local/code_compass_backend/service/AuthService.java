package local.code_compass_backend.service;

import local.code_compass_backend.client.SBAuthClient;
import local.code_compass_backend.database.entity.Role;
import local.code_compass_backend.database.repository.ProfileRepository;
import local.code_compass_backend.dto.AuthResponseDto;
import local.code_compass_backend.dto.LoginDto;
import local.code_compass_backend.dto.ProfileDto;
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
    public record Login(String accessToken, String email, String displayName) {}
    public record CreateNewUser(String email, String displayName) {}


    public Login login(LoginDto loginDto) {
        SBAuthClient.AuthenticationDetails authenticationDetails = authenticateUser(loginDto);

        var profileInformation = profileRepository.findById(authenticationDetails.userId()).get();
            return new Login(authenticationDetails.accessToken(), profileInformation.getEmail(), profileInformation.getDisplayName());
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
            authenticationDetails = sbAuthClient.authenticateAndGetUser(loginDto);
        } catch (HttpStatusCodeException | IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Ongeldige inloggegevens.");
        }

        boolean isAdmin = profileRepository.existsByIdAndRole(authenticationDetails.userId(), Role.ADMIN);
                if (!isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Onvoldoende rechten.");
        }
        return authenticationDetails;
    }

/*    public CreateNewUser create(ProfileDto profileDto) {
        SBAuthClient.CreationResponse creationResponse = validateUser(profileDto);

        return new CreateNewUser(creationResponse.getEmail(), creationResponse.getDisplayName());
    }

    public SBAuthClient.CreationResponse validateUser (ProfileDto profileDto) {
        *//*boolean profileExists = profileRepository.existsByEmail(profileDto.getEmail());
        if (profileExists) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Emailadres is al geregistreerd.");
        }*//*

        SBAuthClient.CreationResponse creationResponse;
        try{
            creationResponse =sbAuthClient.validateAndCreateNewUser(profileDto.getEmail(), profileDto.getDisplayName(), profileDto.getRole()); //dit is de enum, nu gaat het fout
        } catch (HttpStatusCodeException|IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Ongeldige gegevens."); //nog niet helemaal, maar goed genoeg
        }
        return creationResponse;
    }*/


}