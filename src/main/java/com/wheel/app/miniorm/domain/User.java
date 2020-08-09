package com.wheel.app.miniorm.domain;

import com.wheel.app.miniorm.annotation.Column;
import com.wheel.app.miniorm.annotation.PK;
import com.wheel.app.miniorm.annotation.Table;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Description
 * @Author zifu.li@hand-china.com
 * @Date 2020/8/8 18:04
 * @Version 1.0
 */
@Table("sys_user")
@Data
public class User {
    @PK
    @Column("user_id")
    private Long userId;

    @Column("username")
    private String username;

    @Column("password")
    private String password;

    @Column("mobile")
    private String mobile;

    @Column("create_time")
    private Timestamp createTime;
}
