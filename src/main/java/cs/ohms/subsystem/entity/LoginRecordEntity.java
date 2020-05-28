// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/13.
package cs.ohms.subsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * LoginRecordEntity
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "ohms_login_record")
public class LoginRecordEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;// 登录记录id

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    @Column(name = "login_ip", nullable = false)
    private String loginIp; // 登录的ip

    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss")
    @Column(nullable = false, insertable = false, updatable = false)
    private LocalDateTime datetime;//登录时间

    @Column
    private String province;//省

    @Column(name = "province_code")
    private String provinceCode;//省区代码+

    @Column
    private String city;//城市

    @Column(name = "city_code")
    private String cityCode;//城市代码

    @Column
    private String address;//地址

    @Column
    private String agent;//userEntity-agent
}
