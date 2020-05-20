// The code file was created by xuxi (email:n1040044765@qq.com) on 2020/5/4.
package cn.ohms.subsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Resource实体类
 *
 * @author xuxi
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Entity
@Table(name = "ohms_resource")
@DynamicInsert
@DynamicUpdate
public class ResourceEntity implements Serializable {
    @Id
    @Column
    private String id; // 资源id,用32位uuid表示

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user; //资源上传者

    @Column(nullable = false)
    private String name; // 资源名

    @Column(nullable = false)
    private String suffix;  // 资源后缀

    @Column(nullable = false, insertable = false)
    private LocalDateTime datetime; // 上传时间

    @Column(nullable = false)
    private Boolean publicity; // 是否公开，false为私有

    @JsonIgnore
    @Column(nullable = false)
    private String path; // 存储的路径

    @JsonIgnore
    @Column(nullable = false)
    private Boolean state; // 状态，false表示进入回收阶段
}
