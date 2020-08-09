package com.wheel.app.miniorm.metadata;

import lombok.Data;

import java.lang.reflect.Field;

/**
 * @Description
 * @Author zifu.li@hand-china.com
 * @Date 2020/8/8 18:10
 * @Version 1.0
 */
@Data
public class ColumnMetadata {

    /**
     * 列名
     */
    private String columnName;
    /**
     * 列序号
     */
    private int columnIndex;

    /**
     * 对应的java类型
     */
    private Class<?> javaType;

    /**
     * Entity类的属性字段,用来反射设置值
     */
    private Field field;
}
