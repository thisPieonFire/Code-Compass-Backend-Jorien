package local.code_compass_backend.mapper;

import local.code_compass_backend.database.entity.ProfileEntity;
import local.code_compass_backend.database.entity.Role;
import local.code_compass_backend.dto.RegisterResponseDto;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class NewUserProfileMapper {
    public RegisterResponseDto mapToRegisterResponseDto(ProfileEntity profileEntity) {
        RegisterResponseDto RegisterResponseDto = new RegisterResponseDto();
        RegisterResponseDto.setId(String.valueOf(profileEntity.getId()));
        RegisterResponseDto.getUserMetadata().setDisplayName(profileEntity.getDisplayName());
        RegisterResponseDto.getAppMetadata().setRole(String.valueOf(profileEntity.getRole()));
        RegisterResponseDto.setEmail(profileEntity.getEmail());
        RegisterResponseDto.setCreatedAt(String.valueOf(profileEntity.getCreatedAt()));
        RegisterResponseDto.setUpdatedAt(String.valueOf(profileEntity.getUpdatedAt()));
        return RegisterResponseDto;
    }

    public ProfileEntity mapToProfileEntity(RegisterResponseDto registerResponseDto) {
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setId(UUID.fromString(registerResponseDto.getId()));
        profileEntity.setDisplayName(registerResponseDto.getUserMetadata().getDisplayName());
        profileEntity.setRole(Role.valueOf(registerResponseDto.getAppMetadata().getRole()));
        profileEntity.setEmail(registerResponseDto.getEmail());
        profileEntity.setCreatedAt(Instant.parse(registerResponseDto.getCreatedAt()));
        profileEntity.setUpdatedAt(Instant.parse(registerResponseDto.getUpdatedAt()));
        return profileEntity;
    }
}
