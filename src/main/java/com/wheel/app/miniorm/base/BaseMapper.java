package com.wheel.app.miniorm.base;

/**
 * @Description
 * @Author zifu.li@hand-china.com
 * @Date 2020/8/8 18:06
 * @Version 1.0
 */
public interface BaseMapper<T> {

    T selectById(Object id);
}
