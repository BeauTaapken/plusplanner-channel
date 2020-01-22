package plus.planner.channel.model;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

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
    private String channelid;
    private String name;
    private String chatid;
    @Transient
    @JsonRawValue
    private String messages;
}
