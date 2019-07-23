package com.cn.miao.base;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @title: BaseModel
 * @description:
 * @author: dengmiao
 * @create: 2019-07-12 14:47
 **/
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseModel<T> implements Serializable {

    /**
     * 主键
     * 非@org.springframework.data.annotation.Id
     * 而是@javax.persistence.Id
     * 子类可以覆盖
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private T id;

    @Column(name = "create_by", columnDefinition = "varchar(255) DEFAULT NULL COMMENT '创建人'")
    private String createBy;

    @Column(name = "update_by", columnDefinition = "varchar(255) DEFAULT NULL COMMENT '更新人'")
    private String updateBy;

    @Column(name = "create_time", columnDefinition = "datetime COMMENT '创建时间'")
    private LocalDateTime createTime;

    @Column(name = "update_time", columnDefinition = "datetime COMMENT '更新时间'")
    private LocalDateTime updateTime;

    @Column(name = "del_status", columnDefinition = "int(1) DEFAULT NULL COMMENT '删除标识位 0正常 1已删除'", length = 1)
    private boolean delStatus;
}
