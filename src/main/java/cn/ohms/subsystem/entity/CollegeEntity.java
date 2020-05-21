package cn.ohms.subsystem.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 学院类
 *
 * @author <a>xuxi</a>
 */
@Entity
@Table(name="ohms_college")

public class CollegeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Byte id; //学院id

    @Column(nullable = false, unique = true)
    private String name; //学院名

    @Column
    private String description; //学院描述
}
