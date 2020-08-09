package com.wheel.app.miniorm.metadata;

import com.wheel.app.miniorm.annotation.Column;
import com.wheel.app.miniorm.annotation.PK;
import com.wheel.app.miniorm.annotation.Table;
import lombok.Data;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description 表元数据信息
 * @Author zifu.li@hand-china.com
 * @Date 2020/8/8 18:10
 * @Version 1.0
 */
@Data
public class TableMetadata {

    private String tableName;

    private ColumnMetadata primaryKey;

    private List<ColumnMetadata> columns = new ArrayList<>();

    private String selectByIdSql;

    private Class entityClass;

    /**
     * 解析Mapper类构建表元数据信息
     *
     * @param mapperClass 具体的Mapper类入UserMapper
     */
    public TableMetadata(Class<?> mapperClass) {
        //解析泛型
        ResolvableType resolvableType = ResolvableType.forClass(mapperClass);
        ResolvableType baseMapperType = resolvableType.getInterfaces()[0];
        //获得泛型父接口的具体泛型类型，这里是具体的Entity类
        this.entityClass = baseMapperType.resolveGeneric(0);

        //获取实体类上注解的表名
        Table table = AnnotationUtils.findAnnotation(entityClass, Table.class);
        this.tableName = table.value();

        //获取实体类属性上所有注解了@Column的属性的数据库表字段名
        for (int i = 0; i < entityClass.getDeclaredFields().length; i++) {
            ColumnMetadata columnMetadata = new ColumnMetadata();
            Field field = entityClass.getDeclaredFields()[i];

            //获取列名注解
            Column column = AnnotationUtils.getAnnotation(field, Column.class);
            if (column != null) {
                columnMetadata.setColumnIndex(i + 1);
                columnMetadata.setColumnName(column.value());
                ResolvableType forField = ResolvableType.forField(field);
                columnMetadata.setJavaType(forField.resolve());
                columnMetadata.setField(field);
            }
            PK pk = AnnotationUtils.getAnnotation(field, PK.class);
            if (pk != null) {
                primaryKey = columnMetadata;
            }
            columns.add(columnMetadata);
        }
        this.selectByIdSql = genSelectByIdSql();
    }

    /**
     * 生成一个select <columns> from <table> where pk=? 查询语句
     *
     * @return SelectByIdSql
     */
    private String genSelectByIdSql() {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("select ")
                .append(columns.stream().map(ColumnMetadata::getColumnName)
                .collect(Collectors.joining(",")))
                .append(" from ")
                .append(tableName)
                .append(" where ")
                .append(primaryKey.getColumnName() + " = ?");
        return selectSql.toString();
    }

}
