package local.code_compass_backend.service;

import local.code_compass_backend.database.entity.ProfileEntity;
import local.code_compass_backend.database.repository.ProfileRepository;
import local.code_compass_backend.dto.ProfileDto;
import local.code_compass_backend.mapper.ProfileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileMapper profileMapper;

   // loginAuthentication


}
