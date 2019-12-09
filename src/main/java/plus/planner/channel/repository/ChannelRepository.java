package plus.planner.channel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import plus.planner.channel.model.Channel;

import java.util.List;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
    @Query("SELECT c FROM Channel c WHERE c.chatid = :chatid")
    List<Channel> findByChatId(@Param("chatid") Long chatid);
}
