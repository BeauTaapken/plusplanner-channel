package plus.planner.channel.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import plus.planner.channel.model.Channel;
import plus.planner.channel.model.Chat;
import plus.planner.channel.repository.ChannelRepository;
import plus.planner.channel.repository.ChatRepository;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("chat")
public class ChatController {
    private final Logger logger = LoggerFactory.getLogger(ChatController.class);
    private final ChannelRepository channelRepo;
    private final ChatRepository chatRepo;
    private final RestTemplate restTemplate;

    @Autowired
    public ChatController(ChannelRepository channelRepo, ChatRepository chatRepo, RestTemplate restTemplate) {
        this.channelRepo = channelRepo;
        this.chatRepo = chatRepo;
        this.restTemplate = restTemplate;
    }


    @PostMapping(path = "/create")
    public void createChat(@RequestBody Chat chat) {
        logger.info("saving chat: " + chat.getChatid());
        chatRepo.save(chat);
        logger.info("saved chat");
    }

    @GetMapping(path = "/read/{projectid}")
    public List<Chat> readChat(@PathVariable String projectid) throws IOException {
        logger.info("getting chats for projectid: " + projectid);
        final List<Chat> chats = chatRepo.findByProjectId(projectid);
        for (Chat c : chats) {
            logger.info("getting channels for chatid: " + c.getChatid());
            c.setChannels(channelRepo.findByChatId(c.getChatid()));
            for (Channel ch : c.getChannels()) {
                logger.info("getting chats for channelid: " + ch.getChannelid());
                ch.setMessages(restTemplate.getForObject("http://plus-planner-message-service/message/read/" + ch.getChannelid(), String.class));
            }
        }
        logger.info("returning chats");
        return chats;
    }

    @PostMapping(path = "/update")
    public void updateChat(@RequestBody Chat chat) {
        logger.info("updating chat: " + chat.getChatid());
        chatRepo.save(chat);
        logger.info("updated chat");
    }

    @PostMapping(path = "/delete")
    public void deleteChat(@RequestBody String chatid) {
        logger.info("deleting chat: " + chatid);
        chatRepo.deleteById(chatid);
        logger.info("deleted chat");
    }

}
