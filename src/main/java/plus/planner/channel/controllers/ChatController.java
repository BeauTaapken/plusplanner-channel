package plus.planner.channel.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.planner.channel.model.Channel;
import plus.planner.channel.model.Chat;
import plus.planner.channel.repository.ChannelRepository;
import plus.planner.channel.repository.ChatRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("chat")
public class ChatController {
    @Autowired
    private ChannelRepository channelRepo;
    @Autowired
    private ChatRepository chatRepo;
    private ObjectMapper mapper;

    ChatController(){
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
        List<Chat> chats = chatRepo.findAll();
        List<Channel> channels = channelRepo.findAll();
        chats = chats.stream().filter(x -> x.getChatid() == projectid).collect(Collectors.toList());
        for (Chat c: chats) {
            c.setChannels(channels.stream().filter(x -> x.getChannelid() == c.getChatid()).collect(Collectors.toList()));
            for (Channel ch : c.getChannels()) {
                URL url = null;
                URLConnection conn = null;
                try {
                    url = new URL("http://localhost:8084/message/read/" + ch.getChannelid());
                    conn = url.openConnection();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                StringBuilder sb = new StringBuilder();
                String output;
                while ((output = br.readLine()) != null) {
                    sb.append(output);
                }
                ch.setMessages(sb.toString());
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
    public void deleteChat(@PathVariable Long chatid){
        chatRepo.deleteById(chatid);
    }

}
