package plus.planner.channel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import plus.planner.channel.model.Chat;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("SELECT c FROM Chat c WHERE c.projectid = :projectid")
    List<Chat> findByProjectId(@Param("projectid") Long projectid);
}
