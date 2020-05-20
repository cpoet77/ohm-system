package cn.ohms.subsystem.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 权限类
 *
 * @author shc
 */
@Entity
@Table(name = "ohms_permission")
@DynamicInsert
@DynamicUpdate
public class PermissionEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id; //权限id

    @Column(nullable = false, unique = true)
    private String name; //权限名

    @Column
    private String description; //权限描述

    @Column(nullable = false, insertable = false, updatable = false)
    private LocalDateTime datetime; //添加时间
}
