package plus.planner.channel.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import plus.planner.channel.model.Channel;
import plus.planner.channel.repository.ChannelRepository;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("channel")
public class ChannelController {
    private final Logger logger = LoggerFactory.getLogger(ChannelController.class);
    private final ChannelRepository repo;
    private final ObjectMapper objectMapper;

    @Autowired
    public ChannelController(ChannelRepository repo, ObjectMapper objectMapper) {
        this.repo = repo;
        this.objectMapper = objectMapper;
    }

    @PostMapping(path = "/create")
    public void createChannel(@RequestBody String chnl) throws IOException {
        final Channel channel = objectMapper.readValue(chnl, Channel.class);
        logger.info("saving channel: {}", channel.getChannelid());
        repo.save(channel);
        logger.info("saved channel");
    }

    @PostMapping(path = "/update")
    public void updateChannel(@RequestBody String chnl) throws IOException {
        final Channel channel = objectMapper.readValue(chnl, Channel.class);
        logger.info("updating channel: {}", channel.getChannelid());
        repo.save(channel);
        logger.info("updated channel");
    }

    @PostMapping(path = "/delete/{channelid}")
    public void deleteChannel(@PathVariable String channelid) {
        logger.info("deleting channel: {}", channelid);
        repo.deleteById(channelid);
        logger.info("deleted channel");
    }
}
