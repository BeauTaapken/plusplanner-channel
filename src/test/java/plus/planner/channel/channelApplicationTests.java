package plus.planner.channel;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import plus.planner.channel.model.Channel;
import plus.planner.channel.model.Chat;

import javax.ws.rs.core.MediaType;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:application-test.properties")
@SpringBootTest
public class channelApplicationTests {

    private final Gson gson = new Gson();

    private MockMvc mockMvc;

    private final Channel channel = new Channel("1", "testchannel", "1", "testmessage");

    private final Chat chat = new Chat("1", "testchat", "1", new ArrayList<>());

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/channel/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(channel))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        mockMvc.perform(MockMvcRequestBuilders.post("/chat/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(chat))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    public void createChannelCorrectly() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/channel/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(channel))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    public void updateChannelCorrectly() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/channel/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(channel))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    public void deleteChannelCorrectly() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/channel/delete/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    public void createChatCorrectly() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/chat/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(chat))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    public void updateChatCorrectly() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/chat/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(chat))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    public void deleteChatCorrectly() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/chat/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content("1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }
}