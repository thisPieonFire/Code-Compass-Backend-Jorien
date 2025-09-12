package local.code_compass_backend.mapper;

import org.springframework.stereotype.Service;
import local.code_compass_backend.database.entity.TopicEntity;
import local.code_compass_backend.dto.TopicDto;

@Service
    public class TopicMapper {
        public TopicDto mapToTopicDto(TopicEntity topicEntity) {
            TopicDto topicDto = new TopicDto();
            topicDto.setId(topicEntity.getId());
            topicDto.setName(topicEntity.getName());
            topicDto.setDescription(topicEntity.getDescription());
            return topicDto;
        }

        public TopicEntity mapToTopicEntity(TopicDto topicDto) {
            TopicEntity topicEntity = new TopicEntity();
            topicEntity.setName(topicDto.getName());
            topicEntity.setDescription(topicDto.getDescription());
            return topicEntity;

        }
    }
