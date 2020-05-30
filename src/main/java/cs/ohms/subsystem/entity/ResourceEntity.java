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

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Entity
@Table(name = "ohms_resource")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@Accessors(chain = true)
public class ResourceEntity implements Serializable {
    @Id
    @Column
    private String id;//文件id

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;//上传用户

    @Column(nullable = false)
    private String name;//文件名

    @Column(nullable = false)
    private String suffix;//文件后缀

    @Column(nullable = false)
    private LocalDateTime datetime;//上传时间

    @Column(nullable = false)
    private Boolean publicity;//是否公开

    @Column(nullable = false)
    private String path;//物理路径
}
