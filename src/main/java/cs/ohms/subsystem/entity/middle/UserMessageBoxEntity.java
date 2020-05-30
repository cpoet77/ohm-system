// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/30.
package cs.ohms.subsystem.entity.middle;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Entity
@Data
@Accessors(chain = true)
@Table(name = "ohms_user_messagebox")
public class UserMessageBoxEntity implements Serializable {
    @EmbeddedId
    private Key id;

    @Column(name = "is_read")
    private Boolean isRead;

    @Embeddable
    @MappedSuperclass
    @Data
    @Accessors(chain = true)
    public static class Key implements Serializable{
        @Column(name = "user_id")
        private Integer userId;

        @Column(name = "message_id")
        private Integer messageId;
    }
}
