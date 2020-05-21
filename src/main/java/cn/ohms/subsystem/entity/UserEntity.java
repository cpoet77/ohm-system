// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/21.
package cn.ohms.subsystem.entity;

import cn.ohms.subsystem.validation.annotation.NSCharCheck;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * user entity
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Getter
@Setter
@Accessors(chain = true)
@MappedSuperclass
public abstract class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    @Min(1)
    protected Integer id;//id

    @Column(nullable = false)
    @NotEmpty
    @Length(min = 2, max = 5)
    protected String name;//姓名

    @Column(nullable = false)
    @NotEmpty
    @Length(min = 32, max = 32)
    protected String password;//加密过的密码

    @Column(nullable = false)
    @NotEmpty
    @Length(min = 32, max = 32)
    protected String salt;//加密用的盐

    @Column(nullable = false)
    @NotNull
    @NSCharCheck({'M', 'F'})
    protected Character sex;//性别

    @Column(nullable = false)
    @URL
    protected String avatar;//头像地址

    @Column
    @Length(min = 12, max = 128)
    protected String email;//邮箱地址

    @Column
    @Length(min = 10, max = 15)
    protected String phone;//手机号
}
