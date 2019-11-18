package plus.planner.channel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plus.planner.channel.model.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
