package plus.planner.channel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ChannelService {

    public static void main(String[] args) {
        SpringApplication.run(ChannelService.class, args);
    }

}
