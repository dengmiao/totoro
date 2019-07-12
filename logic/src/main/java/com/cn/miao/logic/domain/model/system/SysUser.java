package com.cn.miao.logic.domain.model.system;

import com.cn.miao.base.BaseModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @title: SysUser
 * @description:
 * @author: dengmiao
 * @create: 2019-07-12 16:27
 **/
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "sys_user")
@Where(clause = "del_status = 0")
public class SysUser extends BaseModel<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    /**
     * 登录账号
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 生日
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;

    /**
     * 性别（1：男 2：女）
     */
    private Integer sex;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 状态(1：正常  2：冻结 ）
     */
    private Integer status;
}
