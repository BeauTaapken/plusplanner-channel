package plus.planner.channel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "channel")
@EntityListeners(AuditingEntityListener.class)
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long channelid;
    @NotBlank
    private String name;
    @Null
    @JsonIgnore
    private Long chatid;
    @Transient
    private String messages;
}
