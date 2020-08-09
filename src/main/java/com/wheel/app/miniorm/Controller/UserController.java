package com.wheel.app.miniorm.Controller;

import com.wheel.app.miniorm.config.JdbcProperties;
import com.wheel.app.miniorm.core.MapperProxy;
import com.wheel.app.miniorm.domain.User;
import com.wheel.app.miniorm.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author zifu.li@hand-china.com
 * @Date 2020/8/8 19:39
 * @Version 1.0
 */
@RestController
public class UserController {


    @GetMapping("/get/user/{id}")
    public User getUserById(@PathVariable("id") Long id){
        UserMapper mapper = MapperProxy.getMapper(UserMapper.class);
        User user = mapper.selectById(id);
        return user;

    }
}
