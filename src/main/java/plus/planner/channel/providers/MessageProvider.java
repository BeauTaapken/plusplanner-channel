package plus.planner.channel.providers;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MessageProvider implements IMessageProvider {
    private final RestTemplate restTemplate;

    public MessageProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String getMessages(String channelid) {
        return restTemplate.getForObject("https://plus-planner-message-service/message/read/" + channelid, String.class);
    }
}
