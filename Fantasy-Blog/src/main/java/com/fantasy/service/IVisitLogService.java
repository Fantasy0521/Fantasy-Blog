package com.fantasy.service;

import com.fantasy.entity.VisitLog;
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
public interface IVisitLogService extends IService<VisitLog> {

    @Async
    void saveVisitLog(VisitLog visitLog);

    int countVisitLogByToday();
}
