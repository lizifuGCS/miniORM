package com.wheel.app.miniorm.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author zifu.li@hand-china.com
 * @Date 2020/8/8 18:39
 * @Version 1.0
 */
@ConfigurationProperties(prefix = "mini")
@Data
@Component
public class JdbcProperties {

    private  String url;

    private  String username;

    private  String pwd;

}
