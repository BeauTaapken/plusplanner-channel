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
    ChannelRepository channelRepo;
    @Autowired
    ChatRepository chatRepo;
    ObjectMapper mapper;

    ChatController(){
        mapper = new ObjectMapper();
    }

    @RequestMapping(path = "/create/{chatid}")
    public void createChannel(@PathVariable String chatid) {
        try {
            chatRepo.save(mapper.readValue(chatid, Chat.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(path = "/read/{chatid}")
    public String readSubPart(@PathVariable Long chatid) throws IOException {
        List<Chat> chats = chatRepo.findAll();
        chats = chats.stream().filter(x -> x.getId() == chatid).collect(Collectors.toList());
        for (Chat c: chats) {
            c.setChannels(channelRepo.findAll());
            for (Channel p : c.getChannels()) {
                URL url = null;
                URLConnection conn = null;
                try {
                    url = new URL("http://localhost:8084" + p.getId());
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
                p.setMessages(sb.toString());
            }
        }
        String json = mapper.writeValueAsString(chats);
        json = json.replace("\"[", "[");
        json = json.replace("]\"", "]");
        return json;
    }

    @RequestMapping(path = "/update/{chatid}")
    public void UpdateChat(@PathVariable String chatid) {
        try {
            chatRepo.save(mapper.readValue(chatid, Chat.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(path = "/delete/{chatid}")
    public void deleteChannel(@PathVariable Long chatid){
        chatRepo.deleteById(chatid);
    }

}
