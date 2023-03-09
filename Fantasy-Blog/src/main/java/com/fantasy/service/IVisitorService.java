package com.fantasy.service;

import com.fantasy.entity.Visitor;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.scheduling.annotation.Async;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Fantasy0521
 * @since 2023-03-03
 */
public interface IVisitorService extends IService<Visitor> {

    boolean hasUUID(String identification);

    @Async
    void saveVisitor(Visitor visitor);
}
