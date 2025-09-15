package local.code_compass_backend.service;

import local.code_compass_backend.database.entity.TopicEntity;
import local.code_compass_backend.dto.TopicDto;
import local.code_compass_backend.database.repository.TopicRepository;
import local.code_compass_backend.mapper.TopicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TopicMapper topicMapper;

    public void saveTopic (TopicDto topicDto) {
        TopicEntity topicEntity = topicMapper.mapToTopicEntity(topicDto);
        topicRepository.save(topicEntity);
    }

    public TopicDto getTopic (Long id) {
        TopicEntity topicEntity = topicRepository.findById(id).orElse(null);
        return topicMapper.mapToTopicDto(topicEntity);
    }


}
