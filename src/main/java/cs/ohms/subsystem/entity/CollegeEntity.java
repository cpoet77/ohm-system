package cs.ohms.subsystem.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 学院类
 *
 * @author xuxi
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "ohms_college")
@DynamicInsert
@DynamicUpdate
public class CollegeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id; //学院id

    @Column(nullable = false, unique = true)
    private String name; //学院名

    @Column
    private String description; //学院描述

    @Column(nullable = false)//创建时间
    private LocalDateTime datetime;

    @OneToMany(mappedBy = "college")
    private Set<MajorEntity> majors;//开设的专业
}
