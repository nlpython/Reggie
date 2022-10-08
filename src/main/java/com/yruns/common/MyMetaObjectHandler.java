package com.yruns.common;

import com.alibaba.druid.sql.visitor.functions.If;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MetaObjectHandler
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 执行insert语句时执行
        Long employeeId = BaseContext.getCurrentId();

        if (metaObject.hasSetter("createUser")) {
            metaObject.setValue("createUser", employeeId);
        }
        if (metaObject.hasSetter("updateUser")) {
            metaObject.setValue("updateUser", employeeId);
        }
        if (metaObject.hasSetter("createTime")) {
            metaObject.setValue("createTime", LocalDateTime.now());
        }
        if (metaObject.hasSetter("updateTime")) {
            metaObject.setValue("updateTime", LocalDateTime.now());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long employeeId = BaseContext.getCurrentId();

        if (metaObject.hasSetter("updateUser")) {
            metaObject.setValue("updateUser", employeeId);
        }
        if (metaObject.hasSetter("updateTime")) {
            metaObject.setValue("updateTime", LocalDateTime.now());
        }
    }
}
