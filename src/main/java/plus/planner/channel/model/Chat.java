package plus.planner.channel.model;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "chat")
@EntityListeners(AuditingEntityListener.class)
public class Chat {
    @Id
    private String chatid;
    private String name;
    private String projectid;
    @Transient
    private List<Channel> channels;
}
