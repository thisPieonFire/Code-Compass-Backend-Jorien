package local.code_compass_backend.controller;

import local.code_compass_backend.dto.SubTopicDto;
import local.code_compass_backend.service.SubTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SubTopicController {

    @Autowired
    private SubTopicService subTopicService;

    @PostMapping("/subtopic")
    public void saveSubTopic(@RequestBody SubTopicDto subTopicDto) {
        subTopicService.saveSubTopic(subTopicDto);
    }

    @GetMapping("/subtopic/{id}")
    public SubTopicDto getSubTopic(@PathVariable Long id) {
        return subTopicService.getSubTopic(id);
    }
}