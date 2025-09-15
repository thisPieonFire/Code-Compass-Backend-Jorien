package local.code_compass_backend.database.repository;

import local.code_compass_backend.database.entity.SubTopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<SubTopicEntity, Long> {


}