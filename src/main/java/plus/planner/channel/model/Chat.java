package plus.planner.channel.model;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
    @NotBlank
    private String name;
    @NotBlank
    private String projectid;
    @Transient
    private List<Channel> channels;
}
