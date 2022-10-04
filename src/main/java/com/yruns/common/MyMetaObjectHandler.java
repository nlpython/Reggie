package com.yruns.common;

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
        metaObject.setValue("createUser", employeeId);
        metaObject.setValue("updateUser", employeeId);
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long employeeId = BaseContext.getCurrentId();
        metaObject.setValue("updateUser", employeeId);
        metaObject.setValue("updateTime", LocalDateTime.now());
    }
}
