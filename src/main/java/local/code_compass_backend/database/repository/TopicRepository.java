package local.code_compass_backend.database.repository;

import local.code_compass_backend.database.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<TopicEntity, Long> {


}
