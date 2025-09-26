package local.code_compass_backend.service;

import local.code_compass_backend.client.SBAuthClient;
import local.code_compass_backend.database.repository.IdRepository;
import local.code_compass_backend.database.repository.ProfileRepository;
import local.code_compass_backend.dto.CreateUserDto;
import local.code_compass_backend.dto.LoginDto;
import local.code_compass_backend.dto.NewUserResult;
import local.code_compass_backend.dto.RegisterResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;


import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private IdRepository idRepository;

    @Autowired
    private SBAuthClient sbAuthClient;
    public record Login(String accessToken, String email, String displayName) {}
    public record CreateNewUser(String email, String displayName) {}


    public Login login(LoginDto loginDto) {
        SBAuthClient.AuthenticationDetails authenticationDetails = authenticateUser(loginDto);

        var profileInformation = idRepository.findById(UUID.fromString(authenticationDetails.userId())).get();
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
        ////als de email niet is bevestigd krijg je ook een error, maar die wordt dus ook naar hierboven gegooid

      /*  boolean isAdmin = idRepository.existsByIdAndRole(UUID.fromString(authenticationDetails.userId()), Role.ADMIN);
                if (!isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Onvoldoende rechten.");
        }*/
        return authenticationDetails;
    }

    public NewUserResult createNewUser(CreateUserDto createUserDto) {
        RegisterResponseDto responseDto = validateUser(createUserDto);
        return new NewUserResult(responseDto);
    }

    public RegisterResponseDto validateUser (CreateUserDto createUserDto) {
        RegisterResponseDto registerResponseDto;
        try{
            registerResponseDto = sbAuthClient.validateAndCreateNewUser(createUserDto);
        } catch (HttpStatusCodeException|IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return registerResponseDto;
    }


}