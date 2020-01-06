package plus.planner.channel.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import plus.planner.channel.model.Channel;
import plus.planner.channel.repository.ChannelRepository;

@RestController
@RequestMapping("channel")
public class ChannelController {
    private final Logger logger = LoggerFactory.getLogger(ChannelController.class);
    private final ChannelRepository repo;

    @Autowired
    public ChannelController(ChannelRepository repo) {
        this.repo = repo;
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public void createChannel(@RequestBody Channel channel) {
        logger.info("saving channel: " + channel.getChannelid());
        repo.save(channel);
        logger.info("saved channel");
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public void UpdateChannel(@RequestBody Channel channel) {
        logger.info("updating channel: " + channel.getChannelid());
        repo.save(channel);
        logger.info("updated channel");
    }

    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    public void deleteChannel(@PathVariable String channelid) {
        logger.info("deleting channel: " + channelid);
        repo.deleteById(channelid);
        logger.info("deleted channel");
    }
}
