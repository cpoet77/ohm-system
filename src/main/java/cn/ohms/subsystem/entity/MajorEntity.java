package cn.ohms.subsystem.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Major Entity
 * @author shc
 */

@Entity
@Table("ohms_Major")
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@Accessors(chain = true)
public class MajorEntity implements Serializable  {
    @Id
    @Column(name = "Major_id")
    private String MajorName;//专业名
    private Integer collegeId;//学院名

}
