package plus.planner.channel.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.planner.channel.model.Channel;
import plus.planner.channel.repository.ChannelRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("channel")
public class ChannelController {
    @Autowired
    ChannelRepository repo;
    ObjectMapper mapper;

    ChannelController(){
        mapper = new ObjectMapper();
    }

    @RequestMapping(path = "/create/{subpart}")
    public void createChannel(@PathVariable String subpart) {
        try {
            repo.save(mapper.readValue(subpart, Channel.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(path = "/read/{channelid}")
    public List<Channel> readChannel(@PathVariable Long channelid){
        List<Channel> channels = repo.findAll();
        return channels.stream().filter(x -> x.getId() == channelid).collect(Collectors.toList());
    }

    @RequestMapping(path = "/update/{channelid}")
    public void UpdateChannel(@PathVariable String channelid) {
        try {
            repo.save(mapper.readValue(channelid, Channel.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(path = "/delete/{subpartid}")
    public void deleteChannel(@PathVariable Long channelid){
        repo.deleteById(channelid);
    }
}
