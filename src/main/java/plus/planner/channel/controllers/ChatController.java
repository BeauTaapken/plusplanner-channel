package plus.planner.channel.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    @Autowired
    private ChannelRepository channelRepo;
    @Autowired
    private ChatRepository chatRepo;
    private ObjectMapper mapper;
    @Autowired
    private RestTemplate restTemplate;

    ChatController() {
        mapper = new ObjectMapper();
    }

    @RequestMapping(path = "/create/{chat}")
    public void createChat(@PathVariable String chat) {
        try {
            chatRepo.save(mapper.readValue(chat, Chat.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(path = "/read/{projectid}")
    public String readChat(@PathVariable Long projectid) throws IOException {
        List<Chat> chats = chatRepo.findByProjectId(projectid);
        for (Chat c : chats) {
            c.setChannels(channelRepo.findByChatId(c.getChatid()));
            for (Channel ch : c.getChannels()) {
                String messages = restTemplate.getForObject("http://plus-planner-message-service/message/read/" + ch.getChannelid(), String.class);
                ch.setMessages(messages);
            }
        }
        String json = mapper.writeValueAsString(chats);
        json = json.replace("\"[", "[");
        json = json.replace("]\"", "]");
        json = json.replace("\\\"", "\"");
        return json;
    }

    @RequestMapping(path = "/update/{chat}")
    public void UpdateChat(@PathVariable String chat) {
        try {
            chatRepo.save(mapper.readValue(chat, Chat.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(path = "/delete/{chat}")
    public void deleteChat(@PathVariable Long chatid) {
        chatRepo.deleteById(chatid);
    }

}
