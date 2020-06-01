// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/30.
package cs.ohms.subsystem.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "ohms_messagebox")
@DynamicUpdate
@DynamicInsert
public class MessageBoxEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;//消息id

    @Column(nullable = false)
    private String title;//消息标题

    @Column
    private String content;//消息内容

    @Column(name = "send_time", nullable = false)
    private LocalDateTime sendTime;//发送时间

    @ManyToMany
    @JoinTable(name = "ohms_user_messagebox", joinColumns = {@JoinColumn(name = "message_id", referencedColumnName = "id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)})
    private Set<UserEntity> users = new HashSet<>();//收件人
}
