package local.code_compass_backend.mapper;

import org.springframework.stereotype.Service;
import local.code_compass_backend.database.entity.SubTopicEntity;
import local.code_compass_backend.dto.SubTopicDto;

@Service
public class SubTopicMapper {
    public SubTopicDto mapToSubTopicDto(SubTopicEntity subTopicEntity) {
        SubTopicDto subTopicDto = new SubTopicDto();
        subTopicDto.setId(subTopicEntity.getId());
        subTopicDto.setName(subTopicEntity.getName());
        subTopicDto.setDescription(subTopicEntity.getDescription());
        return subTopicDto;
    }

    public SubTopicEntity mapToSubTopicEntity(SubTopicDto subTopicDto) {
        SubTopicEntity subTopicEntity = new SubTopicEntity();
        subTopicEntity.setName(subTopicDto.getName());
        subTopicEntity.setDescription(subTopicDto.getDescription());
        return subTopicEntity;

    }
}
