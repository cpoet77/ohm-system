// The code file was created by xuxi (email:n1040044765@qq.com) on 2020/5/4.
package cn.ohms.subsystem.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * MessageBox实体类
 *
 * @author xuxi
 */
@Entity
@Table(name = "ohms_messagebox")
@DynamicInsert
@DynamicUpdate
public class MessageBoxEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;  // 消息id

    @Column(nullable = false)
    private String title; // 消息标题

    @Column
    private String content; // 消息内容

    @Column(name = "send_time", nullable = false, insertable = false, updatable = false)
    private LocalDateTime sendTime;  // 发送时间

    @ManyToMany
    @JoinTable(name = "ohms_user_messagebox", joinColumns = {@JoinColumn(name = "message_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private Set<UserEntity> users = new HashSet<>();  // 收件人
}
