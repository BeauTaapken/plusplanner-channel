package plus.planner.channel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plus.planner.channel.model.Channel;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
}
