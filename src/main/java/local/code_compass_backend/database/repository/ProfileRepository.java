package local.code_compass_backend.database.repository;

import local.code_compass_backend.database.entity.ProfileEntity;
import local.code_compass_backend.database.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<ProfileEntity, String> {

    boolean existsByEmail(String email); //used for checking if an email is already registered
    boolean existsByIdAndRole(String id, Role role);

}