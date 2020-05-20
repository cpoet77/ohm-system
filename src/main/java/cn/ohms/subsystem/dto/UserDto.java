// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/5/3.
package cn.ohms.subsystem.dto;

import cs.ohmsubsystem.common.scene.RegisterScene;
import cs.ohmsubsystem.utils.NSParseField;
import cs.ohmsubsystem.utils.leafo2o.LeafMapping;
import cs.ohmsubsystem.validation.annotation.NSCharCheck;
import cs.ohmsubsystem.validation.annotation.NSEmail;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * user dto
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public class UserDto implements Serializable {
    @NSParseField(name = "userName", groups = {RegisterScene.class})
    @NotEmpty(groups = {RegisterScene.class})
    @Length(min = 2, max = 16, groups = {RegisterScene.class})
    @LeafMapping(groups = RegisterScene.class)
    protected String name;// 用户名

    @NSParseField(groups = {RegisterScene.class})
    @NotEmpty(groups = {RegisterScene.class})
    @Length(min = 2, max = 12, groups = {RegisterScene.class})
    @LeafMapping(groups = RegisterScene.class)
    protected String realName;// 真实姓名

    @NSParseField(groups = {RegisterScene.class})
    @NotEmpty(groups = {RegisterScene.class})
    @NSEmail(groups = {RegisterScene.class})
    @LeafMapping(groups = RegisterScene.class)
    protected String email;// 邮箱地址

    @NSParseField(value = "M", groups = {RegisterScene.class})
    @NotNull(groups = {RegisterScene.class})
    @NSCharCheck(value = {'M', 'F'}, groups = {RegisterScene.class})
    @LeafMapping(groups = RegisterScene.class)
    protected Character sex;// 性别

    @NotEmpty
    @URL
    @LeafMapping
    protected String avatar;// 头像地址

    @NotNull
    @Min(0)
    @LeafMapping
    protected Integer activity;// 活跃度

    @LeafMapping
    protected LocalDateTime signUpTime;// 注册时间

    @NotEmpty(groups = {RegisterScene.class})
    @Length(min = 2, max = 2, groups = {RegisterScene.class})
    @LeafMapping(groups = RegisterScene.class)
    protected String locale;// 区域

    /**
     * Gets the value of name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the value of realName.
     *
     * @return realName
     */
    public String getRealName() {
        return realName;
    }

    /**
     * Sets the realName.
     *
     * @param realName realName
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * Gets the value of email.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the value of sex.
     *
     * @return sex
     */
    public Character getSex() {
        return sex;
    }

    /**
     * Sets the sex.
     *
     * @param sex sex
     */
    public void setSex(Character sex) {
        this.sex = sex;
    }

    /**
     * Gets the value of avatar.
     *
     * @return avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * Sets the avatar.
     *
     * @param avatar avatar
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * Gets the value of activity.
     *
     * @return activity
     */
    public Integer getActivity() {
        return activity;
    }

    /**
     * Sets the activity.
     *
     * @param activity activity
     */
    public void setActivity(Integer activity) {
        this.activity = activity;
    }

    /**
     * Gets the value of signUpTime.
     *
     * @return signUpTime
     */
    public LocalDateTime getSignUpTime() {
        return signUpTime;
    }

    /**
     * Sets the signUpTime.
     *
     * @param signUpTime signUpTime
     */
    public void setSignUpTime(LocalDateTime signUpTime) {
        this.signUpTime = signUpTime;
    }

    /**
     * Gets the value of locale.
     *
     * @return locale
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Sets the locale.
     *
     * @param locale locale
     */
    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "name='" + name + '\'' +
                ", realName='" + realName + '\'' +
                ", email='" + email + '\'' +
                ", sex=" + sex +
                ", avatar='" + avatar + '\'' +
                ", activity=" + activity +
                ", signUpTime=" + signUpTime +
                ", locale='" + locale + '\'' +
                '}';
    }
}
