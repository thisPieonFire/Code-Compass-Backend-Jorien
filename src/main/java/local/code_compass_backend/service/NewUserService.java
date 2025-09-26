package local.code_compass_backend.service;

import local.code_compass_backend.database.entity.ProfileEntity;
import local.code_compass_backend.database.repository.ProfileRepository;
import local.code_compass_backend.dto.RegisterResponseDto;
import local.code_compass_backend.mapper.NewUserProfileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

@Service
public class NewUserService {

    @Autowired
    private NewUserProfileMapper newUserProfileMapper;

    @Autowired
    private ProfileRepository profileRepository;

    public void saveNewUserProfile(RegisterResponseDto registerResponseDto) {
        ProfileEntity profileEntity = newUserProfileMapper.mapToProfileEntity(registerResponseDto);
        try{ profileRepository.save(profileEntity);
        }
        catch (HttpStatusCodeException | IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Gegevens konden niet worden weggeschreven");
        }
    }
}
