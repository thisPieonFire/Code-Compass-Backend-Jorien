package local.code_compass_backend.database.repository;

import local.code_compass_backend.database.entity.ProfileEntity;
import local.code_compass_backend.database.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IdRepository extends JpaRepository<ProfileEntity, UUID> {

    boolean existsByIdAndRole(UUID id, Role role);

}