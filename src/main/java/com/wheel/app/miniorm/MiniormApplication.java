package com.wheel.app.miniorm;

import com.wheel.app.miniorm.config.JdbcProperties;
import com.wheel.app.miniorm.utils.SpringContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MiniormApplication {


    public static void main(String[] args) {
        SpringApplication.run(MiniormApplication.class, args);}

}
