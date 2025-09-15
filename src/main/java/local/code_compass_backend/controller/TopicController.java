package local.code_compass_backend.controller;

import local.code_compass_backend.dto.TopicDto;
import local.code_compass_backend.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")*/

@RestController
public class TopicController {

    @Autowired
    private TopicService topicService;

    @PostMapping("/topic")
    public void saveTopic(@RequestBody TopicDto topicDto) {
        topicService.saveTopic(topicDto);
    }

    @GetMapping("/topic/{id}")
    public TopicDto getTopic(@PathVariable Long id) {
        return topicService.getTopic(id);
    }
}