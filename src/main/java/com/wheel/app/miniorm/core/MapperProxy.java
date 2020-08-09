package com.wheel.app.miniorm.core;


import com.wheel.app.miniorm.config.JdbcProperties;
import com.wheel.app.miniorm.metadata.ColumnMetadata;
import com.wheel.app.miniorm.metadata.TableMetadata;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @Description
 * @Author zifu.li@hand-china.com
 * @Date 2020/8/8 18:44
 * @Version 1.0
 */
@Component
@NoArgsConstructor
public class MapperProxy implements InvocationHandler{
    private final String url = "jdbc:mysql://127.0.0.1:3306/sys?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    private final String username = "root";
    private final String pwd = "123456";

    private Class<?> mapperClass;

    private TableMetadata tableMetadata;


    public MapperProxy(Class<?> mapperClass) {
        this.mapperClass = mapperClass;
        this.tableMetadata = new TableMetadata(mapperClass);
    }

    public static <T> T getMapper(Class<T> mapperClass) {
        return (T) Proxy.newProxyInstance(MapperProxy.class.getClassLoader(), new Class[]{mapperClass}, new MapperProxy(mapperClass));
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        try (Connection connection = DriverManager.getConnection(url, username, pwd)) {
            if ("selectById".equalsIgnoreCase(method.getName())) {
                String selectByIdSql = tableMetadata.getSelectByIdSql();

                try (PreparedStatement preparedStatement = connection.prepareStatement(selectByIdSql)) {

                    preparedStatement.setObject(1, args[0]);

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        //反射创建实体类
                        Object entity = tableMetadata.getEntityClass().newInstance();
                        while (resultSet.next()) {
                            for (ColumnMetadata columnMetadata : tableMetadata.getColumns()) {
                                //反射设置属性
                                columnMetadata.getField().setAccessible(true);
                                columnMetadata.getField().set(entity, resultSet.getObject(columnMetadata.getColumnName()));
                            }
                            return entity;
                        }
                    }
                }
            }

            return null;
        }

    }



}
