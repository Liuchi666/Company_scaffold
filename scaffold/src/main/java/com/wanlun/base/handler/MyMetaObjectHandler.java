package com.wanlun.base.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * MybatisPlus字段自动填充配置
 *
 * @author 记住吾名梦寒
 * @update 2023/2/16 15:00
 * @since 2023/2/16 15:00
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 自动插入创建时间到create_time字段
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
    }

    /**
     * 自动插入更新时间到update_time字段
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }

}
