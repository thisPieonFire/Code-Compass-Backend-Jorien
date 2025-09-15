package local.code_compass_backend.service;

import local.code_compass_backend.database.entity.SubTopicEntity;
import local.code_compass_backend.database.repository.SubTopicRepository;
import local.code_compass_backend.dto.SubTopicDto;
import local.code_compass_backend.mapper.SubTopicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubTopicService {

    @Autowired
    private SubTopicRepository subTopicRepository;

    @Autowired
    private SubTopicMapper subTopicMapper;

    public void saveSubTopic (SubTopicDto subTopicDto) {
        SubTopicEntity subTopicEntity = subTopicMapper.mapToSubTopicEntity(subTopicDto);
        subTopicRepository.save(subTopicEntity);
    }

    public SubTopicDto getSubTopic (Long id) {
        SubTopicEntity subTopicEntity = subTopicRepository.findById(id).orElse(null);
        return subTopicMapper.mapToSubTopicDto(subTopicEntity);

    }


}
